package gameLWJGL;

public abstract class GameObject {

    public float x, y;
    public float speed = 0;

    protected GameObject(float x, float y){
        this.x = x;
        this.y = y;
    }

    public abstract void update();
    public abstract void render();
}

