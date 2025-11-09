package scene;

import com.jogamp.opengl.GL2;
import engine.core.input.SceneInputContext;
import engine.grahpics.RenderableObject;
import entities.base.Updatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Scene extends RenderableObject implements Updatable {

    protected final List<RenderableObject> renderableObjects = new ArrayList<>();
    protected final SceneInputContext inputContext = new SceneInputContext();

    protected Scene() {}

    protected Scene(RenderableObject... renderableObjects) {
        this.renderableObjects.addAll(Arrays.asList(renderableObjects));
    }

    public SceneInputContext getInputContext() {
        return inputContext;
    }


    public void addEntity(RenderableObject renderableObject) {
        renderableObjects.add(renderableObject);
    }

    public void add(RenderableObject renderableObject) {
        if (renderableObject != null) renderableObjects.add(renderableObject);
    }

    public void addAll(List<RenderableObject> list) {
        if (list != null) renderableObjects.addAll(list);
    }

    public void addAll(RenderableObject... renderableObjects) {
        if (renderableObjects != null) this.renderableObjects.addAll(Arrays.asList(renderableObjects));
    }

    public void removeEntity(RenderableObject renderableObject) {
        renderableObjects.remove(renderableObject);
    }

    public List<RenderableObject> getRenderableObjects() {
        return renderableObjects;
    }

    public <T extends RenderableObject> List<T> getEntitiesOfType(Class<T> clazz) {
        return renderableObjects.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }


    public void onEnter() {}

    public void onExit() {}

    @Override
    public void update(GL2 gl) {
        for (RenderableObject renderableObject : renderableObjects) {
            if (renderableObject instanceof Updatable updatable) {
                updatable.update(gl);
            }
        }
    }

    public void onDraw(GL2 gl) {
        for (RenderableObject renderableObject : renderableObjects) {
            renderableObject.draw(gl);
        }
    }

    public void render(GL2 gl) {
        update(gl);
        draw(gl);
    }
}
