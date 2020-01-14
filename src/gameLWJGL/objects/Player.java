package gameLWJGL.objects;

import gameLWJGL.collision.Collision;
import gameLWJGL.collision.CollisionDirection;
import gameLWJGL.input.IMoveable;
import gameLWJGL.physics.PhysicsObject;
import gameLWJGL.world.Camera;
import gameLWJGL.world.events.PrintEvent;
import gameLWJGL.world.events.WorldEvents;

import static org.lwjgl.opengl.GL11.*;

public class Player extends PhysicsObject implements IMoveable {


    public static final double JUMP_STRENGTH = 0.3f;
    public static final double SPEED = 0.03f;
    public static final float INITIAL_SIZE = 0.05f;
    public static final float WEIGHT_DIFF = 0.02f;
    public static final float[] DEFAULT_COLOR = new float[]{1,1,1};
    private WorldEvents eventHandler;
    private float xDelta;
    private float[] color;

    public Player(float x, float y, String id){
        super(x,y, INITIAL_SIZE, INITIAL_SIZE, id, ObjectType.PLAYER);
        this.color = DEFAULT_COLOR;
    }

    public Player(float x, float y, String id, float[] color, WorldEvents eventHandler){
        super(x,y, INITIAL_SIZE, INITIAL_SIZE, id, ObjectType.PLAYER);
        this.color = color;
        this.eventHandler = eventHandler;
    }

    @Override
    public boolean update() {
        boolean physicsHasBeenUpdated = super.update();
        x += xDelta;
        isOnGround = false;
        return true; //TODO: could be improved
    }

    @Override
    public void render(Camera camera){

        float xOffset = camera.getXOffset(x);
        float yOffset = camera.getYOffset(y);
        glBegin(GL_QUADS);

        glColor4f(color[0],color[1],color[2],0);
        glVertex2f(-width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, height + yOffset);
        glVertex2f(width + xOffset, -height + yOffset);
        glVertex2f(-width + xOffset, -height + yOffset);
        glEnd();
    }

    public void gainWeight(){
        float prevWeight = weight;
        weight += WEIGHT_DIFF;
        changeWeight(prevWeight);
    }

    public void looseWeight(){
        float prevWeight = weight;
        weight = Math.max(weight - WEIGHT_DIFF, 0);
        changeWeight(prevWeight);
        if(weight == 0){
            eventHandler.addEvent(new PrintEvent("PLAYER " + id + " LOST, HA YOU LOOSER.", System.currentTimeMillis()));
        }
    }

    private void changeWeight(float prevWeight){
        width = width - prevWeight + weight;
        height = height - prevWeight + weight;
        eventHandler.addEvent(new PrintEvent("Player " + id + " has a weight of " + weight + ".", System.currentTimeMillis()));

    }

    @Override
    public void move(int xDirection, int yDirection) {
        if(yDirection > 0){
            jump(JUMP_STRENGTH);
        }
        xDelta = (float) SPEED * xDirection;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public float[] getSpecifics() {
        float[] physicsSpecifics = super.getSpecifics();
        float[] specifics = new float[physicsSpecifics.length + color.length];
        for(int i = 0; i < physicsSpecifics.length; i++){
            specifics[i] = physicsSpecifics[i];
        }
        specifics[physicsSpecifics.length] = color[0];
        specifics[physicsSpecifics.length + 1] = color[1];
        specifics[physicsSpecifics.length + 2] = color[2];
        return specifics;
    }

    @Override
    public void setSpecifics(float[] specifics) {
        color = new float[]{specifics[specifics.length - 3], specifics[specifics.length - 2], specifics[specifics.length - 1]};
        float[] physicsSpecifics = new float[specifics.length - color.length];
        for(int i = 0; i < physicsSpecifics.length; i++){
            physicsSpecifics[i] = specifics[i];
        }
        super.setSpecifics(physicsSpecifics);
    }

    @Override
    public void handleCollision(Collision collisionData) {
        super.handleCollision(collisionData);
        GameObject collidingGameObject = collisionData.gameObjects[0].id == id ? collisionData.gameObjects[1] : collisionData.gameObjects[0];
        if((collidingGameObject.objectType == ObjectType.AI || collidingGameObject.objectType == ObjectType.PLAYER)
                && collisionData.aMetBs != CollisionDirection.DOWNSIDE){
            looseWeight();
        }
    }
}
