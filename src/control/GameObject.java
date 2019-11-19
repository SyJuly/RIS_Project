package control;

public abstract class GameObject {

    public int x, y;
    public int speedX, speedY;

    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }
}
