package flowcharter;

import Form.SetText;
import Form.Property;
import Form.DiamondProperty;
import Form.DiamondSetText;
import Form.Edit;
import Form.Help;
import Shapes.Rectangle;
import Shapes.Parallelogram;
import Shapes.Output;
import Shapes.Oval;
import Shapes.Diamond;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author Sara
 */
public class DrawPanel extends JPanel {

    //Image for menu bar
    private BufferedImage rectangle = null;
    private BufferedImage oval = null;
    private BufferedImage diamond = null;
    private BufferedImage output = null;
    private BufferedImage parallelogram = null;
    private BufferedImage background = null;
    private BufferedImage run = null;
    private BufferedImage exitIm = null;
    private BufferedImage help = null;
    private BufferedImage saveIm = null;

    //Image for coding
    private BufferedImage recSelected = null;
    private BufferedImage diamondSelected = null;
    private BufferedImage ovalSelected = null;
    private BufferedImage outputSelected = null;
    private BufferedImage paraSelected = null;

    //Image of object in the array in AddShape class
    private BufferedImage objectImg = null;
    private BufferedImage pointImg = null;

    public static JPanel panel;

    //Save which type of shape is already selected
    public static ShapeEnum clickedShape;

    //The array of all the objects of shape
    public static AddShape addShape = new AddShape();

    JPopupMenu menu = new JPopupMenu("Popup");
    JMenuBar menuBar = new JMenuBar();

    //These variables are used when just click on a shape and want to show its points
    //Save which shape is clicked
    private Shape tempShape = null;
    //its first element save the point of shape, which is clicked.
    //and its second one save the point of the other shape, which is connected to.
    private Point point[] = new Point[2];
    private boolean pointOn = false;
    private int xPoint;
    private int yPoint;
    private int tx;
    private int ty;

    //Determine if a shape should draw or not
    private boolean drawRec = false;
    private boolean drawDiamond = false;
    private boolean drawOval = false;
    private boolean drawPara = false;
    private boolean drawOutput = false;

    //Used for moving an object 
    private boolean move = false;
    private boolean exist = true;

    private boolean isDragging = false;
    private int xDrag;
    private int yDrag;
    private int shapeLength = 1;
    private Shape[] selectedShape = new Shape[shapeLength];
    private boolean drawRecDrag = false;
    private int widthDrag;
    private int heightDrag;
    private BufferedImage recDragImg = null;

    private int index;
    public static int tempIndex;

    //x and y of mouse event
    private int x, y;

    //x and y of the image
    private int XImg, YImg;

