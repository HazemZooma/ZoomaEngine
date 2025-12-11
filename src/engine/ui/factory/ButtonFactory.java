package engine.ui.factory;

import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.ui.Events.UIEvent;
import engine.ui.background.GeometricBackground;
import engine.ui.core.Button;
import engine.shapes.ShapeType;
import engine.shapes.Triangle;

import java.util.function.Consumer;

public class ButtonFactory {

    public static Button createGeometricButton(float x, float y, float width, float height,
                                               ShapeType shapeType, float[] color,
                                               String text, Consumer<UIEvent> onClick) {

        GeometricBackground background = new GeometricBackground.Builder()
                .shapeType(shapeType)
                .color(color)
                .build();


        return builder()
                .at(x, y)
                .size(width, height)
                .background(background)
                .textContent(text)
                .onClick(onClick)
                .build();

    }


    public static Button createRectangularButton(float x, float y, float width, float height,
                                                 float[] color, String text, Consumer<UIEvent> onClick) {

        return createGeometricButton(x, y, width, height, ShapeType.RECTANGLE, color, text, onClick);
    }


    public static Button createCircularButton(float x, float y, float diameter,
                                              float[] color, String text, Consumer<UIEvent> onClick) {

        return createGeometricButton(x, y, diameter, diameter, ShapeType.CIRCLE, color, text, onClick);
    }


    public static Button createRoundedButton(float x, float y, float width, float height,
                                             float radius, float[] color, String text,
                                             Consumer<UIEvent> onClick) {

        GeometricBackground background = new GeometricBackground.Builder()
                .shapeType(ShapeType.ROUNDED_RECTANGLE)
                .color(color)
                .radius(radius)
                .build();

        return builder()
                .at(x, y)
                .size(width, height)
                .background(background)
                .textContent(text)
                .onClick(onClick)
                .build();
    }


//    public static Button createImageButton(float x, float y, float width, float height,
//                                           String imagePath, String text, Consumer<UIEvent> onClick) {
//
//        ImageBackground background = new ImageBackground.Builder()
//                .imagePath(imagePath)
//                .build();
//
//        return new Button.Builder()
//                .at(x, y)
//                .Size(width, height)
//                .background(background)
//                .textContent(text)
//                .onClick(onClick)
//                .build();
//    }

    public static Button createPrimaryButton(float x ,float y , String text , Consumer<UIEvent> onClick){
        return createPrimaryButton(x, y , 120 , 30 , text ,onClick);
    }

    public static Button createPrimaryButton(float x, float y, float width, float height,
                                             String text, Consumer<UIEvent> onClick) {

        return  builder()
                .at(x, y)
                .size(width, height)
                .background(new GeometricBackground.Builder()
                        .shapeType(ShapeType.ROUNDED_RECTANGLE)
                        .color(Colors.BLUE)
                        .radius(8f)
                        .build())
                .textContent(text)
                .textColor(Colors.WHITE)
                .onClick(onClick)
                .onHoverEnter(e -> {
                    if (e.getTarget() instanceof Button button) {
                        button.getTransformation().setScale(1.2f , 1.2f);
                        if (button.getBackground() instanceof GeometricBackground gb) {
                            gb.setColor(Colors.lightenColor(Colors.BLUE));
                        }
                    }
                })
                .onHoverExit(e -> {
                    if (e.getTarget() instanceof Button button) {
                        button.getTransformation().setScale(1f , 1f);
                        if (button.getBackground() instanceof GeometricBackground gb) {
                            gb.setColor(Colors.BLUE);
                        }
                    }
                })
                .build();
    }

    public static Button createSecondaryButton(float x, float y, float width, float height,
                                               String text, Consumer<UIEvent> onClick) {

        return builder()
                .at(x, y)
                .size(width, height)
                .background(new GeometricBackground.Builder()
                        .shapeType(ShapeType.RECTANGLE)
                        .color(Colors.LIGHT_GRAY)
                        .build())
                .textContent(text)
                .textColor(Colors.BLACK)
                .onClick(onClick)
                .onHoverEnter(e -> {
                    if (e.getTarget() instanceof Button button) {
                        if (button.getBackground() instanceof GeometricBackground gb) {
                            gb.setColor(new float[]{0.8f, 0.8f, 0.8f});
                        }
                    }
                })
                .onHoverExit(e -> {
                    if (e.getTarget() instanceof Button button) {
                        if (button.getBackground() instanceof GeometricBackground gb) {
                            gb.setColor(Colors.LIGHT_GRAY);
                        }
                    }
                })
                .build();
    }


