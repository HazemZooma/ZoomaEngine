package scene;

import com.jogamp.opengl.GL2;
import command.Command;
import command.UI.UIManagerInputCommand;
import engine.core.input.SceneInputContext;
import engine.graphics.Colors;
import engine.graphics.RenderableObject;
import engine.ui.UIManager;
import engine.ui.core.Modal;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Scene<T extends RenderableObject> {

    protected final List<T> renderableObjects = new ArrayList<>();
    protected final UIManager uiManager;
    protected final SceneInputContext inputContext = new SceneInputContext();
    protected float[] sceneBackgroundColor = Colors.BLACK;
    protected boolean initialized = false;
    protected boolean resumed = false;
    protected SceneType sceneType;

    protected Scene() {
        this.uiManager = new UIManager();
    }

    @SafeVarargs
    protected Scene(T... renderableObjects) {
        this.renderableObjects.addAll(Arrays.asList(renderableObjects));
        this.uiManager = new UIManager();
    }

    public SceneType getSceneType() {
        return sceneType;
    }

    public SceneInputContext getInputContext() {
        return inputContext;
    }


    @SafeVarargs
    public final void add(T... renderableObjects) {
        if (renderableObjects != null) this.renderableObjects.addAll(Arrays.asList(renderableObjects));
    }

    public void add(List<T> list) {
        if (list != null) renderableObjects.addAll(list);
    }

    public void addFirst(T renderableObject) {
        this.renderableObjects.addFirst(renderableObject);
    }

    public void removeIf(Predicate<T> predicate) {
        renderableObjects.removeIf(predicate);
    }

    public void forEach(Consumer<T> consumer) {
        renderableObjects.forEach(consumer);
    }


    public List<T> getRenderableObjects() {
        return Collections.unmodifiableList(renderableObjects);
    }

    public List<T> getEntitiesOfType(Class<T> clazz) {
        return renderableObjects.stream()
                .filter(clazz::isInstance)
                .toList();
    }


    public void initialize() {
        if(!initialized) {
            Command uiManagerInputCommand = new UIManagerInputCommand(uiManager, inputContext.getMouseState());
            inputContext.bindMouse(MouseEvent.NOBUTTON, uiManagerInputCommand);
            inputContext.bindMouse(MouseEvent.BUTTON1, uiManagerInputCommand);
        }
    }

    public void onResume(){

    }

    public void onExit() {
        uiManager.getUIElements().forEach(e -> {
            if(e instanceof Modal modal) modal.hide();
        });
        resumed = true;
    }



    public void render(GL2 gl) {
        gl.glClearColor(
                sceneBackgroundColor[0],
                sceneBackgroundColor[1],
                sceneBackgroundColor[2],
                1.0f
        );

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        if(!initialized) {
            initialize();
            initialized = true;
        }
        else if(resumed){
            onResume();
            resumed = false;
        }
        for (T renderableObject : renderableObjects) {
            renderableObject.draw(gl);
        }
        uiManager.draw(gl);
    }
}