    private int x1, y1, x2, y2;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 700);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        //menu bar
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 200, 700);

        //add image for manu      
        g.drawImage(background, 0, 0, 900, 700, this);
        g.drawImage(rectangle, 20, 30, 150, 60, this);
        g.drawImage(oval, 20, 110, 150, 60, this);
        g.drawImage(diamond, 20, 190, 150, 60, this);
        g.drawImage(output, 20, 270, 150, 60, this);
        g.drawImage(parallelogram, 20, 350, 150, 60, this);
        g.drawImage(run, 10, 480, 80, 80, this);
        g.drawImage(help, 100, 480, 80, 80, this);
        g.drawImage(saveIm, 10, 570, 80, 80, this);
        g.drawImage(exitIm, 100, 570, 80, 80, this);

        //draw image from array in AddShape class
        if (addShape.getShape()[0] != null) {
            for (Shape sh : addShape.getShape()) {
                if (sh != null) {
                    String array[] = sh.drawable();
                    int tempX = Integer.parseInt(array[0]);
                    int tempY = Integer.parseInt(array[1]);
                    int width = Integer.parseInt(array[2]);
                    int heigth = Integer.parseInt(array[3]);
                    String message = sh.readText().getMessage();

                    try {
                        objectImg = ImageIO.read(new FileInputStream(array[4]));
                    } catch (IOException ex) {
                        Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.drawImage(objectImg, tempX, tempY, width, heigth, this);

                    if (message != null) {
                        String f = sh.readText().getFont();
                        int size = sh.readText().getSize();
                        String style = sh.readText().getStyle();
                        Font font = new Font(f, Font.PLAIN, size);
                        if (style.equals("ITALIC")) {
                            font = new Font(f, Font.ITALIC, size);
                        }

                        if (style.equals("BOLD")) {
                            font = new Font(f, Font.BOLD, size);
                        }

                        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                        FontMetrics fm = img.getGraphics().getFontMetrics(font);
                        int temp = fm.stringWidth(message);
                        int X = tempX + (width / 2);
                        int Y = tempY + (heigth / 2);
                        g.setFont(font);
                        g.drawString(message, X, Y);
                    }

                    for (int i = 0; i < 8; i++) {
                        if (sh.getPoint()[i].getNextPoint()[0] != null) {
                            for (Point p : sh.getPoint()[i].getNextPoint()) {
                                if (p != null) {
                                    ty = (sh.getPoint()[i].getY() + p.getY()) / 2;
                                    tx = (sh.getPoint()[i].getX() + p.getX()) / 2;
                                    g.drawLine(sh.getPoint()[i].getX(), sh.getPoint()[i].getY(), sh.getPoint()[i].getX(), ty);
                                    g.drawLine(sh.getPoint()[i].getX(), ty, p.getX(), ty);
                                    g.drawLine(p.getX(), ty, p.getX(), p.getY());
                                }
                            }
                        }
                    }

                    if (sh.type() == ShapeEnum.Diamond) {
                        if (sh.getLeftTag() != null && sh.getRightTag() != null) {
                            Font fn = new Font("Calibri (Body)", Font.BOLD, 16);
                            g.setFont(fn);
                            g.drawString(sh.getLeftTag(), sh.pointArray[7].getX() - 30, sh.pointArray[7].getY() + 5);
                            g.drawString(sh.getRightTag(), sh.pointArray[3].getX(), sh.pointArray[3].getY() + 5);
                            if (sh.getDownTag() != null) {
                                g.drawString(sh.getDownTag(), sh.pointArray[5].getX(), sh.pointArray[5].getY() + 10);
                            }
                        }
                    }
                }
            }
        }

        //draw the image when dragging them.
        if (drawRec) {
            g.drawImage(recSelected, XImg, YImg, 150, 60, this);
            drawRec = false;
        }
        if (drawDiamond) {
            g.drawImage(diamondSelected, XImg, YImg, 150, 60, this);
            drawDiamond = false;
        }
        if (drawOval) {
            g.drawImage(ovalSelected, XImg, YImg, 150, 60, this);
            drawOval = false;
        }
        if (drawPara) {
            g.drawImage(paraSelected, XImg, YImg, 150, 60, this);
            drawPara = false;
        }
        if (drawOutput) {
            g.drawImage(outputSelected, XImg, YImg, 150, 60, this);
            drawOutput = false;
        }
        if (tempShape != null) {
            for (int i = 0; i < 8; i++) {
                g.drawImage(pointImg, tempShape.pointArray[i].getX(), tempShape.pointArray[i].getY(), 6, 6, this);
            }
        }
        if (pointOn) {
            for (int i = 0; i < addShape.getShapeLength() - 1; i++) {
                if (addShape.getShape()[0] != null) {
                    for (int j = 0; j < 8; j++) {
                        if (i != index) {
                            int tempX1 = addShape.getShape()[i].pointArray[j].getX() - 3;
                            int tempY1 = addShape.getShape()[i].pointArray[j].getY() - 3;
                            g.drawImage(pointImg, tempX1, tempY1, 6, 6, this);
                        }
                    }
                }
            }
            ty = (point[0].getY() + yPoint) / 2;
            tx = (point[0].getX() + xPoint) / 2;
            g.drawLine(point[0].getX(), point[0].getY(), point[0].getX(), ty);
            g.drawLine(point[0].getX(), ty, xPoint, ty);
            g.drawLine(xPoint, ty, xPoint, yPoint);
        }

        if (drawRecDrag) {
            //g.drawImage(recDragImg, xDrag, yDrag, widthDrag, heightDrag, this);
        }
    }

    public DrawPanel() {

        //Images of the menu bar
        try {
            background = ImageIO.read(new FileInputStream("images/BG.png"));
            rectangle = ImageIO.read(new FileInputStream("images/rectangle.png"));
            oval = ImageIO.read(new FileInputStream("images/oval.png"));
            diamond = ImageIO.read(new FileInputStream("images/diamond.png"));
            output = ImageIO.read(new FileInputStream("images/output.png"));
            parallelogram = ImageIO.read(new FileInputStream("images/parallelogram.png"));
            run = ImageIO.read(new FileInputStream("images/run.png"));
            help = ImageIO.read(new FileInputStream("images/help.png"));
            saveIm = ImageIO.read(new FileInputStream("images/save.png"));
            exitIm = ImageIO.read(new FileInputStream("images/exit.png"));
        } catch (IOException e) {
        }

        selectedShape[0] = null;

        //pop up menu
        JMenuItem writeText = new JMenuItem("Add Text");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem edit = new JMenuItem("Edit");

        menu.add(writeText);
        menu.add(delete);
        menu.add(edit);

        setLayout(null);

        //Menu bar
        JMenu file = new JMenu("File");
        menuBar.add(file);
        menuBar.setBounds(5, 0, 183, 20);

        JMenuItem newPage = new JMenuItem("New Project");
        file.add(newPage);

        JMenuItem openProject = new JMenuItem("Open Project");
        file.add(openProject);

        JMenuItem save = new JMenuItem("Save");
        file.add(save);

        JMenu Edit = new JMenu("Edit");
        menuBar.add(Edit);

        JMenuItem Delete = new JMenuItem("Delete");
        //Edit.add(Delete);

        JMenuItem exit = new JMenuItem("Exit");
        Edit.add(exit);

        this.add(menuBar);

        addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //Open a form by clicking on the images and draw the image
                        //Rectangle
                        if (20 <= x && x <= 150 && 30 <= y && y <= 90) {
                            clickedShape = ShapeEnum.Rectangle;
                            tempShape = null;
                            Property property = new Property(null, true);
                            property.setLocationRelativeTo(null);
                            property.setVisible(true);
                            clickedShape = null;
                        }

                        //Oval
                        if (20 <= x && x <= 150 && 110 <= y && y <= 170) {
                            clickedShape = ShapeEnum.Oval;
                            tempShape = null;
                            Property property = new Property(null, true);
                            property.setLocationRelativeTo(null);
                            property.setVisible(true);
                            clickedShape = null;
                        }

                        //Diamond
                        if (20 <= x && x <= 150 && 190 <= y && y <= 250) {
                            clickedShape = ShapeEnum.Diamond;
                            tempShape = null;
                            DiamondProperty diamondProperty = new DiamondProperty(null, true);
                            diamondProperty.setLocationRelativeTo(null);
                            diamondProperty.setVisible(true);
                            clickedShape = null;
                        }

                        //Output
                        if (20 <= x && x <= 150 && 270 <= y && y <= 330) {
                            clickedShape = ShapeEnum.Output;
                            tempShape = null;
                            Property property = new Property(null, true);
                            property.setLocationRelativeTo(null);
                            property.setVisible(true);
                            clickedShape = null;
                        }

                        //Parallelogram
                        if (20 <= x && x <= 150 && 350 <= y && y <= 410) {
                            clickedShape = ShapeEnum.Parallelogram;
                            tempShape = null;
                            Property property = new Property(null, true);
                            property.setLocationRelativeTo(null);
                            property.setVisible(true);
                            clickedShape = null;
                        }

                        //Run
                        if (10 <= x && x <= 90 && 480 <= y && y <= 560) {
                            tempShape = null;
                            clickedShape = ShapeEnum.run;
                            DisplayOutput.reset();
                            RAM ram = new RAM();
                            RAM.variableLength = 1;
                            for (Shape sh : addShape.getShape()) {
                                if (sh != null) {
                                    sh.watched = false;
                                }
                            }
                        }

                        //Save
                        if (10 <= x && x <= 90 && 570 <= y && y <= 650) {
                            tempShape = null;
                            clickedShape = ShapeEnum.save;
                            String name = JOptionPane.showInputDialog("Enter the address of file");
                            int length = name.length();
                            String subs = name.substring(length - 4, length);
                            boolean match = subs.matches(".txt");
                            while (!match) {
                                name = JOptionPane.showInputDialog("Enter the address of file");
                                length = name.length();
                                subs = name.substring(length - 4, length);
                                match = subs.matches(".txt");
                            }
//                            String path = System.getProperty("java.class.path") + "\\images\\" + name;
                            FileClass fc = new FileClass(name);
                            fc.writeFile();
                        }

                        //exit
                        if (100 <= x && x <= 180 && 570 <= y && y <= 650) {
                            int answer = JOptionPane.showConfirmDialog(null, "Do you want to save changes??", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                            if (answer == JOptionPane.YES_OPTION) {
                                String name = JOptionPane.showInputDialog("Enter the address of file");
                                int length = name.length();
                                String subs = name.substring(length - 4, length);
                                boolean match = subs.matches(".txt");
                                while (!match) {
                                    name = JOptionPane.showInputDialog("Enter the address of file");
                                    length = name.length();
                                    subs = name.substring(length - 4, length);
                                    match = subs.matches(".txt");
                                }
//                                String path = System.getProperty("java.class.path") + "\\images\\" + name;
                                FileClass fc = new FileClass(name);
                                fc.writeFile();
                                System.exit(0);
                            } else if (answer == JOptionPane.NO_OPTION) {
                                System.exit(0);
                            }
                        }

                        //help
                        if (100 <= x && x <= 180 && 480 <= y && y <= 560) {
                            Help hc = new Help(null, true);
                            hc.setLocationRelativeTo(null);
                            hc.setVisible(true);
                        }

                        if (200 <= x && x <= 900 && 0 <= y && y <= 700) {
                            for (int i = 0; i < addShape.getShapeLength() - 1; i++) {
                                if (addShape.getShape()[i] != null) {
                                    x1 = addShape.getShape()[i].getPoint()[0].getX();
                                    x2 = addShape.getShape()[i].getPoint()[2].getX();
                                    y1 = addShape.getShape()[i].getPoint()[0].getY();
                                    y2 = addShape.getShape()[i].getPoint()[4].getY();
                                    if (x1 <= x && x <= x2 && y >= y1 && y <= y2) {
                                        tempShape = addShape.getShape()[i];
                                        index = i;
                                        try {
                                            pointImg = ImageIO.read(new FileInputStream("images/point.png"));
                                        } catch (IOException ex) {
                                            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
                                    } else {
                                        tempShape = null;
                                    }
                                    repaint();
                                }
                            }
                        }
                        repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                        //Find where mouse clicked
                        x = e.getX();
                        y = e.getY();

                        //Change the icon image by clicking on it
                        //Rectangle
                        if (20 <= x && x <= 150 && 30 <= y && y <= 90) {
                            tempShape = null;
                            clickedShape = ShapeEnum.Rectangle;
                            try {
                                rectangle = ImageIO.read(new FileInputStream("images/recClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //Oval
                        if (20 <= x && x <= 150 && 110 <= y && y <= 170) {
                            tempShape = null;
                            clickedShape = ShapeEnum.Oval;
                            try {
                                oval = ImageIO.read(new FileInputStream("images/ovalClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //Diamond
                        if (20 <= x && x <= 150 && 190 <= y && y <= 250) {
                            tempShape = null;
                            clickedShape = ShapeEnum.Diamond;
                            try {
                                diamond = ImageIO.read(new FileInputStream("images/diamondClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //Output shape
                        if (20 <= x && x <= 150 && 270 <= y && y <= 330) {
                            tempShape = null;
                            clickedShape = ShapeEnum.Output;
                            try {
                                output = ImageIO.read(new FileInputStream("images/outputClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //Parallelogram
                        if (20 <= x && x <= 150 && 350 <= y && y <= 410) {
                            tempShape = null;
                            clickedShape = ShapeEnum.Parallelogram;
                            try {
                                parallelogram = ImageIO.read(new FileInputStream("images/paraClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //run
                        if (10 <= x && x <= 90 && 480 <= y && y <= 560) {
                            tempShape = null;
                            clickedShape = ShapeEnum.run;
                            try {
                                run = ImageIO.read(new FileInputStream("images/runClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //help
                        if (100 <= x && x <= 180 && 480 <= y && y <= 560) {
                            tempShape = null;
                            clickedShape = ShapeEnum.help;
                            try {
                                help = ImageIO.read(new FileInputStream("images/helpClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //save
                        if (10 <= x && x <= 90 && 570 <= y && y <= 650) {
                            tempShape = null;
                            clickedShape = ShapeEnum.save;
                            try {
                                saveIm = ImageIO.read(new FileInputStream("images/saveClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //exit
                        if (100 <= x && x <= 180 && 570 <= y && y <= 650) {
                            tempShape = null;
                            clickedShape = ShapeEnum.exit;
                            try {
                                exitIm = ImageIO.read(new FileInputStream("images/exitClicked.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //Dragging a shape object on the screen 
                        if (200 <= x && x <= 900 && 0 <= y && y <= 700) {
                            for (int i = 0; i < addShape.getShapeLength() - 1; i++) {
                                if (addShape.getShape()[i] != null) {
                                    x1 = addShape.getShape()[i].getPoint()[0].getX();
                                    x2 = addShape.getShape()[i].getPoint()[2].getX();
                                    y1 = addShape.getShape()[i].getPoint()[0].getY();
                                    y2 = addShape.getShape()[i].getPoint()[4].getY();
                                    if (x1 <= x && x <= x2 && y >= y1 && y <= y2) {
                                        //Chech to display popup menu or just drag the shape
                                        if (e.isPopupTrigger()) {
                                            menu.show(e.getComponent(), e.getX(), e.getY());
                                        } else {
                                            clickedShape = addShape.getShape()[i].type();
                                            move = true;
                                            index = i;
                                            exist = false;
                                        }
                                    } else {
                                        isDragging = true;
                                        xDrag = x;
                                        yDrag = y;
                                    }
                                }
                            }
                        }

                        if (tempShape != null) {
                            for (int i = 0; i < 8; i++) {
                                int tempX1 = tempShape.pointArray[i].getX() - 3;
                                int tempX2 = tempShape.pointArray[i].getX() + 3;
                                int tempY1 = tempShape.pointArray[i].getY() - 3;
                                int tempY2 = tempShape.pointArray[i].getY() + 3;

                                if (x >= tempX1 && x <= tempX2 && y >= tempY1 && y <= tempY2) {
                                    point[0] = tempShape.pointArray[i];
                                    pointOn = true;
                                }
                            }
                        }

                        repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                        //Find where mouse released
                        x = e.getX();
                        y = e.getY();

                        //Change the icon image by releasing mouse
                        //Rectangle
                        if (clickedShape == ShapeEnum.Rectangle && exist == true) {
                            try {
                                rectangle = ImageIO.read(new FileInputStream("images/rectangle.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (200 <= x && x <= 825 && 30 <= y && y <= 630) {
                                Rectangle rectangle = new Rectangle(x, y, 150, 60);
                                rectangle.createCoordination(x, y, 150, 60);
                                addShape.add(rectangle);

                            }
                            clickedShape = null;
                        }

                        //Diamond
                        if (clickedShape == ShapeEnum.Diamond && exist == true) {
                            try {
                                diamond = ImageIO.read(new FileInputStream("images/diamond.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (200 <= x && x <= 825 && 30 <= y && y <= 630) {
                                Diamond diamond = new Diamond(x, y, 150, 60);
                                diamond.createCoordination(x, y, 150, 60);
                                addShape.add(diamond);
                            }
                            clickedShape = null;
                        }

                        //Output
                        if (clickedShape == ShapeEnum.Output && exist == true) {
                            try {
                                output = ImageIO.read(new FileInputStream("images/output.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (200 <= x && x <= 825 && 30 <= y && y <= 630) {
                                Output output = new Output(x, y, 150, 60);
                                output.createCoordination(x, y, 150, 60);
                                addShape.add(output);
                            }
                            clickedShape = null;
                        }

                        //Oval
                        if (clickedShape == ShapeEnum.Oval && exist == true) {
                            try {
                                oval = ImageIO.read(new FileInputStream("images/oval.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (200 <= x && x <= 825 && 30 <= y && y <= 630) {
                                Oval oval = new Oval(x, y, 150, 60);
                                oval.createCoordination(x, y, 150, 60);
                                addShape.add(oval);
                            }
                            clickedShape = null;
                        }

                        //Parallelogram
                        if (clickedShape == ShapeEnum.Parallelogram && exist == true) {
                            try {
                                parallelogram = ImageIO.read(new FileInputStream("images/parallelogram.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (200 <= x && x <= 825 && 30 <= y && y <= 630) {
                                Parallelogram parallelogram = new Parallelogram(x, y, 150, 60);
                                parallelogram.createCoordination(x, y, 150, 60);
                                addShape.add(parallelogram);
                            }
                            clickedShape = null;
                        }

                        if (clickedShape == ShapeEnum.run && exist == true) {
                            try {
                                run = ImageIO.read(new FileInputStream("images/run.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        if (clickedShape == ShapeEnum.help && exist == true) {
                            try {
                                help = ImageIO.read(new FileInputStream("images/help.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (clickedShape == ShapeEnum.save && exist == true) {
                            try {
                                saveIm = ImageIO.read(new FileInputStream("images/save.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (clickedShape == ShapeEnum.exit && exist == true) {
                            try {
                                exitIm = ImageIO.read(new FileInputStream("images/exit.png"));
                            } catch (IOException ex) {
                                Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        //Check if dragging a shape object, to do not create an object
                        if (clickedShape == ShapeEnum.Rectangle && move == true) {
                            move = false;
                            exist = true;
                            clickedShape = null;
                        }

                        if (clickedShape == ShapeEnum.Diamond && move == true) {
                            move = false;
                            exist = true;
                            clickedShape = null;
                        }

                        if (clickedShape == ShapeEnum.Output && move == true) {
                            move = false;
                            exist = true;
                            clickedShape = null;
                        }

                        if (clickedShape == ShapeEnum.Oval && move == true) {
                            move = false;
                            exist = true;
                            clickedShape = null;
                        }

                        if (clickedShape == ShapeEnum.Parallelogram && move == true) {
                            move = false;
                            exist = true;
                            clickedShape = null;
                        }

                        //Check to display poppup menu
                        if (200 <= x && x <= 900 && 0 <= y && y <= 700) {
                            for (int i = 0; i < addShape.getShapeLength() - 1; i++) {
                                if (addShape.getShape()[i] != null) {
                                    x1 = addShape.getShape()[i].getPoint()[0].getX();
                                    x2 = addShape.getShape()[i].getPoint()[2].getX();
                                    y1 = addShape.getShape()[i].getPoint()[0].getY();
                                    y2 = addShape.getShape()[i].getPoint()[4].getY();
                                    if (x1 <= x && x <= x2 && y >= y1 && y <= y2) {
                                        if (e.isPopupTrigger()) {
                                            menu.show(e.getComponent(), e.getX(), e.getY());
                                            tempIndex = i;
                                        }
                                    }
                                }
                            }
                        }

                        if (pointOn) {
                            for (int i = 0; i < addShape.getShapeLength() - 1; i++) {
                                if (i != index) {
                                    for (int j = 0; j < 8; j++) {
                                        int tempX1 = addShape.getShape()[i].pointArray[j].getX() - 3;
                                        int tempX2 = addShape.getShape()[i].pointArray[j].getX() + 3;
                                        int tempY1 = addShape.getShape()[i].pointArray[j].getY() - 3;
                                        int tempY2 = addShape.getShape()[i].pointArray[j].getY() + 3;
                                        if (x >= tempX1 && x <= tempX2 && y >= tempY1 && y <= tempY2) {
                                            point[1] = addShape.getShape()[i].pointArray[j];
                                            addShape.getShape()[i].connection(point[0], point[1]);
                                            point[1].addPreConnection(point[0]);
                                            pointOn = false;
                                        } else {
                                            pointOn = false;
                                        }
                                    }
                                } else {
                                    pointOn = false;
                                }
                            }
                        }

                        if (selectedShape[0] != null) {
                            isDragging = false;
                        } else {
                            isDragging = false;
                        }
                        repaint();
                    }
                }
        );

        addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        //Find where mouse is dragging
                        x = e.getX();
                        y = e.getY();

                        //Draw images as dragging them.
                        //Rectangle
                        if (clickedShape == ShapeEnum.Rectangle && move == false) {
                            if (200 <= x && x <= 275 && 30 <= y && y <= 630) {
                                XImg = 200;
                                YImg = y - 30;

                                try {
                                    recSelected = ImageIO.read(new FileInputStream("images/recImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawRec = true;
                            }
                            if (275 <= x && x <= 825 && 30 <= y && y <= 630) {
                                XImg = x - 75;
                                YImg = y - 30;

                                try {
                                    recSelected = ImageIO.read(new FileInputStream("images/recImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawRec = true;
                            }
                        }

                        if (clickedShape == ShapeEnum.Rectangle && move == true && pointOn == false) {
                            if (200 <= x && x <= 825 && 30 <= y && y <= 630) {
                                addShape.getShape()[index].changeCoords(x, y);
                                for (int i = 0; i < 8; i++) {
                                    if (addShape.getShape()[index].pointArray[i].getPrePointLength() > 1) {
                                        for (int j = 0; j < addShape.getShape()[index].pointArray[i].getPrePointLength() - 1; j++) {
                                            Shape s = addShape.getShape()[index].pointArray[i].getPrePoint()[j].getShape();
                                            Point p = addShape.getShape()[index].pointArray[i].getPrePoint()[j];
                                            int Indx = s.findPoint(p);
                                            s.pointArray[Indx].changeNextPoint(addShape.getShape()[index].pointArray[i]);
                                        }
                                    }
                                }
                            }
                        }

                        //Diamond
                        if (clickedShape == ShapeEnum.Diamond && move == false) {
                            if (200 <= x && x <= 275 && 30 <= y && y <= 630) {
                                XImg = 200;
                                YImg = y - 30;

                                try {
                                    diamondSelected = ImageIO.read(new FileInputStream("images/diamondImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawDiamond = true;
                            }
                            if (275 <= x && x <= 825 && 30 <= y && y <= 630) {
                                XImg = x - 75;
                                YImg = y - 30;

                                try {
                                    diamondSelected = ImageIO.read(new FileInputStream("images/diamondImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawDiamond = true;
                            }
                        }

                        if (clickedShape == ShapeEnum.Diamond && move == true && pointOn == false) {
                            addShape.getShape()[index].changeCoords(x, y);
                            for (int i = 0; i < 8; i++) {
                                if (addShape.getShape()[index].pointArray[i].getPrePointLength() > 1) {
                                    for (int j = 0; j < addShape.getShape()[index].pointArray[i].getPrePointLength() - 1; j++) {
                                        Shape s = addShape.getShape()[index].pointArray[i].getPrePoint()[j].getShape();
                                        Point p = addShape.getShape()[index].pointArray[i].getPrePoint()[j];
                                        int Indx = s.findPoint(p);
                                        s.pointArray[Indx].changeNextPoint(addShape.getShape()[index].pointArray[i]);
                                    }
                                }
                            }
                        }

                        //Oval
                        if (clickedShape == ShapeEnum.Oval && move == false) {
                            if (200 <= x && x <= 275 && 30 <= y && y <= 630) {
                                XImg = 200;
                                YImg = y - 30;

                                try {
                                    ovalSelected = ImageIO.read(new FileInputStream("images/ovalImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawOval = true;
                            }
                            if (275 <= x && x <= 825 && 30 <= y && y <= 630) {
                                XImg = x - 75;
                                YImg = y - 30;

                                try {
                                    ovalSelected = ImageIO.read(new FileInputStream("images/ovalImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawOval = true;
                            }
                        }

                        if (clickedShape == ShapeEnum.Oval && move == true && pointOn == false) {
                            addShape.getShape()[index].changeCoords(x, y);
                            for (int i = 0; i < 8; i++) {
                                if (addShape.getShape()[index].pointArray[i].getPrePointLength() > 1) {
                                    for (int j = 0; j < addShape.getShape()[index].pointArray[i].getPrePointLength() - 1; j++) {
                                        Shape s = addShape.getShape()[index].pointArray[i].getPrePoint()[j].getShape();
                                        Point p = addShape.getShape()[index].pointArray[i].getPrePoint()[j];
                                        int Indx = s.findPoint(p);
                                        s.pointArray[Indx].changeNextPoint(addShape.getShape()[index].pointArray[i]);
                                    }
                                }
                            }
                        }

                        //Output
                        if (clickedShape == ShapeEnum.Output && move == false) {
                            if (200 <= x && x <= 275 && 30 <= y && y <= 630) {
                                XImg = 200;
                                YImg = y - 30;

                                try {
                                    outputSelected = ImageIO.read(new FileInputStream("images/outputImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawOutput = true;
                            }
                            if (275 <= x && x <= 825 && 30 <= y && y <= 630) {
                                XImg = x - 75;
                                YImg = y - 30;

                                try {
                                    outputSelected = ImageIO.read(new FileInputStream("images/outputImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawOutput = true;
                            }
                        }

                        if (clickedShape == ShapeEnum.Output && move == true && pointOn == false) {
                            addShape.getShape()[index].changeCoords(x, y);
                            for (int i = 0; i < 8; i++) {
                                if (addShape.getShape()[index].pointArray[i].getPrePointLength() > 1) {
                                    for (int j = 0; j < addShape.getShape()[index].pointArray[i].getPrePointLength() - 1; j++) {
                                        Shape s = addShape.getShape()[index].pointArray[i].getPrePoint()[j].getShape();
                                        Point p = addShape.getShape()[index].pointArray[i].getPrePoint()[j];
                                        int Indx = s.findPoint(p);
                                        s.pointArray[Indx].changeNextPoint(addShape.getShape()[index].pointArray[i]);
                                    }
                                }
                            }
                        }

                        //Parallelogram
                        if (clickedShape == ShapeEnum.Parallelogram && move == false) {
                            if (200 <= x && x <= 275 && 30 <= y && y <= 630) {
                                XImg = 200;
                                YImg = y - 30;

                                try {
                                    paraSelected = ImageIO.read(new FileInputStream("images/paraImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawPara = true;
                            }
                            if (275 <= x && x <= 825 && 30 <= y && y <= 630) {
                                XImg = x - 75;
                                YImg = y - 30;

                                try {
                                    paraSelected = ImageIO.read(new FileInputStream("images/paraImg.png"));
                                } catch (IOException ex) {
                                    Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                drawPara = true;
                            }
                        }

                        if (clickedShape == ShapeEnum.Parallelogram && move == true && pointOn == false) {
                            addShape.getShape()[index].changeCoords(x, y);
                            for (int i = 0; i < 8; i++) {
                                if (addShape.getShape()[index].pointArray[i].getPrePointLength() > 1) {
                                    for (int j = 0; j < addShape.getShape()[index].pointArray[i].getPrePointLength() - 1; j++) {
                                        Shape s = addShape.getShape()[index].pointArray[i].getPrePoint()[j].getShape();
                                        Point p = addShape.getShape()[index].pointArray[i].getPrePoint()[j];
                                        int Indx = s.findPoint(p);
                                        s.pointArray[Indx].changeNextPoint(addShape.getShape()[index].pointArray[i]);
                                    }
                                }
                            }
                        }

                        if (pointOn) {
                            xPoint = x;
                            yPoint = y;
                        }

                        repaint();
                    }
                }
        );
        writeText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (addShape.getShape()[DrawPanel.tempIndex].type() == ShapeEnum.Diamond) {
                    DiamondSetText diamondSetText = new DiamondSetText(null, true);
                    diamondSetText.setLocationRelativeTo(null);
                    diamondSetText.setVisible(true);
                } else {
                    SetText setText = new SetText(null, true);
                    setText.setLocationRelativeTo(null);
                    setText.setVisible(true);
                }
                repaint();
            }
        });

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Are you sure??", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    if (tempShape.equals(addShape.getShape()[tempIndex])) {
                        tempShape = null;
                    }
                    for (int i = 0; i < 8; i++) {
                        if (addShape.getShape()[tempIndex].pointArray[i].getPrePointLength() > 1) {
                            Shape s = null;
                            int Indx = 0;
                            for (int j = 0; j < addShape.getShape()[tempIndex].pointArray[i].getPrePointLength() - 1; j++) {
                                s = addShape.getShape()[tempIndex].pointArray[i].getPrePoint()[j].getShape();
                                Point p = addShape.getShape()[tempIndex].pointArray[i].getPrePoint()[j];
                                Indx = s.findPoint(p);
                                s.pointArray[Indx].deleteConnection(addShape.getShape()[tempIndex].pointArray[i]);
                            }
                            s.pointArray[Indx].setNextPointLength(s.pointArray[Indx].getNextPointLength() - 1);
                        }
                    }
                    for (int i = tempIndex; i < addShape.getShape().length - 1; i++) {
                        addShape.getShape()[i] = addShape.getShape()[i + 1];
                    }
                    addShape.setShapeLength(addShape.getShapeLength() - 1);
                }
                repaint();
            }
        });

        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Edit edit = new Edit(null, true);
                edit.setLocationRelativeTo(null);
                edit.setVisible(true);
                repaint();
            }
        });

        newPage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to save changes??", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    String name = JOptionPane.showInputDialog("Enter the address of file");
                    int length = name.length();
                    String subs = name.substring(length - 4, length);
                    boolean match = subs.matches(".txt");
                    while (!match) {
                        name = JOptionPane.showInputDialog("Enter the address of file");
                        length = name.length();
                        subs = name.substring(length - 4, length);
                        match = subs.matches(".txt");
                    }
//                    String path = System.getProperty("java.class.path") + "\\images\\" + name;
                    FileClass fc = new FileClass(name);
                    fc.writeFile();

                    addShape = new AddShape();

                    point[0] = null;
                    point[1] = null;

                    shapeLength = 1;
                    selectedShape = new Shape[shapeLength];

                    tempShape = null;
                    pointOn = false;
                } else if (answer == JOptionPane.NO_OPTION) {
                    addShape = new AddShape();

                    point[0] = null;
                    point[1] = null;

                    shapeLength = 1;
                    selectedShape = new Shape[shapeLength];

                    tempShape = null;
                    pointOn = false;
                }
                repaint();
            }
        });

        Delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Are you sure??", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    if (tempShape.equals(addShape.getShape()[tempIndex])) {
                        tempShape = null;
                    }
                    for (int i = tempIndex; i < addShape.getShapeLength() - 1; i++) {
                        addShape.getShape()[i] = addShape.getShape()[i + 1];
                    }
                    addShape.setShapeLength(addShape.getShapeLength() - 1);
                }
                repaint();
            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to save changes??", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    String name = JOptionPane.showInputDialog("Enter the address of file");
                    int length = name.length();
                    String subs = name.substring(length - 4, length);
                    boolean match = subs.matches(".txt");
                    while (!match) {
                        name = JOptionPane.showInputDialog("Enter the address of file");
                        length = name.length();
                        subs = name.substring(length - 4, length);
                        match = subs.matches(".txt");
                    }
//                    String path = System.getProperty("java.class.path") + "\\images\\" + name;
                    FileClass fc = new FileClass(name);
                    fc.writeFile();
                    System.exit(0);
                } else if (answer == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }

            }
        });

        openProject.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (addShape.getShape()[0] != null) {
                    int answer = JOptionPane.showConfirmDialog(null, "Do you want to save changes??", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

                    //Save the current changes
                    if (answer == JOptionPane.YES_OPTION) {
                        String name = JOptionPane.showInputDialog("Enter the address of file");
                        int length = name.length();
                        String subs = name.substring(length - 4, length);
                        boolean match = subs.matches(".txt");
                        while (!match) {
                            name = JOptionPane.showInputDialog("Enter the address of file");
                            length = name.length();
                            subs = name.substring(length - 4, length);
                            match = subs.matches(".txt");
                        }
//                        String path = System.getProperty("java.class.path") + "\\images\\" + name;
                        FileClass fc = new FileClass(name);
                        fc.writeFile();

                        addShape = new AddShape();

                        point[0] = null;
                        point[1] = null;

                        shapeLength = 1;
                        selectedShape = new Shape[shapeLength];

                        tempShape = null;
                        pointOn = false;

                        //open and read a new file
                        name = JOptionPane.showInputDialog("Enter the address of file");
                        length = name.length();
                        subs = name.substring(length - 4, length);
                        match = subs.matches(".txt");
                        while (!match) {
                            name = JOptionPane.showInputDialog("Enter the address of file");
                            length = name.length();
                            subs = name.substring(length - 4, length);
                            match = subs.matches(".txt");
                        }
//                        path = System.getProperty("java.class.path") + "\\images\\" + name;
                        fc = new FileClass(name);
                        fc.readFile();
                    } else if (answer == JOptionPane.NO_OPTION) {
                        //clear all the shapes
                        addShape = new AddShape();

                        point[0] = null;
                        point[1] = null;

                        shapeLength = 1;
                        selectedShape = new Shape[shapeLength];

                        tempShape = null;
                        pointOn = false;

                        //open and read a new file
                        String name = JOptionPane.showInputDialog("Enter the address of file");
                        int length = name.length();
                        String subs = name.substring(length - 4, length);
                        boolean match = subs.matches(".txt");
                        while (!match) {
                            name = JOptionPane.showInputDialog("Enter the address of file");
                            length = name.length();
                            subs = name.substring(length - 4, length);
                            match = subs.matches(".txt");
                        }
//                        String path = System.getProperty("java.class.path") + "\\images\\" + name;
                        FileClass fc = new FileClass(name);
                        fc.readFile();
                    }
                } else {
                    String name = JOptionPane.showInputDialog("Enter the address of file");
                    int length = name.length();
                    String subs = name.substring(length - 4, length);
                    boolean match = subs.matches(".txt");
                    while (!match) {
                        name = JOptionPane.showInputDialog("Enter the address of file");
                        length = name.length();
                        subs = name.substring(length - 4, length);
                        match = subs.matches(".txt");
                    }
//                    String path = System.getProperty("java.class.path") + "\\images\\" + name;
                    FileClass fc = new FileClass(name);
                    fc.readFile();
                }
                repaint();
            }
        });
    }
}
