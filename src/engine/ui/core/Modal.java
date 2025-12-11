package engine.ui.core;

import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.text.Text;
import engine.ui.background.GeometricBackground;
import engine.ui.background.UIBackground;
import engine.ui.factory.ButtonFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Modal extends UIElement {
    private ModalHeader header;
    private final List<RenderableObject> contentElements = new ArrayList<>();
    private final List<Button> actionButtons = new ArrayList<>();

    private Text titleText;
    private Text messageText;
    private Button closeButton;

    private final boolean hasHeader;
    private final boolean hasCloseButton;

    private ModalTextAlignment alignment;

    private Modal(Builder<?> builder) {
        super(builder);
//        System.out.println("zooma" + builder.height);
//        System.out.println("zooma " + getHeight());

        this.hasHeader = builder.hasHeader;
        this.hasCloseButton = builder.hasCloseButton;

        if(builder.alignment != null) alignment = builder.alignment;


        createHeader(builder);
        createContent(builder);
        createActionButtons(builder);
        createCloseButton(builder);



        layoutElements();

        setBackgroundOpacity(builder.backgroundOpacity);
        setChildrenOpacity(builder.childrenOpacity);


    }

    private void createHeader(Builder<?> builder) {
        if (!hasHeader) return;

        float posY = getHeight()/2 + builder.headerHeight/2;


        header = new ModalHeader(0 , posY , width , builder.headerHeight);


        if(builder.headerBackground != null) header.background  = builder.headerBackground;
        else header.background = new GeometricBackground.Builder().color(builder.headerColor).build();


        if(!builder.title.trim().isEmpty()) {
            float titleSize = builder.titleSize == 0 ? header.getHeight() /2: builder.titleSize;
            titleText = new Text(-header.getWidth() /2, posY, builder.title,(int) titleSize, builder.titleColor);
            titleText.getTransformation().translateTo(-titleText.getWidth()/2 , posY- titleText.getHeight()/2);
            header.addChildren(titleText);
        }


        addChildren(header);
    }

    private void createContent(Builder<?> builder) {
        if (builder.message != null && !builder.message.trim().isEmpty()) {
            messageText = new Text(0, 0, builder.message , builder.messageSize, builder.messageColor);
            contentElements.add(messageText);
        }

        if (builder.contentElements != null) {
            contentElements.addAll(builder.contentElements);

            addChildren(contentElements);
        }


    }

    private void createActionButtons(Builder<?> builder) {
        if (builder.buttons == null || builder.buttons.isEmpty()) return;

        float totalButtonWidth = getWidth() - (paddingHorizontal * 2);
        float buttonWidth =  totalButtonWidth / builder.buttons.size();
        float buttonSpacing = 10f;

        float startX = -totalButtonWidth / 2 + buttonWidth / 2;
        float buttonY = -height / 2 + paddingVertical + 20f;

        for (int i = 0; i < builder.buttons.size(); i++) {
            ModalButtonConfig buttonConfig = builder.buttons.get(i);


            Button button = ButtonFactory.builder()
                    .at(startX + i * (buttonWidth + buttonSpacing), buttonY)
                    .size(buttonConfig.width , buttonConfig.height)
                    .background(new GeometricBackground.Builder()
                            .shapeType(engine.shapes.ShapeType.ROUNDED_RECTANGLE)
                            .color(buttonConfig.color)
                            .radius(8f)
                            .build())
                    .textContent(buttonConfig.text)
                    .textColor(buttonConfig.textColor)
                    .onClick(e -> {
                        if (buttonConfig.action != null) {
                            buttonConfig.action.accept(this);
                        }
                    })
                    .build();

            actionButtons.add(button);
        }
        addChildren(actionButtons);
    }

    private void createCloseButton(Builder<?> builder) {
        if (!hasCloseButton) return;

        float buttonDiameter = hasHeader ? header.getHeight()/2 : getWidth()/15;

        float posX = getWidth()/2 - buttonDiameter/2;

        float posY = hasHeader ? (getHeight()/2 + header.getHeight()/2) : getHeight()/2;

        closeButton = ButtonFactory.createCircularButton(
                posX, posY, 10,
                Colors.RED, "X", e -> {
                    setVisible(false);
                    if (builder.onClose != null) {
                        builder.onClose.accept(this);
                    }
                });

        closeButton.setMinWidth(0);
        closeButton.setMinHeight(0);

        closeButton.setWidth(buttonDiameter);


        closeButton.setOnHoverEnter(e -> {
            if (closeButton.getBackground() instanceof GeometricBackground bg) {
                bg.setColor(Colors.lightenColor(Colors.RED));
            }
        });

        closeButton.setOnHoverExit(e -> {
            if (closeButton.getBackground() instanceof GeometricBackground bg) {
                bg.setColor(Colors.RED);
            }
        });

//        closeButton.setParent(this);
        addChildren(closeButton);
    }

    private void layoutElements() {
        if(hasHeader){
            header.setWidth(width);
        }

        if (messageText != null) {
            float messageX = getTransformation().getTranslateX(), messageY = getTransformation().getTranslateY();
            String firstLine = messageText.getContent().split("\n")[0];
            Text dummy = new Text(0 ,0, firstLine , (int) messageText.getSize()[1]);
            float firstLineHeight = dummy.getHeight();
            switch (alignment) {
                case TOP_LEFT:
                    messageX -= getWidth()/2;
                    messageY += getHeight()/2 - firstLineHeight;
                    break;
                case CENTER:
                default:
                    messageX -= dummy.getWidth()/2;
                    break;
            }
            dummy = null;
            messageText.getTransformation().translateTo(messageX, messageY);
        }
//        float currentY = hasHeader ? height / 2 - header.getHeight() - 60 : height / 2 - 60;
//        for (RenderableObject element : contentElements) {
//            if (element != messageText) {
//                element.getTransformation().translateTo(0, currentY);
//                currentY -= element.getHeight() + 10;
//            }
//        }
    }

    @Override
    public boolean isInside(double mouseX, double mouseY) {
        if (!visible) return false;

        float x = getTransformation().getTranslateX();
        float y = getTransformation().getTranslateY();

        boolean insideModal = (mouseX >= x - getWidth() / 2 && mouseX <= x + getWidth() / 2 &&
                mouseY >= y - getHeight() / 2 && mouseY <= y + getHeight() / 2);

        if (insideModal) return true;

        return closeButton != null && closeButton.isInside(mouseX, mouseY);
    }

    public void addContentElement(UIElement element) {
        if (element == null) return;

        contentElements.add(element);
        addChildren(element);

        layoutElements();
    }

    public void removeContentElement(UIElement element) {
        if (element == null) return;

        contentElements.remove(element);
        children.remove(element);

        layoutElements();
    }

    public void clearContent() {
        for (RenderableObject element : contentElements) {
            children.remove(element);
        }
        contentElements.clear();
        layoutElements();
    }

    public void setTitle(String title) {
        if (titleText != null) {
            titleText.setContent(title);
        }
    }

    @Override
    public void setWidth(float width){
        super.setWidth(width);
        if(hasHeader) header.setWidth(width);
    }

    public void setMessage(String message) {
        if (messageText != null) {
            messageText.setContent(message);
        }
    }



    public void toggleVisibility() {
        setVisible(!visible);
    }

    public ModalTextAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(ModalTextAlignment alignment) {
        this.alignment = alignment;
        layoutElements();
    }

    public static class ModalHeader extends UIElement{
        public ModalHeader(float posX, float posY, float width, float height ) {
            super(posX , posY , width , height);
        }


        @Override
        public boolean isInside(double mouseX, double mouseY) {
          return background.isInside(getTransformation().getTranslateX(), getTransformation().getTranslateY() , getWidth() ,getHeight() ,mouseX , mouseY);
        }
    }

    public enum ModalTextAlignment{
        CENTER , TOP_CENTER , BOTTOM_CENTER , RIGHT_CENTER , LEFT_CENTER,
        TOP_LEFT, BOTTOM_LEFT,TOP_RIGHT , BOTTOM_RIGHT
    }

    public static class Builder<T extends Builder<T>> extends UIElement.Builder<T> {
        private String title = "Hazem";
        private String message = "";
        protected float backgroundOpacity = 0.7f;
        protected float childrenOpacity = 0.7f;
        private float[] titleColor = Colors.WHITE;
        private float[] messageColor = Colors.BLACK;
        private int titleSize;
        private int messageSize = 10;
        private boolean hasHeader = true;
        private float headerHeight = 20f;
        private UIBackground headerBackground;
        private float[] headerColor = Colors.BLUE;
        private final List<RenderableObject> headerContent = new ArrayList<>();
        private boolean hasCloseButton = true;
        private ModalTextAlignment alignment = ModalTextAlignment.CENTER;
        private Consumer<Modal> onClose = null;
        private final List<ModalButtonConfig> buttons = new ArrayList<>();
        private final List<RenderableObject> contentElements = new ArrayList<>();


        @Override
        protected T self() {
            return (T) this;
        }

        public T title(String title) {
            this.title = title;
            return self();
        }

        public T message(String message) {
            this.message = message;
            return self();
        }

        public T headerColor(float[] color) {
            this.headerColor = color;
            return self();
        }

        public T titleColor(float[] color) {
            this.titleColor = color;
            return self();
        }

        public T messageColor(float[] color) {
            this.messageColor = color;
            return self();
        }

        public T titleSize(int size) {
            this.titleSize = size;
            return self();
        }

        public T messageSize(int size) {
            this.messageSize = size;
            return self();
        }

        public T headerHeight(float height) {
            this.headerHeight = height;
            return self();
        }

        public T hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return self();
        }

        public T headerBackground(UIBackground background) {
            this.headerBackground = background;
            return self();
        }

        public T addHeaderContent(RenderableObject ...content) {
            headerContent.addAll(Arrays.asList(content));
            return self();
        }

        @Override
        public T backgroundOpacity(float backgroundOpacity){
            this.backgroundOpacity = backgroundOpacity;
            return self();
        }
        @Override
        public T childrenOpacity(float childrenOpacity){
            this.childrenOpacity = childrenOpacity;
            return self();
        }

        public T hasCloseButton(boolean hasCloseButton) {
            this.hasCloseButton = hasCloseButton;
            return self();
        }

        public T onClose(Consumer<Modal> onClose) {
            this.onClose = onClose;
            return self();
        }

        public T addButton(String text , Consumer<Modal> action , float[] color) {
            return addButton(90 ,30 , text , action , color);
        }

        public T addButton(String text , Consumer<Modal> action , float[] color , float[] textColor) {
            return addButton(90 ,30 , text , action , color ,textColor );
        }

        public T addButton(float width , float height ,String text, Consumer<Modal> action, float[] color) {
            buttons.add(new ModalButtonConfig(width , height , text, action, color, Colors.WHITE));
            return self();
        }

        public T addButton(float width , float height , String text, Consumer<Modal> action, float[] color, float[] textColor) {
            buttons.add(new ModalButtonConfig(width , height ,text , action, color, textColor));
            return self();
        }

        public T addContentElement(RenderableObject element) {
            contentElements.add(element);
            return self();
        }

        public T setTextAlignment(ModalTextAlignment alignment){
            this.alignment = alignment;
            return self();
        }

        public T addContentElements(List<UIElement> elements) {
            contentElements.addAll(elements);
            return self();
        }

        public Modal build() {
            if(width == 0) width =150;
            if(height == 0) height = 150;
            return new Modal(this);
        }
    }

    private record ModalButtonConfig(float width , float height , String text, Consumer<Modal> action, float[] color, float[] textColor) {
    }
}