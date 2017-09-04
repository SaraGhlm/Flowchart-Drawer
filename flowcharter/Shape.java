package flowcharter;

/**
 *
 * @author Sara
 */
public class Shape implements Component {

    protected int XCenter;
    protected int YCenter;

    protected int width;
    protected int height;

    protected Point pointArray[];
    protected String[] drawArray;

    protected Text text;

    protected boolean watched;

    protected String leftTag;
    protected String rightTag;
    protected String downTag;

    boolean leftWatched = false;
    boolean rightWatched = false;
    boolean downWatched = false;

    public Shape() {
        this.rightTag = null;
        this.leftTag = null;
        this.downTag = null;
        this.drawArray = new String[5];
        this.pointArray = new Point[8];
        text = new Text(null, "Calibri (Body)", "PLAIN", 12);
        watched = false;
    }

    public void setXCenetr(int x) {
        XCenter = x;
    }

    public void setYCenetr(int y) {
        YCenter = y;
    }

    public int getXCenter() {
        return XCenter;
    }

    public int getYCenter() {
        return YCenter;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public ShapeEnum type() {
        return ShapeEnum.Default;
    }

    @Override
    public void connection(Point p1, Point p2) {
        p1.addConnection(p2);

    }

    public Point[] getPoint() {
        return pointArray;
    }

    @Override
    public Point[] createCoordination(int x, int y, int width, int height) {
        int X;
        int Y;
        int Width = width / 2;
        int Height = height / 2;

        this.width = width;
        this.height = height;

        if (200 + Width <= x) {
            X = x - Width;
            Y = y - Height;
            pointArray[0] = new Point(X, Y, this, 0);

            X = x - 3;
            Y = y - Height;
            pointArray[1] = new Point(X, Y, this, 1);

            X = x + Width;
            Y = y - Height;
            pointArray[2] = new Point(X, Y, this, 2);

            X = x + Width;
            Y = y;
            pointArray[3] = new Point(X, Y, this, 3);

            X = x + Width;
            Y = y + Height;
            pointArray[4] = new Point(X, Y, this, 4);

            X = x;
            Y = y + Height;
            pointArray[5] = new Point(X, Y, this, 5);

            X = x - Width;
            Y = y + Height;
            pointArray[6] = new Point(X, Y, this, 6);

            X = x - Width;
            Y = y;
            pointArray[7] = new Point(X, Y, this, 7);

        }

        if (200 <= x && x <= 200 + Width) {
            X = 200;
            Y = y - Height;
            pointArray[0] = new Point(X, Y, this, 0);

            X = 200 + Width;
            Y = y - Height;
            pointArray[1] = new Point(X, Y, this, 1);

            X = 200 + width;
            Y = y - Height;
            pointArray[2] = new Point(X, Y, this, 2);

            X = 200 + width;
            Y = y;
            pointArray[3] = new Point(X, Y, this, 3);

            X = 200 + width;
            Y = y + Height;
            pointArray[4] = new Point(X, Y, this, 4);

            X = 200 + Width;
            Y = y + Height;
            pointArray[5] = new Point(X, Y, this, 5);

            X = 200;
            Y = y + Height;
            pointArray[6] = new Point(X, Y, this, 6);

            X = 200;
            Y = y;
            pointArray[7] = new Point(X, Y, this, 7);
        }
        return pointArray;
    }

    @Override
    public Point[] coordinationByForm(int x, int y, int width, int height) {

        int X;
        int Y;
        int Width = width / 2;
        int Height = height / 2;

        this.width = width;
        this.height = height;

        if (200 + Width <= x) {
            X = x - Width;
            Y = y - Height;
            Point temp = new Point(X, Y, pointArray[0].getShape(), 0);
            if (pointArray[0].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[0].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[0].getNextPoint()[i]);
                }
            }
            if (pointArray[0].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[0].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[0].getPrePoint()[i]);
                }
            }
            pointArray[0] = temp;

            X = x;
            Y = y - Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 1);
            if (pointArray[1].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[1].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[1].getNextPoint()[i]);
                }
            }
            if (pointArray[1].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[1].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[1].getPrePoint()[i]);
                }
            }
            pointArray[1] = temp;

            X = x + Width;
            Y = y - Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 2);
            if (pointArray[2].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[2].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[2].getNextPoint()[i]);
                }
            }
            if (pointArray[2].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[2].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[2].getPrePoint()[i]);
                }
            }
            pointArray[2] = temp;

            X = x + Width;
            Y = y;
            temp = new Point(X, Y, pointArray[0].getShape(), 3);
            if (pointArray[3].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[3].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[3].getNextPoint()[i]);
                }
            }
            if (pointArray[3].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[3].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[3].getPrePoint()[i]);
                }
            }
            pointArray[3] = temp;

            X = x + Width;
            Y = y + Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 4);
            if (pointArray[4].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[4].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[4].getNextPoint()[i]);
                }
            }
            if (pointArray[4].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[4].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[4].getPrePoint()[i]);
                }
            }
            pointArray[4] = temp;

            X = x;
            Y = y + Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 5);
            if (pointArray[5].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[5].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[5].getNextPoint()[i]);
                }
            }
            if (pointArray[5].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[5].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[5].getPrePoint()[i]);
                }
            }
            pointArray[5] = temp;

            X = x - Width;
            Y = y + Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 6);
            if (pointArray[6].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[6].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[6].getNextPoint()[i]);
                }
            }
            if (pointArray[6].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[6].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[6].getPrePoint()[i]);
                }
            }
            pointArray[6] = temp;

            X = x - Width;
            Y = y;
            temp = new Point(X, Y, pointArray[0].getShape(), 7);
            if (pointArray[7].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[7].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[7].getNextPoint()[i]);
                }
            }
            if (pointArray[7].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[7].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[7].getPrePoint()[i]);
                }
            }
            pointArray[7] = temp;
        }

        if (200 <= x && x <= 200 + Width) {
            X = 200;
            Y = y - Height;
            Point temp = new Point(X, Y, pointArray[0].getShape(), 0);
            if (pointArray[0].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[0].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[0].getNextPoint()[i]);
                }
            }
            if (pointArray[0].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[0].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[0].getPrePoint()[i]);
                }
            }
            pointArray[0] = temp;

            X = 200 + Width;
            Y = y - Height - 3;
            temp = new Point(X, Y, pointArray[0].getShape(), 1);
            if (pointArray[1].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[1].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[1].getNextPoint()[i]);
                }
            }
            if (pointArray[1].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[1].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[1].getPrePoint()[i]);
                }
            }
            pointArray[1] = temp;

            X = 200 + width;
            Y = y - Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 2);
            if (pointArray[2].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[2].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[2].getNextPoint()[i]);
                }
            }
            if (pointArray[2].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[2].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[2].getPrePoint()[i]);
                }
            }
            pointArray[2] = temp;

            X = 200 + width;
            Y = y;
            temp = new Point(X, Y, pointArray[0].getShape(), 3);
            if (pointArray[3].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[3].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[3].getNextPoint()[i]);
                }
            }
            if (pointArray[3].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[3].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[3].getPrePoint()[i]);
                }
            }
            pointArray[3] = temp;

            X = 200 + width;
            Y = y + Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 4);
            if (pointArray[4].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[4].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[4].getNextPoint()[i]);
                }
            }
            if (pointArray[4].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[4].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[4].getPrePoint()[i]);
                }
            }
            pointArray[4] = temp;

            X = 200 + Width;
            Y = y + Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 5);
            if (pointArray[5].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[5].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[5].getNextPoint()[i]);
                }
            }
            if (pointArray[5].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[5].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[5].getPrePoint()[i]);
                }
            }
            pointArray[5] = temp;

            X = 200;
            Y = y + Height;
            temp = new Point(X, Y, pointArray[0].getShape(), 6);
            if (pointArray[6].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[6].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[6].getNextPoint()[i]);
                }
            }
            if (pointArray[6].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[6].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[6].getPrePoint()[i]);
                }
            }
            pointArray[6] = temp;

            X = 200;
            Y = y;
            temp = new Point(X, Y, pointArray[0].getShape(), 7);
            if (pointArray[7].getNextPoint()[0] != null) {
                for (int i = 0; i < pointArray[7].getNextPoint().length - 1; i++) {
                    temp.addConnection(pointArray[7].getNextPoint()[i]);
                }
            }
            if (pointArray[7].getPrePoint()[0] != null) {
                for (int i = 0; i < pointArray[7].getPrePointLength() - 1; i++) {
                    temp.addPreConnection(pointArray[7].getPrePoint()[i]);
                }
            }
            pointArray[7] = temp;
        }
        return pointArray;
    }

    @Override
    public void changeCoords(int x, int y) {
    }

    @Override
    public String[] drawable() {

        drawArray[0] = "x";
        drawArray[1] = "y";
        drawArray[2] = "width";
        drawArray[3] = "height";
        drawArray[4] = "Image path";

        return drawArray;
    }

    @Override
    public Text readText() {
        return text;
    }

    @Override
    public void writeText(String message, String font, String style, int size) {
        text.setFont(font);
        text.setMessage(message);
        text.setSize(size);
        text.setStyle(style);
    }

    public boolean equals(Shape p) {
        if (p.type() == this.type()) {
            if (p.XCenter == this.XCenter) {
                if (p.YCenter == this.YCenter) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setLeftTag(String tag) {
        leftTag = tag;
    }

    @Override
    public void setRightTag(String tag) {
        rightTag = tag;
    }

    @Override
    public String getLeftTag() {
        return leftTag;
    }

    @Override
    public String getRightTag() {
        return rightTag;
    }

    @Override
    public void setDownTag(String tag) {
        downTag = tag;
    }

    @Override
    public String getDownTag() {
        return downTag;
    }

    public int findPoint(Point p) {
        int index = 0;
        for (int i = 0; i < 8; i++) {
            boolean match = p.equals(pointArray[i]);
            if (match) {
                index = i;
            }
        }
        return index;
    }
}
