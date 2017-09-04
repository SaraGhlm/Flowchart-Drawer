package flowcharter;

import Shapes.Diamond;
import Shapes.Output;
import Shapes.Oval;
import Shapes.Parallelogram;
import Shapes.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sara
 */
public class FileClass {

    File file;

    public FileClass(String path) {

        file = new File(path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void readFile() {
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            while ((sCurrentLine = br.readLine()) != null) {
                int shapeLength = Integer.parseInt(sCurrentLine);
                for (int i = 0; i < shapeLength - 1; i++) {
                    sCurrentLine = br.readLine();
                    if (sCurrentLine.equals("Rectangle")) {
                        int xCenter = Integer.parseInt(br.readLine());
                        int yCenter = Integer.parseInt(br.readLine());
                        int width = Integer.parseInt(br.readLine());
                        int height = Integer.parseInt(br.readLine());

                        Rectangle rectangle = new Rectangle(xCenter, yCenter, width, height);
                        rectangle.createCoordination(xCenter, yCenter, width, height);
                        DrawPanel.addShape.add(rectangle);

                        String message = br.readLine();
                        String font = br.readLine();
                        String style = br.readLine();
                        int size = Integer.parseInt(br.readLine());

                        rectangle.writeText(message, font, style, size);
                    } else if (sCurrentLine.equals("Oval")) {
                        int xCenter = Integer.parseInt(br.readLine());
                        int yCenter = Integer.parseInt(br.readLine());
                        int width = Integer.parseInt(br.readLine());
                        int height = Integer.parseInt(br.readLine());

                        Oval oval = new Oval(xCenter, yCenter, width, height);
                        oval.createCoordination(xCenter, yCenter, width, height);
                        DrawPanel.addShape.add(oval);

                        String message = br.readLine();
                        String font = br.readLine();
                        String style = br.readLine();
                        int size = Integer.parseInt(br.readLine());

                        oval.writeText(message, font, style, size);
                    } else if (sCurrentLine.equals("Parallelogram")) {
                        int xCenter = Integer.parseInt(br.readLine());
                        int yCenter = Integer.parseInt(br.readLine());
                        int width = Integer.parseInt(br.readLine());
                        int height = Integer.parseInt(br.readLine());

                        Parallelogram parallelogram = new Parallelogram(xCenter, yCenter, width, height);
                        parallelogram.createCoordination(xCenter, yCenter, width, height);
                        DrawPanel.addShape.add(parallelogram);

                        String message = br.readLine();
                        String font = br.readLine();
                        String style = br.readLine();
                        int size = Integer.parseInt(br.readLine());

                        parallelogram.writeText(message, font, style, size);
                    } else if (sCurrentLine.equals("Output")) {
                        int xCenter = Integer.parseInt(br.readLine());
                        int yCenter = Integer.parseInt(br.readLine());
                        int width = Integer.parseInt(br.readLine());
                        int height = Integer.parseInt(br.readLine());

                        Output output = new Output(xCenter, yCenter, width, height);
                        output.createCoordination(xCenter, yCenter, width, height);
                        DrawPanel.addShape.add(output);

                        String message = br.readLine();
                        String font = br.readLine();
                        String style = br.readLine();
                        int size = Integer.parseInt(br.readLine());

                        output.writeText(message, font, style, size);
                    } else if (sCurrentLine.equals("Diamond")) {
                        int xCenter = Integer.parseInt(br.readLine());
                        int yCenter = Integer.parseInt(br.readLine());
                        int width = Integer.parseInt(br.readLine());
                        int height = Integer.parseInt(br.readLine());

                        Diamond diamond = new Diamond(xCenter, yCenter, width, height);
                        diamond.createCoordination(xCenter, yCenter, width, height);
                        DrawPanel.addShape.add(diamond);

                        String message = br.readLine();
                        String font = br.readLine();
                        String style = br.readLine();
                        int size = Integer.parseInt(br.readLine());

                        diamond.writeText(message, font, style, size);

                        String left = br.readLine();
                        String down = br.readLine();
                        String right = br.readLine();

                        diamond.setLeftTag(left);
                        diamond.setRightTag(right);

                        if (down.equals("null")) {
                            diamond.setDownTag(null);
                        } else {
                            diamond.setDownTag(down);
                        }
                    }
                }
                for (int i = 0; i < shapeLength - 1; i++) {
                    for (int j = 0; j < 8; j++) {
                        int nextPointLength = Integer.parseInt(br.readLine());
                        for (int k = 0; k < nextPointLength - 1; k++) {
                            int xNext = Integer.parseInt(br.readLine());
                            int yNext = Integer.parseInt(br.readLine());
                            Point p = DrawPanel.addShape.getShape()[i].pointArray[j].findPoint(xNext, yNext);
                            DrawPanel.addShape.getShape()[i].pointArray[j].addConnection(p);
                        }
                    }
                }
            }

        } catch (IOException e) {
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    public void writeFile() {
        FileWriter fw;
        BufferedWriter bw;

        try {
            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);

            //bw.write(DrawPanel.addShape.getShapeLength());
            bw.write(String.valueOf(DrawPanel.addShape.getShapeLength()) + "\n");
            //bw.newLine();

            for (Shape sh : DrawPanel.addShape.getShape()) {
                if (sh != null) {
                    bw.write(sh.type() + "\n");
                    bw.write(sh.XCenter + "\n");
                    bw.write(sh.YCenter + "\n");
                    bw.write(sh.width + "\n");
                    bw.write(sh.height + "\n");

                    bw.write(sh.readText().getMessage() + "\n");
                    bw.write(sh.readText().getFont() + "\n");
                    bw.write(sh.readText().getStyle() + "\n");

                    if (sh.readText().getMessage() == null) {
                        bw.write(String.valueOf(0) + "\n");
                    } else {
                        bw.write(sh.readText().getSize() + "\n");
                    }

                    if (sh.type() == ShapeEnum.Diamond) {
                        bw.write(sh.leftTag + "\n");
                        bw.write(sh.downTag + "\n");
                        bw.write(sh.rightTag + "\n");
                    }
                }
            }

            for (Shape sh : DrawPanel.addShape.getShape()) {
                if (sh != null) {
                    for (int i = 0; i < 8; i++) {

                        bw.write(sh.pointArray[i].getNextPointLength() + "\n");
                        for (int j = 0; j < sh.pointArray[i].getNextPointLength() - 1; j++) {
                            bw.write(sh.pointArray[i].getNextPoint()[j].getX() + "\n");
                            bw.write(sh.pointArray[i].getNextPoint()[j].getY() + "\n");
                        }
                    }
                }
            }

            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
