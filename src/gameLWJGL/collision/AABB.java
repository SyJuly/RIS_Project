package gameLWJGL.collision;

import gameLWJGL.objects.GameObject;

public class AABB {

    public static Collision getCollision(GameObject a, GameObject b) // AABB - AABB collision
    {
        boolean isColliding = false;
        if(Math.abs(a.x - b.x) < a.width + b.width)
        {
            if(Math.abs(a.y - b.y) < a.height + b.height)
            {
                isColliding = true;
            }
        }
        return new Collision( isColliding, new GameObject[]{a, b});
    }

}
