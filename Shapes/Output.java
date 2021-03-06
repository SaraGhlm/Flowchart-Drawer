package Shapes;

import flowcharter.Shape;
import flowcharter.ShapeEnum;

/**
 *
 * @author Sara
 */
public class Output extends Shape {

    public Output(int x, int y, int width, int height) {
        super();
        XCenter = x;
        YCenter = y;
        this.height = height;
        this.width = width;
    }

    @Override
    public ShapeEnum type() {
        return ShapeEnum.Output;
    }

    /**
     * When changing a shape size, this method finds its center, and then call
     * coordinationByDragging.
     *
     * @param x
     * @param y
     */
    @Override
    public void changeCoords(int x, int y) {

        if (x > XCenter) {
            XCenter += x - pointArray[3].getX();
        }

        if (x < XCenter) {
            XCenter -= pointArray[7].getX() - x;
        }

        if (y > YCenter) {
            YCenter += y - pointArray[5].getY();
        }

        if (y < YCenter) {
            YCenter -= pointArray[1].getY() - y;
        }

        coordinationByForm(XCenter, YCenter, width, height);
    }

    @Override
    public String[] drawable() {

        int Width = this.width;
        int Height = this.height;

        drawArray[0] = Integer.toString(pointArray[0].getX());
        drawArray[1] = Integer.toString(pointArray[0].getY());
        drawArray[2] = Integer.toString(Width);
        drawArray[3] = Integer.toString(Height);
        drawArray[4] = "images/outputImg.PNG";

        return drawArray;
    }

}
