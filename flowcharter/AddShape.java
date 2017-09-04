package flowcharter;

import java.util.Arrays;

public class AddShape {

    private int shapeLength = 1;
    private Shape[] shape = new Shape[shapeLength];

    /**
     * Method add the object of Shape subclasses in an array in the class.
     *
     * @param sh
     */
    public void add(Shape sh) {
        shape[shapeLength - 1] = sh;
        Shape shapeTemp[] = Arrays.copyOf(shape, shapeLength + 1);
        shape = shapeTemp;
        shapeLength++;
    }

    /**
     * return the array of shapes.
     *
     * @return Shape []
     */
    public Shape[] getShape() {
        return shape;
    }

    public int getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(int i) {
        shapeLength = i;
    }
}
