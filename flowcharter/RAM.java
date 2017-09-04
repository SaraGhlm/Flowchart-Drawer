package flowcharter;

import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Sara
 */
public class RAM {

    static int variableLength = 1;
    static Variable[] variable = new Variable[variableLength];

    private final Shape[] shape = DrawPanel.addShape.getShape();
    private final int shapeLng = DrawPanel.addShape.getShapeLength();

    private int countOfPara = 0;
    private int countOfStart = 0;
    private int countOfEnd = 0;
    private int countOfConnection = 0;

    private Shape tempShape;

    private int diamondLength = 1;
    private Shape[] diamond = new Shape[diamondLength];

    private boolean reachedEnd = false;
    private boolean onceFindEnd = false;
    private boolean haveStart = false;
    private boolean haveEnd = false;
    private boolean havePara = false;
    private boolean Continue = false;

    public RAM() {
        readArray();
    }

    private void readArray() {

        //at first check the array to have Start, End and a parallelogram for
        //declaring and initialization.
        for (Shape sh : shape) {
            if (sh != null) {
                if (sh.type() == ShapeEnum.Oval) {
                    if (sh.readText().getMessage() != null) {
                        if (sh.readText().getMessage().equalsIgnoreCase("START")) {
                            haveStart = true;
                            countOfStart++;
                            Continue = true;
                            tempShape = sh;
                        } else if (sh.readText().getMessage().equalsIgnoreCase("END")) {
                            haveEnd = true;
                            countOfEnd++;
                            Continue = true;
                        }
                    }
                } else if (sh.type() == ShapeEnum.Parallelogram) {
                    countOfPara++;
                    havePara = true;
                    Continue = true;
                }
            }
        }

        //If the array does not have start or end or parallelogram, it would not 
        //continue to compile it.
        if (!haveStart) {
            JOptionPane.showMessageDialog(null, "Does not have start!!!", "Error", 0);
            Continue = false;
        } else if (countOfStart > 1) {
            JOptionPane.showMessageDialog(null, "Can not have more than one start!!!", "Error", 0);
            Continue = false;
        }

        if (!haveEnd) {
            JOptionPane.showMessageDialog(null, "Does not have end!!!", "Error", 0);
            Continue = false;
        } else if (countOfEnd > 1) {
            JOptionPane.showMessageDialog(null, "Can not have more than one end!!!", "Error", 0);
            Continue = false;
        }

        if (!havePara) {
            JOptionPane.showMessageDialog(null, "Did not declare any variable!!!", "Error", 0);
            Continue = false;
        } else if (countOfPara > 1) {
            JOptionPane.showMessageDialog(null, "Can not declare variable in more than one place!!!", "Error", 0);
            Continue = false;
        }

        //If every thing is alright, then it check, does the start connected to parallelogram...
        if (Continue) {
            for (int i = 0; i < 8; i++) {
                int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                if (nextPointLength > 2) {
                    JOptionPane.showMessageDialog(null, "Start con not have more than one connection!!!", "Error", 0);
                    Continue = false;
                    break;
                }
            }
            if (Continue) {
                for (int i = 0; i < 8; i++) {
                    int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                    if (nextPointLength == 2) {
                        countOfConnection++;
                    }
                }
                if (countOfConnection == 1) {
                    for (int i = 0; i < 8; i++) {
                        int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                        if (nextPointLength == 2) {
                            ShapeEnum typ = tempShape.getPoint()[i].getNextPoint()[0].getShape().type();
                            if (typ == ShapeEnum.Parallelogram) {
                                tempShape.watched = true;
                                tempShape = tempShape.getPoint()[i].getNextPoint()[0].getShape();
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Should declare variables at first!!!", "Error", 0);
                                Continue = false;
                            }
                        }
                    }
                } else if (countOfConnection == 0) {
                    JOptionPane.showMessageDialog(null, "Start should be connected!!!", "Error", 0);
                    Continue = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Start con not have more than one connection!!!", "Error", 0);
                    Continue = false;
                }
            }
        }

        countOfConnection = 0;

        //Then start from parallelogram, to check can reach the end.
        if (Continue) {
            //System.out.println("start checking para");
            do {
                //if shape is the end 
                if (isEnd(tempShape)) {
                    //System.out.println("reached the END");
                    reachedEnd = true;
                    tempShape.watched = true;
                    onceFindEnd = true;
                }

                //if shape is not watch before, then can check it.
                if (tempShape.watched == false) {
                    if (tempShape.type() != ShapeEnum.Diamond && !isEnd(tempShape)) {
                        //System.out.println("start checking " + tempShape.type());

                        for (int i = 0; i < 8; i++) {
                            int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                            if (nextPointLength > 2) {
                                JOptionPane.showMessageDialog(null, "Can not have more than one connection from a point!!!", "Error", 0);
                                Continue = false;
                                break;
                            }
                        }
                        if (Continue) {
                            for (int i = 0; i < 8; i++) {
                                int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                                if (nextPointLength == 2) {
                                    countOfConnection++;
                                }
                            }
                            if (countOfConnection == 1) {
                                for (int i = 0; i < 8; i++) {
                                    int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                                    if (nextPointLength == 2) {
                                        tempShape.watched = true;
                                        tempShape = tempShape.getPoint()[i].getNextPoint()[0].getShape();
                                        //System.out.println("next shape is a "+tempShape.type());
                                        break;
                                    }
                                }
                            } else if (countOfConnection == 0) {
                                JOptionPane.showMessageDialog(null, "All the shape should have connection!!!", "Error", 0);
                                Continue = false;
                            } else {
                                JOptionPane.showMessageDialog(null, "All the shapes except diamond should have only one connection!!!", "Error", 0);
                                Continue = false;
                            }
                            countOfConnection = 0;
                        }
                    } else if (tempShape.type() == ShapeEnum.Diamond && !isEnd(tempShape)) {
                        //System.out.println("start checking diamond");
                        for (int i = 0; i < 8; i++) {
                            int nextPointLength = tempShape.getPoint()[i].getNextPointLength();
                            if (nextPointLength > 2) {
                                JOptionPane.showMessageDialog(null, "Can not have more than one connection from a point!!!", "Error", 0);
                                Continue = false;
                                break;
                            }
                            if (nextPointLength == 2) {
                                countOfConnection++;
                            }
                        }
                        if (Continue) {
                            if (countOfConnection == 2 || countOfConnection == 3) {
                                //System.out.println("add diamond to the array");
                                addDiamond(tempShape);
                                tempShape.watched = true;
                                tempShape.leftWatched = true;
                                tempShape = tempShape.getPoint()[7].getNextPoint()[0].getShape();
                            } else if (countOfConnection == 0) {
                                JOptionPane.showMessageDialog(null, "All the shape should have connection!!!", "Error", 0);
                                Continue = false;
                            } else if (countOfConnection == 1) {
                                JOptionPane.showMessageDialog(null, "Diamonds should have more than one output connection!!!", "Error", 0);
                                Continue = false;
                            }
                            countOfConnection = 0;
                        }
                    }
                } else if (!reachedEnd && tempShape.watched == true) {
                    //System.out.println("Ever enter here!!!");
                    if (diamond[diamondLength - 2].downTag != null) {
                        if (diamond[diamondLength - 2].downWatched == false) {
                            tempShape = diamond[diamondLength - 1].getPoint()[5].getNextPoint()[0].getShape();
                            diamond[diamondLength - 2].downWatched = true;
                            //System.out.println("next shape is a "+tempShape.type());
                        } else if (diamond[diamondLength - 2].rightWatched == false) {
                            tempShape = diamond[diamondLength - 2].getPoint()[3].getNextPoint()[0].getShape();
                            diamond[diamondLength - 2].rightWatched = true;
                            //System.out.println("next shape is a "+tempShape.type());
                        } else {
                            diamondLength--;
                        }
                    } else {
                        if (diamond[diamondLength - 2].rightWatched == false) {
                            tempShape = diamond[diamondLength - 2].getPoint()[3].getNextPoint()[0].getShape();
                            diamond[diamondLength - 2].rightWatched = true;
                            //System.out.println("next shape is a "+tempShape.type());
                        } else {
                            diamondLength--;
                        }
                    }
                }

                if (reachedEnd) {
                    //System.out.println("check to see, is there any diamond???");
                    if (diamond[0] != null) {
                        //System.out.println("yes, there is...");
                        if (diamond[diamondLength - 2].downTag == null) {
                            if (diamond[diamondLength - 2].rightWatched == false) {
                                tempShape = diamond[diamondLength - 2].getPoint()[3].getNextPoint()[0].getShape();
                                diamond[diamondLength - 2].rightWatched = true;
                                //System.out.println("next shape is a "+tempShape.type());
                            } else {
                                diamondLength--;
                            }
                        } else {
                            if (diamond[diamondLength - 2].downWatched == false) {
                                tempShape = diamond[diamondLength - 2].getPoint()[5].getNextPoint()[0].getShape();
                                diamond[diamondLength - 2].downWatched = true;
                                //System.out.println("next shape is a "+tempShape.type());
                            } else if (diamond[diamondLength - 2].rightWatched == false) {
                                tempShape = diamond[diamondLength - 2].getPoint()[3].getNextPoint()[0].getShape();
                                diamond[diamondLength - 2].rightWatched = true;
                                //System.out.println("next shape is a "+tempShape.type());
                            } else {
                                diamondLength--;
                            }
                        }
                    }
                    reachedEnd = false;
                }
                if (!Continue) {
                    break;
                }
            } while (isAllWatched());

            if (!onceFindEnd) {
                JOptionPane.showMessageDialog(null, "There is no connection to end point!!!", "Error", 0);
            } else {
                ALU alu = new ALU();
            }
            //System.out.println("All done...");
        }
    }

    /**
     * check the shape, to see is it the end of the flowchart.
     *
     * @param sh is a shape
     * @return
     */
    private boolean isEnd(Shape sh) {

        if (sh.type() == ShapeEnum.Oval) {
            if (sh.watched == false) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllWatched() {
        boolean wtc = false;
        for (Shape sh : shape) {
            if (sh != null) {
                if (sh.watched == false) {
                    wtc = true;
                    //System.out.println(sh.type() + " is not watch yet");
                }
            }
        }
        return wtc;
    }

    private void addDiamond(Shape sh) {
        diamond[diamondLength - 1] = sh;
        Shape temp[] = Arrays.copyOf(diamond, diamondLength + 1);
        diamond = temp;
        diamondLength++;
    }

    public static void addVariable(Variable v) {
        variable[variableLength - 1] = v;
        Variable[] temp = Arrays.copyOf(variable, variableLength + 1);
        variable = temp;
        variableLength++;
    }
}
