package engine.ui.factory;

import engine.graphics.Colors;
import engine.shapes.ShapeType;
import engine.ui.Events.UIEvent;
import engine.ui.core.Button;
import engine.ui.core.Modal;
import engine.ui.core.UIElement;

import java.util.function.Consumer;

public class ModalFactory {

    public static Builder builder() {
        return new Builder();
    }

    public static Modal createInfoModal(String title, String message, Consumer<Modal> onClose) {
        return builder()
                .headerHeight(20)
                .messageSize(10)
                .titleSize(5)
                .title(title)
                .hasCloseButton(false)
                .message(message)
                .headerColor(Colors.BLUE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton("OK", onClose, Colors.WHITE, Colors.BLUE)
                .build();
    }

    public static Modal createInfoModal(String title, String message) {
        return createInfoModal(title, message, null);
    }

    public static Modal createInfoModalWithoutButton(String title, String message, float[] messageColor, float[] headerColor, float modalWidth, float modalHeight, float headerHeight) {
        String[] lines = message.split("\n");
        int lineHeight = (int) (modalHeight / lines.length);


        return builder()
                .size(modalWidth, modalHeight)
                .headerHeight(headerHeight)
                .messageSize(lineHeight)
                .title(title)
                .message(message)
                .headerColor(headerColor)
                .titleColor(Colors.BLACK)
                .messageColor(messageColor)
                .build();

    }

    public static Modal createInfoModalWithoutButton(String title, String message, float[] messageColor, float[] headerColor) {
        return createInfoModalWithoutButton(title, message, messageColor, headerColor, 200, 100, 20);
    }

    public static Modal createInfoModalWithoutButton(String title, String message) {
        return createInfoModalWithoutButton(title, message, Colors.BLACK, Colors.CLOUD_GREY);
    }

    public static Modal createConfirmationModal(String title, String message, Consumer<UIEvent> onConfirm, Consumer<UIEvent> onCancel , String onConfirmText , String onCancelText , float width , float height) {

        Modal modal = builder()
                .size(width, height)
                .title(title)
                .message(message)
                .headerColor(Colors.BRIGHT_GREEN)
                .titleColor(Colors.WHITE)
                .hasCloseButton(false)
                .messageColor(Colors.BLACK)
                .childrenOpacity(1)
                .backgroundOpacity(1)
                .build();

        Button confirmButton = ButtonFactory.builder()
                .at(-width/3.5f , -height/3.2f)
                .size(width/3 , height /5)
                .textContent(onConfirmText)
                .onClick(onConfirm)
                .geometricBackground(ShapeType.ROUNDED_RECTANGLE , Colors.DARK_GREEN)
                .build();

        Button cancelButton = ButtonFactory.builder()
                .at(width/3.5f , -height/3.2f)
                .size(width/3 , height /5)
                .textContent(onCancelText)
                .onClick(onCancel)
                .geometricBackground(ShapeType.ROUNDED_RECTANGLE , Colors.RED)
                .build();

        modal.addChildren(confirmButton, cancelButton);

        return modal;
    }

    public static Modal createConfirmationModal(String title, String message, Consumer<UIEvent> onConfirm, Consumer<UIEvent> onCancel, String onConfirmText, String onCancelText) {

        return createConfirmationModal(title , message , onConfirm , onCancel , onConfirmText , onCancelText , 250 , 150);
    }

    public static Modal createConfirmationModal(String title, String message,
                                                Consumer<UIEvent> onConfirm,
                                                Consumer<UIEvent> onCancel) {
        return createConfirmationModal(title, message, onConfirm, onCancel, "Yes ", " No");
    }

    public static Modal createConfirmationModal(String title, String message,
                                                Consumer<UIEvent> onConfirm) {
        return createConfirmationModal(title, message, onConfirm, e -> {
            ((Modal) e.getTarget().getParent()).hide();
        });
    }

    public static Modal createWarningModal(String title, String message,
                                           Consumer<Modal> onAcknowledge) {
        return builder()
                .title(title)
                .message(message)
                .headerColor(Colors.ORANGE)
                .titleColor(Colors.BLACK)
                .messageColor(Colors.BLACK)
                .addButton("I Understand", onAcknowledge, Colors.ORANGE, Colors.BLACK)
                .build();
    }

    public static Modal createWarningModal(String title, String message) {
        return createWarningModal(title, message, Modal::hide);
    }

    public static Modal createErrorModal(String title, String message,
                                         Consumer<Modal> onClose) {
        return builder()
                .title(title)
                .message(message)
                .headerColor(Colors.RED)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton("Close", onClose, Colors.RED)
                .build();
    }

    public static Modal createErrorModal(String title, String message) {
        return createErrorModal(title, message, Modal::hide);
    }

    public static Modal createSuccessModal(String title, String message,
                                           Consumer<Modal> onContinue) {
        return builder()
                .title(title)
                .message(message)
                .headerColor(Colors.LIGHT_GREEN)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton("Continue", onContinue, Colors.DARK_GREEN)
                .build();
    }

    public static Modal createInputModal(String title, String prompt,
                                         Consumer<Modal> onSubmit,
                                         Consumer<Modal> onCancel) {

        return builder()
                .title(title)
                .message(prompt)
                .headerColor(Colors.PURPLE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton("Submit", onSubmit, Colors.PURPLE)
                .addButton("Cancel", onCancel, Colors.GRAY)
                .build();
    }

    public static Modal createLoadingModal(String title, String message) {
        return builder()
                .title(title)
                .message(message)
                .headerColor(Colors.BLUE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .hasCloseButton(false)
                .hasHeader(true)
                .build();
    }

    public static Modal createMinimalModal(String message, Consumer<Modal> onClose) {
        return builder()
                .message(message)
                .hasHeader(false)
                .hasCloseButton(true)
                .messageColor(Colors.BLACK)
                .onClose(onClose)
                .build();
    }


    public static Modal createDialogModal(String title, String message,
                                          String confirmText, String cancelText,
                                          Consumer<Modal> onConfirm,
                                          Consumer<Modal> onCancel) {
        return builder()
                .title(title)
                .message(message)
                .size(400, 200)
                .headerColor(Colors.BLUE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton(confirmText, onConfirm, Colors.BLUE)
                .addButton(cancelText, onCancel, Colors.GRAY)
                .build();
    }

    public static Modal createThemedModal(String title, String message,
                                          float[] themeColor,
                                          String buttonText,
                                          Consumer<Modal> onAction) {
        return builder()
                .title(title)
                .message(message)
                .headerColor(themeColor)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton(buttonText, onAction, themeColor)
                .build();
    }

    public static Modal createCustomButtonModal(String title, String message,
                                                String[] buttonTexts,
                                                Consumer<Modal>[] buttonActions,
                                                float[][] buttonColors) {
        Builder builder = builder()
                .title(title)
                .message(message)
                .headerColor(Colors.BLUE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK);

        for (int i = 0; i < buttonTexts.length; i++) {
            float[] color = i < buttonColors.length ? buttonColors[i] : Colors.BLUE;
            Consumer<Modal> action = i < buttonActions.length ? buttonActions[i] : Modal::hide;
            builder.addButton(buttonTexts[i], action, color);
        }

        return builder.build();
    }

    public static Modal createCenteredModal(String title, String message,
                                            float width, float height,
                                            Consumer<Modal> onClose) {
        return builder()
                .title(title)
                .message(message)
                .size(width, height)
                .headerColor(Colors.BLUE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton("Close", onClose, Colors.BLUE)
                .build();
    }


    public static Modal createAlertModal(String message, Consumer<Modal> onOK) {
        return builder()
                .title("Alert")
                .message(message)
                .headerColor(Colors.ORANGE)
                .titleColor(Colors.BLACK)
                .messageColor(Colors.BLACK)
                .addButton("OK", onOK, Colors.ORANGE, Colors.BLACK)
                .build();
    }

    public static Modal createIconModal(String title, String message,
                                        float[] iconColor,
                                        Consumer<Modal> onAction) {

        return builder()
                .title(title)
                .message(message)
                .headerColor(Colors.BLUE)
                .titleColor(Colors.WHITE)
                .messageColor(Colors.BLACK)
                .addButton("OK", onAction, Colors.BLUE)
                .build();
    }

    public static class Builder extends Modal.Builder<Builder> {


        @Override
        protected Builder self() {
            return this;
        }
    }
}