    public static Button createOutlineButton(float x, float y, float width, float height,
                                             String text, Consumer<UIEvent> onClick) {

        return builder()
                .at(x, y)
                .size(width, height)
                .background(new GeometricBackground.Builder()
                        .shapeType(ShapeType.RECTANGLE)
                        .color(Colors.BLACK)
                        .build())
                .textContent(text)
                .textColor(Colors.BLUE)
                .onClick(onClick)
                .onHoverEnter(e -> {
                    if (e.getTarget().getBackground() instanceof GeometricBackground background) {
                        background.setColor(Colors.WHITE);
                    }
                })
                .onHoverExit(e -> {
                    if (e.getTarget().getBackground() instanceof GeometricBackground background) {
                        background.setColor(Colors.BLUE);
                    }
                })
                .build();
    }

    public static Button createBackButtonWithArrow(float x, float y , float radius , float[] color , Consumer<UIEvent> onClick) {
        Triangle arrow = new  Triangle(-radius/3.5f, 0 , radius * 1.2f  , radius * 1.2f , color == Colors.BLUE? Colors.BLACK : Colors.BLUE);
        arrow.getTransformation().rotateAroundSelf(90);


        return createBackButtonWithIcon(x,y,radius , color , onClick , arrow);
    }

    public static Button createBackButtonWithIcon(float x, float y, float radius , float[] color ,Consumer<UIEvent> onClick , RenderableObject icon) {
        Button button = createCircularButton(x, y , radius * 2 , color , null , onClick);

        button.setOnHoverEnter(b -> {
            Button but = (Button) b.getTarget();
            but.getTransformation().setScale(1.2f , 1.2f);
            if(but.getBackground() instanceof GeometricBackground bg) {
                bg.setColor(Colors.lightenColor(color));
            }
        } );
        button.setOnHoverExit(
                b -> {
                    Button but = (Button) b.getTarget();
                    but.getTransformation().setScale(1f, 1f);
                    if(but.getBackground() instanceof GeometricBackground bg) {
                        bg.setColor(color);
                    }
                });

        if(icon != null) {
            button.addChildren(icon);
        }

        return button;
    }

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder extends Button.Builder<Builder> {

        public Builder geometricBackground(ShapeType shapeType, float[] color) {
            GeometricBackground background = new GeometricBackground.Builder()
                    .shapeType(shapeType)
                    .color(color)
                    .build();
            background(background);

            return this;
        }

        public Builder geometricBackground(ShapeType shapeType, float[] color, float radius) {
            GeometricBackground background = new GeometricBackground.Builder()
                    .shapeType(shapeType)
                    .color(color)
                    .radius(radius)
                    .build();
            background(background);

            return self();
        }


//        public Builder imageBackground(String imagePath) {
//            ImageBackground background = new ImageBackground.Builder()
//                    .imagePath(imagePath)
//                    .build();
//            return (Builder) background(background);
//        }


        public Builder withHoverEffect(float[] hoverColor, float[] originalColor) {
            onHoverEnter(e -> {
                if (e.getTarget() instanceof Button button) {
                    if (button.getBackground() instanceof GeometricBackground gb) {
                        gb.setColor(hoverColor);
                    }
                }
            });

            onHoverExit(e -> {
                if (e.getTarget() instanceof Button button) {
                    if (button.getBackground() instanceof GeometricBackground gb) {
                        gb.setColor(originalColor);
                    }
                }
            });

            return this;
        }


        public Builder primaryStyle() {
            if (this.background instanceof GeometricBackground gb) {
                gb.setColor(Colors.BLUE);
                gb.setShapeType(ShapeType.ROUNDED_RECTANGLE);
                gb.setRadius(8f);
            }
            textColor(Colors.WHITE);
            return this;
        }


        public Builder secondaryStyle() {
            if (this.background instanceof GeometricBackground gb) {
                gb.setColor(Colors.LIGHT_GRAY);
                gb.setShapeType(ShapeType.RECTANGLE);
            }
            textColor(Colors.BLACK);
            return this;
        }

        @Override
        protected Builder self(){
            return this;
        }

        @Override
        public Button build() {
            if (width <= 0) width = 100f;
            if (height <= 0) height = 40f;

            if (background == null) {
                background = new GeometricBackground.Builder()
                        .shapeType(ShapeType.RECTANGLE)
                        .color(Colors.BLUE)
                        .build();
            }

            return super.build();
        }
    }
}