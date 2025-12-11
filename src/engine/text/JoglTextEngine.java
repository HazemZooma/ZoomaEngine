package engine.text;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import engine.core.EngineContext;

import java.awt.geom.Rectangle2D;

class JoglTextEngine implements TextEngine {

    private final String fontName;
    private final TextRenderer textRenderer;

    JoglTextEngine(String fontName , int size) {
        this.fontName = fontName;
        this.textRenderer = FontRegistry.getInstance().getRenderer(fontName , size);
    }


    @Override
    public float[] calculateDimensions(String text) {
        String[] lines = text.split("\n");
        float orthoWidth =0 , orthoHeight =0 , linePixelWidth =0;
        Rectangle2D bounds = textRenderer.getBounds(text);




        float linePixelHeight = (float) bounds.getHeight();

        for (String line : lines) {
            bounds = textRenderer.getBounds(line);
            linePixelWidth = (float) bounds.getWidth();


            orthoWidth = Math.max(orthoWidth, linePixelWidth);
        }


         orthoWidth = EngineContext.get().pixelToOrthoWidth(orthoWidth);
         orthoHeight = EngineContext.get().pixelToOrthoHeight(linePixelHeight);

        return new float[]{orthoWidth, orthoHeight};
    }

    @Override
    public void draw(GL2 gl, Text text) {
        if (text == null || text.getContent().trim().isEmpty() || text.getContent() == null) {
            return;
        }
        EngineContext engineContext = EngineContext.get();

        float textX = text.getTransformation().getTranslateX();
        float textY = text.getTransformation().getTranslateY();
        String content = text.getContent();
        float[] color = text.getColor();;


        int pixelX = engineContext.orthoToPixelX(textX);
        int pixelY = engineContext.orthoToPixelY(textY);

        String[] lines = content.split("\n");

        textRenderer.beginRendering(engineContext.getWorldPixelWidth(), engineContext.getWorldPixelHeight());
        textRenderer.setColor(color[0], color[1], color[2], 1f);



        int offsetY = pixelY;
        for (int i = 0 ; i < lines.length-1 ;i++) {
            int lineHeight = engineContext.orthoToPixelHeight(calculateDimensions(lines[i+1])[1]);
            textRenderer.draw(lines[i], pixelX, offsetY);
            offsetY -= lineHeight;
        }
        textRenderer.draw(lines[lines.length-1], pixelX, offsetY);

//        System.out.println(lines[lines.length -1 ]);

        textRenderer.endRendering();

    }

    public void dispose() {
        if (textRenderer != null) {
            textRenderer.dispose();
        }
    }


    public TextRenderer getRenderer(float orthoSize) {
        int pixelSize = EngineContext.get().orthoToPixelHeight(orthoSize);
        return FontRegistry.getInstance().getRenderer(fontName, pixelSize);
    }
}