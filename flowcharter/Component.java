package flowcharter;

/**
 *
 * @author Sara
 */
public interface Component {

    Text readText();

    void writeText(String message, String font, String style, int size);

    /**
     * Return the object belongs to which subclass
     *
     * @return
     */
    ShapeEnum type();

    void connection(Point p1, Point p2);

    Point[] createCoordination(int x, int y, int width, int height);

    /**
     * When changing a shape coordination, this method finds its center, and
     * then call coordinationByDragging.
     *
     * @param x
     * @param y
     */
    void changeCoords(int x, int y);

    /**
     * This method fill up points array; which hold x and y of 8 different point
     * on an object. These points give the exact location of the object on the
     * screen. points are the four corners and the middle of each side of the
     * rectangle. The parameters are the x and y of the center of the shape, and
     * width and height of the shape. Method takes its parameter from jDialog,
     * which open by clicking on the icon of the shape.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return Point []
     */
    Point[] coordinationByForm(int x, int y, int width, int height);

    /**
     * when an object is created this method will call for drawing it.
     * drawable() return an array with length 5. The first and second elements
     * are the x and y of the top-left corner of the rectangle. The third and
     * forth elements are the width and height of the rectangle. And the fifth
     * one is the image path.
     *
     * @return String[]
     */
    String[] drawable();

    void setLeftTag(String tag);

    void setRightTag(String tag);

    void setDownTag(String tag);

    String getLeftTag();

    String getRightTag();

    String getDownTag();
}
