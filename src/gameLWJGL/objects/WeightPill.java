package gameLWJGL.objects;

import gameLWJGL.collision.Collision;
import gameLWJGL.world.Camera;
import gameLWJGL.world.WorldUpdates;

import static org.lwjgl.opengl.GL11.*;

public class WeightPill extends GameObject implements IDynamicObject {

    public static final float SIZE = 0.1f;
    public static final float NUM_TRIANGLES = 10;
    public static final float PI_TWICE = 2.0f * (float)Math.PI;

    private WeightPillPool pool;
    private boolean hasBeenEaten = false;
    private boolean isActive = false;

    public WeightPill(float x, float y, String id, WeightPillPool pool) {
        super(x, y, SIZE, SIZE, id, ObjectType.PILL);
        this.pool = pool;
    }

    @Override
    public boolean update() {
        return hasBeenEaten;
    }

    @Override
    public void render(Camera camera) {
        float xOffset = camera.getXOffset(x);
        float yOffset = camera.getYOffset(y);

        glBegin(GL_TRIANGLE_FAN);
        glColor4f(0,0.5f,0.5f,0);
        glVertex2f(xOffset, yOffset); // center of circle
        for(int i = 0; i <= NUM_TRIANGLES;i++) {
            glVertex2f(
                    xOffset + (SIZE * (float)Math.cos(i *  PI_TWICE / NUM_TRIANGLES)),
                    yOffset + (SIZE * (float)Math.sin(i * PI_TWICE / NUM_TRIANGLES))
            );
        }
        glEnd();
    }

    @Override
    public void handleCollision(Collision collisionData) {
        if(hasBeenEaten) return;
        GameObject collidingGameObject = collisionData.gameObjects[0].id == id ? collisionData.gameObjects[1] : collisionData.gameObjects[0];
        if(collidingGameObject.objectType == ObjectType.PLAYER){
            Player player = (Player) collidingGameObject;
            player.gainWeight();
            hasBeenEaten = true;
            pool.resetPill(this);
            WorldUpdates.getInstance().addGameObjectToRemove(id);
        }
    }

    @Override
    public float[] getSpecifics() {
        return new float[0];
    }

    @Override
    public void setSpecifics(float[] specifics) { }

    @Override
    public boolean shouldBeDestroyed() {
        return hasBeenEaten;
    }

    @Override
    public GameObject getGameObject() {
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean setActive) {
        isActive = setActive;
        if(setActive){
            hasBeenEaten = false;
        }
    }
}
