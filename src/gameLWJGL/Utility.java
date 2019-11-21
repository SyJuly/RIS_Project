package gameLWJGL;

public class Utility {

    public static Model GetTriangle(float size){
        float[] vertices = new float[]{
                -1 * size, -1 * size, 0,
                0,1 * size,0,
                1 * size,-1 * size,0
        };
        return new Model(vertices);
    }

    public static Model GetSquare(float size){
        return new Model(getSquareVertices(size));
    }

    public static Model GetSquareWithTexture(float size){

        float[] vertices = getSquareVertices(size);

        float[] texture = new float[]{
                0,0,
                1,0,
                1,1,
                1,1,
                0,1,
                0,0,
        };
        return new Model(vertices, texture);
    }

    private static float[] getSquareVertices(float size) {
        return new float[]{
                -1 * size, 1 * size, 0, // TOP LEFT
                1 * size,1 * size, 0, // TOP RIGHT
                1 * size,-1 * size, 0, // BOTTOM RIGHT

                1 * size, -1 * size, 0, // BOTTOM RIGHT
                -1 * size, -1 * size, 0, // BOTTOM LEFT
                -1 * size, 1 *  size, 0 // TOP LEFT
        };
    }
}
