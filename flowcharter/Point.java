package flowcharter;

import java.util.Arrays;

/**
 *
 * @author Sara
 */
public class Point {

    private int x;
    private int y;
    private final int index;
    private int pointLength = 1;
    private Point nextPoint[] = new Point[pointLength];
    private final Shape shapeBelong;

    private int prePointLength = 1;
    private Point previousPoint[] = new Point[prePointLength];

    /**
     * give any object of this class x and y parameter.
     *
     * @param x
     * @param y
     * @param sh
     * @param index
     */
    public Point(int x, int y, Shape sh, int index) {
        this.x = x;
        this.y = y;
        nextPoint[0] = null;
        previousPoint[0] = null;
        shapeBelong = sh;
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point[] getNextPoint() {
        return nextPoint;
    }

    public int getNextPointLength() {
        return pointLength;
    }

    public void setNextPointLength(int i) {
        pointLength = i;
    }

    public Shape getShape() {
        return shapeBelong;
    }

    public int getPrePointLength() {
        return prePointLength;
    }

    public Point[] getPrePoint() {
        return previousPoint;
    }

    /**
     * Add a point in the array of points, as next part its connected. parameter
     * of method is a Point.
     *
     * @param p
     */
    public void addConnection(Point p) {

        nextPoint[pointLength - 1] = p;
        Point tempPoint[] = Arrays.copyOf(nextPoint, pointLength + 1);
        nextPoint = tempPoint;
        pointLength++;
    }

    public boolean equals(Point p) {
        if (x == p.x) {
            if (y == p.y) {
                return true;
            }
        }
        return false;
    }

    public Point findPoint(int x, int y) {

        Point p = null;

        for (int i = 0; i < DrawPanel.addShape.getShapeLength() - 1; i++) {
            for (int j = 0; j < 8; j++) {
                if (DrawPanel.addShape.getShape()[i].pointArray[j].x == x) {
                    if (DrawPanel.addShape.getShape()[i].pointArray[j].y == y) {
                        p = DrawPanel.addShape.getShape()[i].pointArray[j];
                        break;
                    }
                }
            }
        }
        return p;
    }

    public void addPreConnection(Point p) {
        previousPoint[prePointLength - 1] = p;
        Point tempPoint[] = Arrays.copyOf(previousPoint, prePointLength + 1);
        previousPoint = tempPoint;
        prePointLength++;
    }

    public void changeNextPoint(Point p) {
        for (int i = 0; i < pointLength - 1; i++) {
            if (p.getShape().equals(nextPoint[i].getShape())) {
                if (p.index == nextPoint[i].index) {
                    nextPoint[i] = p;
                }
            }
        }
    }

    public void deleteConnection(Point p) {
        int indx = 0;

        for (int i = 0; i < pointLength - 1; i++) {
            if (p.equals(nextPoint[i])) {
                indx = i;
            }
        }
        for (int i = indx; i < pointLength - 1; i++) {
            nextPoint[i] = nextPoint[i + 1];
        }
    }

    public void deletePreConnection(Point p) {
        int indx = 0;

        for (int i = 0; i < prePointLength - 1; i++) {
            if (p.equals(previousPoint[i])) {
                indx = i;
            }
        }

        for (int i = indx; i < prePointLength - 1; i++) {
            previousPoint[i] = previousPoint[i + 1];
        }
    }
}
