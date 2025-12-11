package engine.core;

import App_constants.Constants;

public final class EngineContext {

    private int worldPixelWidth = 800;
    private int worldPixelHeight = 600;

    private static final EngineContext INSTANCE = new EngineContext();

    private EngineContext() { }

    public static EngineContext get() {
        return INSTANCE;
    }

    public void updateViewport(int pixelWidth, int pixelHeight) {
        this.worldPixelWidth = pixelWidth;
        this.worldPixelHeight = pixelHeight;
    }

    public int orthoToPixelX(float orthoX) {
        int orthoLeft = Constants.World.ORTHO_LEFT;
        int orthoRight = Constants.World.ORTHO_RIGHT;

        float normalized = (orthoX - orthoLeft) / (orthoRight - orthoLeft);

        return (int) (normalized * worldPixelWidth);
    }

    public int orthoToPixelY(float orthoY) {
        int orthoBottom = Constants.World.ORTHO_BOTTOM;
        int orthoTop = Constants.World.ORTHO_TOP;

        float normalized = (orthoY - orthoBottom) / (orthoTop - orthoBottom);

        return (int) (normalized * worldPixelHeight);
    }

    public int pixelToOrthoX(float pixelX) {
        int orthoLeft = Constants.World.ORTHO_LEFT;
        int orthoRight = Constants.World.ORTHO_RIGHT;

        float normalized = pixelX / worldPixelWidth;
        return (int) (orthoLeft + normalized * (orthoRight - orthoLeft));
    }

    public int pixelToOrthoY(float pixelY) {
        int orthoBottom = Constants.World.ORTHO_BOTTOM;
        int orthoTop = Constants.World.ORTHO_TOP;

        float normalized = (pixelY - worldPixelHeight);
        return (int) (orthoBottom + normalized * (orthoTop - orthoBottom));
    }

    public int pixelToOrthoWidth(float pixelWidth) {
        int orthoWidth = Constants.World.ORTHO_WIDTH;

        float normalized = pixelWidth / worldPixelWidth;

//        System.out.println(normalized * orthoWidth);
        return (int) (normalized * orthoWidth);
    }

    public int pixelToOrthoHeight(float pixelHeight) {
        int orthoHeight = Constants.World.ORTHO_HEIGHT;

        float normalized = pixelHeight / worldPixelHeight;
        return (int) (normalized * orthoHeight);
    }

    public int orthoToPixedWidth(float orthoWidth){
        int worldOrthoWidth = Constants.World.ORTHO_WIDTH;

        float normalized = orthoWidth / worldOrthoWidth;
        return (int) (normalized * worldPixelWidth);
    }

    public int orthoToPixelHeight(float orthoHeight){
        int worldOrthoHeight = Constants.World.ORTHO_HEIGHT;

        float normalized = orthoHeight / worldOrthoHeight;
        return (int) (normalized * worldPixelHeight);
    }

    public int getWorldPixelWidth() { return worldPixelWidth; }
    public int getWorldPixelHeight() { return worldPixelHeight; }
}