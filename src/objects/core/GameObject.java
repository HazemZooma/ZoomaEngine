package objects.core;

import com.jogamp.opengl.GL2;
import engine.graphics.RenderableObject;
import engine.texture.Sprite;


public abstract class GameObject extends RenderableObject {
    protected String[] skinsPaths;
    protected Sprite[] sprites;
    protected boolean dead;
    protected Sprite currSprite;
    private static final String BASE_DIRECTORY = "resources/assets/textures/";

    public GameObject(float x , float y , String path){
        this.skinsPaths = new String[1];
        skinsPaths[0] = path;
        this.sprites = new Sprite[1];
        this.currSprite =  this.sprites[0] = new Sprite(x , y , skinsPaths[0]);


    }

    public GameObject(float x, float y,  String[] skinsPaths) {
        this.skinsPaths = new String[skinsPaths.length];
        this.sprites = new Sprite[skinsPaths.length];
        for (int i = 0 ; i< skinsPaths.length ; i++){
            sprites[i] = new Sprite(x , y , BASE_DIRECTORY + skinsPaths[i]);
        }
        this.currSprite = sprites[0];
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public float getWidth() {
        return currSprite.getWidth() * getTransformation().getScaleX();
    }

    @Override
    public float getHeight() {
        return currSprite.getHeight() * getTransformation().getScaleY();
    }

    public void switchSkin(int index){
        if(index >= 0 && index < sprites.length ){
            currSprite = sprites[index];
        }
    }

    public void setSize(float width , float height){
        this.currSprite.setWidth(width);
        this.currSprite.setHeight(height);
    }

    public abstract void update();

    public abstract void onCollision(GameObject other);

    @Override
    protected void onDraw(GL2 gl) {
        if (currSprite == null) return;
        currSprite.draw(gl);
    }
}
