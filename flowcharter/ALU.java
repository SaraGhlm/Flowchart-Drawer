package flowcharter;

import javax.swing.JOptionPane;

/**
 *
 * @author Sara
 */
public class ALU {

    private final Shape[] shape = DrawPanel.addShape.getShape();
    private Shape tempShape;
    private boolean found = false;
    private boolean error = false;

    private boolean leftOn = false;
    private boolean downOn = false;
    private boolean rightOn = false;

    private int outputIndex;

    public ALU() {
        compile();
    }

    private void compile() {
        String[] message;
        //at first should find the start.
        for (Shape sh : shape) {
            if (sh != null) {
                if (sh.type() == ShapeEnum.Oval) {
                    if (sh.readText().getMessage() != null) {
                        if (sh.readText().getMessage().equalsIgnoreCase("START")) {
                            tempShape = sh;

                            //when found start, should search for the parallelogram.
                            for (int i = 0; i < 8; i++) {
                                int nextPointLength = tempShape.pointArray[i].getNextPointLength();
                                if (nextPointLength == 2) {
                                    tempShape = tempShape.pointArray[i].getNextPoint()[0].getShape();
                                }
                            }
                        }
                    }
                }
            }
        }

        do {
            String string = tempShape.readText().getMessage();

            if (tempShape.type() == ShapeEnum.Parallelogram) {
                boolean match = string.matches("(\\s*[a-zA-Z]+\\s*={1}\\s*\\d+\\"
                        + "s*;{1}\\s*|\\s*[a-zA-Z]+\\s*;{1}\\s*)+");
                if (match) {
                    message = string.split(";");
                    for (String varb : message) {
                        if (varb.matches("\\s*[a-zA-Z]+\\s*={1}\\s*\\d+\\s*")) {
                            varb = varb.replaceAll("\\s", "");
                            String[] splt = varb.split("=");
                            Variable v = new Variable(splt[0], Integer.parseInt(splt[1]));
                            RAM.addVariable(v);
                            showVariable();
                        }
                        if (varb.matches("\\s*[a-zA-Z]+\\s*")) {
                            varb = varb.replaceAll("\\s", "");
                            Variable v = new Variable(varb, 0);
                            RAM.addVariable(v);
                            showVariable();
                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        int nextLength = tempShape.pointArray[i].getNextPointLength();
                        if (nextLength > 1) {
                            tempShape = tempShape.pointArray[i].getNextPoint()[0].getShape();
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Syntax error!!!", "Erro"
                            + "r", JOptionPane.OK_OPTION);
                    break;
                }
            } else if (tempShape.type() == ShapeEnum.Rectangle) {
                boolean match = string.matches("(\\s*[a-zA-Z]+\\s*={1}\\s*(\\d+|"
                        + "[a-zA-Z]+)\\s*(\\+|-|\\*|/|%){1}\\s*(\\d+|[a-zA-Z]+)"
                        + "\\s*;{1}\\s*)+");

                if (match) {
                    message = string.split(";");
                    for (String varb : message) {
                        varb = varb.replaceAll("\\s", "");
                        String[] splt = varb.split("=");
                        String varName = splt[0];

                        //if calculate two number
                        if (splt[1].matches("\\d+(\\+|-|\\*|/){1}\\d+")) {

                            if (splt[1].contains("+")) {
                                String[] st = splt[1].split("\\+");
                                int num1 = Integer.parseInt(st[0]);
                                int num2 = Integer.parseInt(st[1]);
                                int index = findVariable(varName);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    RAM.variable[index].setValue(num1 + num2);
                                    found = false;
                                }
                            }

                            if (splt[1].contains("-")) {
                                String[] st = splt[1].split("-");
                                int num1 = Integer.parseInt(st[0]);
                                int num2 = Integer.parseInt(st[1]);
                                int index = findVariable(varName);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    RAM.variable[index].setValue(num1 - num2);
                                    found = false;
                                }
                            }

                            if (splt[1].contains("*")) {
                                String[] st = splt[1].split("\\*");
                                int num1 = Integer.parseInt(st[0]);
                                int num2 = Integer.parseInt(st[1]);
                                int index = findVariable(varName);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    RAM.variable[index].setValue(num1 * num2);
                                    found = false;
                                }
                            }

                            if (splt[1].contains("/")) {
                                String[] st = splt[1].split("/");
                                int num1 = Integer.parseInt(st[0]);
                                try {
                                    int num2 = Integer.parseInt(st[1]);
                                    int index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not "
                                                + "declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 / num2);
                                        found = false;
                                    }
                                } catch (ArithmeticException e) {
                                    JOptionPane.showMessageDialog(null, "Divided"
                                            + " by zero!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    error = true;
                                    break;
                                }
                            }

                            if (splt[1].contains("%")) {
                                String[] st = splt[1].split("%");
                                int num1 = Integer.parseInt(st[0]);
                                int num2 = Integer.parseInt(st[1]);
                                int index = findVariable(varName);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    RAM.variable[index].setValue(num1 % num2);
                                    found = false;
                                }
                            }
                        }

                        //if calculate a number and a varaible
                        if (splt[1].matches("\\d+(\\+|-|\\*|/){1}[a-zA-Z]+")) {

                            if (splt[1].contains("+")) {
                                String[] st = splt[1].split("\\+");
                                int num1 = Integer.parseInt(st[0]);
                                int index = findVariable(st[1]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num2 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 + num2);
                                        found = false;
                                    }
                                }
                            }

                            if (splt[1].contains("-")) {
                                String[] st = splt[1].split("-");
                                int num1 = Integer.parseInt(st[0]);
                                int index = findVariable(st[1]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num2 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 - num2);
                                        found = false;
                                    }
                                }
                            }

                            if (splt[1].contains("*")) {
                                String[] st = splt[1].split("\\*");
                                int num1 = Integer.parseInt(st[0]);
                                int index = findVariable(st[1]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num2 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 * num2);
                                        found = false;
                                    }
                                }
                            }

                            if (splt[1].contains("/")) {
                                String[] st = splt[1].split("/");
                                int num1 = Integer.parseInt(st[0]);
                                int index = findVariable(st[1]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    try {
                                        int num2 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(varName);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            RAM.variable[index].setValue(num1 / num2);
                                            found = false;
                                        }
                                    } catch (ArithmeticException e) {
                                        JOptionPane.showMessageDialog(null, "Div"
                                                + "ided by zero!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        error = true;
                                        break;
                                    }
                                }
                            }

                            if (splt[1].contains("%")) {
                                String[] st = splt[1].split("%");
                                int num1 = Integer.parseInt(st[0]);
                                int index = findVariable(st[1]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num2 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 % num2);
                                        found = false;
                                    }
                                }
                            }
                        }

                        //if calculate a number and a variable
                        if (splt[1].matches("[a-zA-Z]+(\\+|-|\\*|/){1}\\d+")) {

                            if (splt[1].contains("+")) {
                                String[] st = splt[1].split("\\+");
                                int num1 = Integer.parseInt(st[1]);
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num2 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 + num2);
                                        found = false;
                                    }
                                }
                            }

                            if (splt[1].contains("-")) {
                                String[] st = splt[1].split("-");
                                int num2 = Integer.parseInt(st[1]);
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num1 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 - num2);
                                        found = false;
                                    }
                                }
                            }

                            if (splt[1].contains("*")) {
                                String[] st = splt[1].split("\\*");
                                int num1 = Integer.parseInt(st[1]);
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num2 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 * num2);
                                        found = false;
                                    }
                                }
                            }

                            if (splt[1].contains("/")) {
                                String[] st = splt[1].split("/");
                                int num2 = Integer.parseInt(st[1]);
                                try {
                                    int index = findVariable(st[0]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num1 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(varName);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            RAM.variable[index].setValue(num1 / num2);
                                            found = false;
                                        }
                                    }
                                } catch (ArithmeticException e) {
                                    JOptionPane.showMessageDialog(null, "Divided"
                                            + " by zero!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    error = true;
                                    break;
                                }
                            }

                            if (splt[1].contains("%")) {
                                String[] st = splt[1].split("%");
                                int num2 = Integer.parseInt(st[1]);
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num1 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(varName);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        RAM.variable[index].setValue(num1 % num2);
                                        found = false;
                                    }
                                }
                            }
                        }
                        //calculate two variable
                        if (splt[1].matches("[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+")) {

                            //System.out.println("Is it checking??");
                            if (splt[1].contains("+")) {
                                String[] st = splt[1].split("\\+");
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num1 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(st[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(varName);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            RAM.variable[index].setValue(num1 + num2);
                                            found = false;
                                        }
                                    }
                                }
                            }

                            if (splt[1].contains("-")) {
                                String[] st = splt[1].split("-");
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num1 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(st[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(varName);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            RAM.variable[index].setValue(num1 - num2);
                                            found = false;
                                        }
                                    }
                                }
                            }

                            if (splt[1].contains("*")) {
                                String[] st = splt[1].split("\\*");
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num1 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(st[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(varName);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            RAM.variable[index].setValue(num1 * num2);
                                            found = false;
                                        }
                                    }
                                }
                            }

                            if (splt[1].contains("/")) {
                                String[] st = splt[1].split("/");
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    try {
                                        int num1 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(st[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index].getValue();
                                            found = false;
                                            index = findVariable(varName);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                RAM.variable[index].setValue(num1 / num2);
                                                found = false;
                                            }
                                        }
                                    } catch (ArithmeticException e) {
                                        JOptionPane.showMessageDialog(null, "Div"
                                                + "ided by zero!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        error = true;
                                        break;
                                    }
                                }
                            }

                            if (splt[1].contains("%")) {
                                String[] st = splt[1].split("%");
                                int index = findVariable(st[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num1 = RAM.variable[index].getValue();
                                    found = false;
                                    index = findVariable(st[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        found = false;
                                        index = findVariable(varName);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            RAM.variable[index].setValue(num1 % num2);
                                            found = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        int nextLength = tempShape.pointArray[i].getNextPointLength();
                        if (nextLength > 1) {
                            tempShape = tempShape.pointArray[i].getNextPoint()[0].getShape();
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Syntax error!!!",
                            "Error", JOptionPane.OK_OPTION);
                    break;
                }
            } else if (tempShape.type() == ShapeEnum.Diamond) {
                string = string.replaceAll("\\s", "");
                boolean match = string.matches("((\\d+|[a-zA-Z]+)(>|>=|=|<=|<){1}"
                        + "(\\d+|[a-zA-Z]+)|[a-zA-Z]+){1}");

                if (match) {
                    match = string.matches("[a-zA-Z]");
                    if (match) {

                        int index = findVariable(string);
                        if (!found) {
                            JOptionPane.showMessageDialog(null, "Not declare var"
                                    + "iable!!!", "Error", JOptionPane.OK_OPTION);
                            break;
                        } else {
                            int num = RAM.variable[index].getValue();

                            String left = tempShape.leftTag;
                            match = left.matches("([a-zA-Z]+(>|>=|=|<=|<){1}"
                                    + "[a-zA-Z]+){1}|([a-zA-Z]+(>|>=|=|<=|<){1}){1}"
                                    + "|((>|>=|=|<=|<){1}[a-zA-Z]+){1}|([a-zA-Z]+"
                                    + "(>|>=|=|<=|<){1}\\d+){1}|(\\d+(>|>=|=|<=|<){1}"
                                    + "[a-zA-Z]+){1}|(\\d+(>|>=|=|<=|<){1}){1}|"
                                    + "(\\d+(>|>=|=|<=|<){1}){1}");
                            if (match) {

                                match = left.matches("([a-zA-Z]+(>|>=|=|<=|<){1}"
                                        + "[a-zA-Z]+){1}");
                                if (match) {
                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        if (message[0].equals(string)) {
                                            int index2 = findVariable(message[1]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num > num2) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }
                                        } else if (message[1].equals(string)) {
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 > num) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        if (message[0].equals(string)) {
                                            int index2 = findVariable(message[1]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num < num2) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }
                                        } else if (message[1].equals(string)) {
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 < num) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        }
                                    }

                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        if (message[0].equals(string)) {
                                            int index2 = findVariable(message[1]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num == num2) {
                                                    tempShape = tempShape.pointArray[7].getNextPoint()[0]
                                                            .getShape();
                                                    leftOn = true;
                                                }
                                            }
                                        } else if (message[1].equals(string)) {
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].
                                                        getValue();
                                                if (num2 == num) {
                                                    tempShape = tempShape.pointArray[7].
                                                            getNextPoint()[0].
                                                            getShape();
                                                    leftOn = true;
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        if (message[0].equals(string)) {
                                            int index2 = findVariable(message[1]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num >= num2) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }
                                        } else if (message[1].equals(string)) {
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 >= num) {
                                                    tempShape = tempShape.pointArray[7].getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        if (message[0].equals(string)) {
                                            int index2 = findVariable(message[1]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].
                                                        getValue();
                                                if (num <= num2) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }
                                        } else if (message[1].equals(string)) {
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Not declare variable!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].
                                                        getValue();
                                                if (num2 <= num) {
                                                    tempShape = tempShape.pointArray[7].
                                                            getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                }
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        }
                                    }
                                }

                                match = left.matches("([a-zA-Z]+(>|>=|=|<=|<){1}){1}");
                                if (match) {

                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num2 > num) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num2 < num) {
                                                tempShape = tempShape.pointArray[7].getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num2 == num) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num2 >= num) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num2 <= num) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }
                                }

                                match = left.matches("((>|>=|=|<=|<){1}[a-zA-Z]+){1}");
                                if (match) {

                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num > num2) {
                                                tempShape = tempShape.pointArray[7].getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num < num2) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }
                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num == num2) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num >= num2) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].
                                                    getValue();
                                            if (num <= num2) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }
                                }

                                match = left.matches("([a-zA-Z]+(>|>=|=|<=|<){1}\\d+){1}");
                                if (match) {
                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 > num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 < num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 == num3) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 >= num3) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 <= num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }
                                }

                                match = left.matches("(\\d+(>|>=|=|<=|<){1}[a-zA-Z]+){1}");
                                if (match) {
                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 > num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 < num3) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 == num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 >= num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Left tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 <= num3) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            }
                                        }
                                    }
                                }

                                match = left.matches("(\\d+(>|>=|=|<=|<){1}){1}");
                                if (match) {
                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 > num) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 < num) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 == num) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 >= num) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 <= num) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }
                                }

                                match = left.matches("((>|>=|=|<=|<){1}\\d+){1}");
                                if (match) {
                                    if (left.contains(">")) {
                                        message = left.split(">");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num > num2) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains("<")) {
                                        message = left.split("<");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num < num2) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains("=")) {
                                        message = left.split("=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num == num2) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains(">=")) {
                                        message = left.split(">=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num >= num2) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }

                                    if (left.contains("<=")) {
                                        message = left.split("<=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num <= num2) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        }
                                    }
                                }
                            }

                            if (!leftOn) {
                                String down = tempShape.downTag;
                                match = down.matches("([a-zA-Z]+(>|>=|=|<=|<){1}"
                                        + "[a-zA-Z]+){1}|([a-zA-Z]+(>|>=|=|<=|<)"
                                        + "{1}){1}|((>|>=|=|<=|<){1}[a-zA-Z]+){1}");

                                if (match) {

                                    match = down.matches("([a-zA-Z]+(>|>=|=|<=|<)"
                                            + "{1}[a-zA-Z]+){1}");
                                    if (match) {
                                        if (down.contains(">")) {
                                            message = down.split(">");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num > num2) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0].getShape();
                                                        downOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].
                                                            getValue();
                                                    if (num2 > num) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        downOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null,
                                                        "Down tag is not correct!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (down.contains("<")) {
                                            message = down.split("<");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].
                                                            getValue();
                                                    if (num < num2) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        downOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2]
                                                            .getValue();
                                                    if (num2 < num) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0].
                                                                getShape();
                                                        downOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null,
                                                        "Down tag is not correct!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (down.contains("=")) {
                                            message = down.split("=");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].
                                                            getValue();
                                                    if (num == num2) {
                                                        tempShape = tempShape.pointArray[5]
                                                                .getNextPoint()[0].
                                                                getShape();
                                                        downOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].
                                                            getValue();
                                                    if (num2 == num) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0].getShape();
                                                        downOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null,
                                                        "Down tag is not correct!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (down.contains(">=")) {
                                            message = down.split(">=");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Not declare variable!!!",
                                                            "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2]
                                                            .getValue();
                                                    if (num >= num2) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        downOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2]
                                                            .getValue();
                                                    if (num2 >= num) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        downOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (down.contains("<=")) {
                                            message = down.split("<=");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num <= num2) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0].getShape();
                                                        downOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num2 <= num) {
                                                        tempShape = tempShape.pointArray[5].
                                                                getNextPoint()[0].
                                                                getShape();
                                                        downOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }
                                    }

                                    match = down.matches("([a-zA-Z]+(>|>=|=|<=|<){1}){1}");
                                    if (match) {

                                        if (down.contains(">")) {
                                            message = down.split(">");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 > num) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains("<")) {
                                            message = down.split("<");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 < num) {
                                                    tempShape = tempShape.pointArray[5].
                                                            getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains("=")) {
                                            message = down.split("=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 == num) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains(">=")) {
                                            message = down.split(">=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 >= num) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains("<=")) {
                                            message = down.split("<=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 <= num) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }
                                    }

                                    match = down.matches("((>|>=|=|<=|<){1}[a-zA-Z]+){1}");
                                    if (match) {

                                        if (down.contains(">")) {
                                            message = down.split(">");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num > num2) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains("<")) {
                                            message = down.split("<");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num < num2) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }
                                        if (down.contains("=")) {
                                            message = down.split("=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num == num2) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains(">=")) {
                                            message = down.split(">=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num >= num2) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }

                                        if (down.contains("<=")) {
                                            message = down.split("<=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num <= num2) {
                                                    tempShape = tempShape.pointArray[5]
                                                            .getNextPoint()[0].getShape();
                                                    downOn = true;
                                                }
                                            }
                                        }
                                    }
                                }

                                match = down.matches("([a-zA-Z]+(>|>=|=|<=|<){1}\\d+){1}");
                                if (match) {
                                    if (down.contains(">")) {
                                        message = down.split(">");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 > num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains("<")) {
                                        message = down.split("<");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 < num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains("=")) {
                                        message = down.split("=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 == num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains(">=")) {
                                        message = down.split(">=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 >= num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains("<=")) {
                                        message = down.split("<=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 <= num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }
                                }

                                match = down.matches("(\\d+(>|>=|=|<=|<){1}[a-zA-Z]+){1}");
                                if (match) {
                                    if (down.contains(">")) {
                                        message = down.split(">");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 > num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains("<")) {
                                        message = down.split("<");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 < num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains("=")) {
                                        message = down.split("=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 == num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains(">=")) {
                                        message = down.split(">=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 >= num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }

                                    if (down.contains("<=")) {
                                        message = down.split("<=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null, "Down tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 <= num3) {
                                                tempShape = tempShape.pointArray[5]
                                                        .getNextPoint()[0].getShape();
                                                downOn = true;
                                            }
                                        }
                                    }
                                }

                                match = down.matches("(\\d+(>|>=|=|<=|<){1}){1}");
                                if (match) {
                                    if (down.contains(">")) {
                                        message = down.split(">");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 > num) {
                                            tempShape = tempShape.pointArray[5]
                                                    .getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains("<")) {
                                        message = down.split("<");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 < num) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains("=")) {
                                        message = down.split("=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 == num) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains(">=")) {
                                        message = down.split(">=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 >= num) {
                                            tempShape = tempShape.pointArray[5]
                                                    .getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains("<=")) {
                                        message = down.split("<=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 <= num) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }
                                }

                                match = down.matches("((>|>=|=|<=|<){1}\\d+){1}");
                                if (match) {
                                    if (down.contains(">")) {
                                        message = down.split(">");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num > num2) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains("<")) {
                                        message = down.split("<");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num < num2) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains("=")) {
                                        message = down.split("=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num == num2) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains(">=")) {
                                        message = down.split(">=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num >= num2) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }

                                    if (down.contains("<=")) {
                                        message = down.split("<=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num <= num2) {
                                            tempShape = tempShape.pointArray[5].
                                                    getNextPoint()[0].getShape();
                                            downOn = true;
                                        }
                                    }
                                }
                            }

                            if (!leftOn && !downOn) {
                                String right = tempShape.rightTag;
                                match = right.matches("([a-zA-Z]+(>|>=|=|<=|<){1}"
                                        + "[a-zA-Z]+){1}|([a-zA-Z]+(>|>=|=|<=|<){1}){1}"
                                        + "|((>|>=|=|<=|<){1}[a-zA-Z]+){1}");

                                if (match) {

                                    match = right.matches("([a-zA-Z]+(>|>=|=|<=|<)"
                                            + "{1}[a-zA-Z]+){1}");
                                    if (match) {
                                        if (right.contains(">")) {
                                            message = right.split(">");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num > num2) {
                                                        tempShape = tempShape.pointArray[3]
                                                                .getNextPoint()[0].getShape();
                                                        rightOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num2 > num) {
                                                        tempShape = tempShape.pointArray[3]
                                                                .getNextPoint()[0].getShape();
                                                        rightOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (right.contains("<")) {
                                            message = right.split("<");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num < num2) {
                                                        tempShape = tempShape.pointArray[3]
                                                                .getNextPoint()[0].getShape();
                                                        rightOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num2 < num) {
                                                        tempShape = tempShape.pointArray[3].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        rightOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (right.contains("=")) {
                                            message = right.split("=");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num == num2) {
                                                        tempShape = tempShape.pointArray[3].getNextPoint()[0].getShape();
                                                        rightOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num2 == num) {
                                                        tempShape = tempShape.pointArray[3].getNextPoint()[0].getShape();
                                                        rightOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (right.contains(">=")) {
                                            message = right.split(">=");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num >= num2) {
                                                        tempShape = tempShape.pointArray[3].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        rightOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num2 >= num) {
                                                        tempShape = tempShape.pointArray[3].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        rightOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }

                                        if (right.contains("<=")) {
                                            message = right.split("<=");
                                            if (message[0].equals(string)) {
                                                int index2 = findVariable(message[1]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2]
                                                            .getValue();
                                                    if (num <= num2) {
                                                        tempShape = tempShape.pointArray[3].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        rightOn = true;
                                                    }
                                                }
                                            } else if (message[1].equals(string)) {
                                                int index2 = findVariable(message[0]);
                                                if (!found) {
                                                    JOptionPane.showMessageDialog(null, "Not declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                                    break;
                                                } else {
                                                    int num2 = RAM.variable[index2].getValue();
                                                    if (num2 <= num) {
                                                        tempShape = tempShape.pointArray[3].
                                                                getNextPoint()[0]
                                                                .getShape();
                                                        rightOn = true;
                                                    }
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            }
                                        }
                                    }

                                    match = right.matches("([a-zA-Z]+(>|>=|=|<=|<)"
                                            + "{1}){1}");
                                    if (match) {

                                        if (right.contains(">")) {
                                            message = right.split(">");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 > num) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains("<")) {
                                            message = right.split("<");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 < num) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains("=")) {
                                            message = right.split("=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 == num) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains(">=")) {
                                            message = right.split(">=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 >= num) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains("<=")) {
                                            message = right.split("<=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num2 <= num) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }

                                    match = right.matches("((>|>=|=|<=|<){1}"
                                            + "[a-zA-Z]+){1}");
                                    if (match) {

                                        if (right.contains(">")) {
                                            message = right.split(">");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num > num2) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains("<")) {
                                            message = right.split("<");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Right tag is not correct!!!", "Error",
                                                        JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num < num2) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                        if (right.contains("=")) {
                                            message = right.split("=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Right tag is not correct!!!",
                                                        "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2]
                                                        .getValue();
                                                if (num == num2) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains(">=")) {
                                            message = right.split(">=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num >= num2) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }

                                        if (right.contains("<=")) {
                                            message = right.split("<=");
                                            int index2 = findVariable(message[0]);
                                            if (!found) {
                                                JOptionPane.showMessageDialog(null, "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                                break;
                                            } else {
                                                int num2 = RAM.variable[index2].getValue();
                                                if (num <= num2) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }
                                }

                                match = right.matches("([a-zA-Z]+(>|>=|=|<=|<){1}"
                                        + "\\d+){1}");
                                if (match) {
                                    if (right.contains(">")) {
                                        message = right.split(">");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 > num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains("<")) {
                                        message = right.split("<");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 < num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains("=")) {
                                        message = right.split("=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!", "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 == num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains(">=")) {
                                        message = right.split(">=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 >= num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains("<=")) {
                                        message = right.split("<=");
                                        int index2 = findVariable(message[0]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            int num3 = Integer.parseInt(message[1]);
                                            if (num2 <= num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }

                                match = right.matches("(\\d+(>|>=|=|<=|<){1}"
                                        + "[a-zA-Z]+){1}");
                                if (match) {
                                    if (right.contains(">")) {
                                        message = right.split(">");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 > num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains("<")) {
                                        message = right.split("<");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 < num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains("=")) {
                                        message = right.split("=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 == num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains(">=")) {
                                        message = right.split(">=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 >= num3) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }

                                    if (right.contains("<=")) {
                                        message = right.split("<=");
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Right tag is not correct!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num3 = RAM.variable[index2].getValue();
                                            int num2 = Integer.parseInt(message[0]);
                                            if (num2 <= num3) {
                                                tempShape = tempShape.pointArray[3].
                                                        getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }

                                match = right.matches("(\\d+(>|>=|=|<=|<){1}){1}");
                                if (match) {
                                    if (right.contains(">")) {
                                        message = right.split(">");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 > num) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains("<")) {
                                        message = right.split("<");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 < num) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains("=")) {
                                        message = right.split("=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 == num) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains(">=")) {
                                        message = right.split(">=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 >= num) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains("<=")) {
                                        message = right.split("<=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num2 <= num) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                }

                                match = right.matches("((>|>=|=|<=|<){1}\\d+){1}");
                                if (match) {
                                    if (right.contains(">")) {
                                        message = right.split(">");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num > num2) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains("<")) {
                                        message = right.split("<");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num < num2) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains("=")) {
                                        message = right.split("=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num == num2) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains(">=")) {
                                        message = right.split(">=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num >= num2) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }

                                    if (right.contains("<=")) {
                                        message = right.split("<=");
                                        int num2 = Integer.parseInt(message[0]);
                                        if (num <= num2) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (!leftOn && !downOn && !rightOn) {
                            JOptionPane.showMessageDialog(null, "All the if cond"
                                    + "itions are false!!!", "Error", JOptionPane.OK_OPTION);
                            break;
                        }
                    } else {

                        if (string.contains(">")) {
                            message = string.split(">");
                            if (message[0].matches("\\d+")) {

                                int num = Integer.parseInt(message[0]);
                                if (message[1].matches("\\d+")) {
                                    int num2 = Integer.parseInt(message[1]);
                                    if (num > num2) {
                                        if (tempShape.leftTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    } else {
                                        if (tempShape.leftTag.equals("No")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("No")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                } else {
                                    int index = findVariable(message[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        if (num > num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }
                            } else if (message[0].matches("[a-zA-Z]")) {

                                int index = findVariable(message[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error", JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num = RAM.variable[index].getValue();

                                    if (message[1].matches("\\d+")) {
                                        int num2 = Integer.parseInt(message[1]);
                                        if (num > num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    } else {
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num > num2) {
                                                if (tempShape.leftTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            } else {
                                                if (tempShape.leftTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[3].
                                                            getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (string.contains("<")) {
                            message = string.split("<");
                            if (message[0].matches("\\d+")) {

                                int num = Integer.parseInt(message[0]);
                                if (message[1].matches("\\d+")) {
                                    int num2 = Integer.parseInt(message[1]);
                                    if (num < num2) {
                                        if (tempShape.leftTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    } else {
                                        if (tempShape.leftTag.equals("No")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("No")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                } else {
                                    int index = findVariable(message[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error", JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        if (num < num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3].
                                                        getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }
                            } else if (message[0].matches("[a-zA-Z]")) {

                                int index = findVariable(message[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error", JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num = RAM.variable[index].getValue();

                                    if (message[1].matches("\\d+")) {
                                        int num2 = Integer.parseInt(message[1]);
                                        if (num < num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3].
                                                        getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3].
                                                        getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    } else {
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num < num2) {
                                                if (tempShape.leftTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[7].
                                                            getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[3].
                                                            getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            } else {
                                                if (tempShape.leftTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[7].
                                                            getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[3].
                                                            getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (string.contains("=")) {
                            message = string.split("=");
                            if (message[0].matches("\\d+")) {

                                int num = Integer.parseInt(message[0]);
                                if (message[1].matches("\\d+")) {
                                    int num2 = Integer.parseInt(message[1]);
                                    if (num == num2) {
                                        if (tempShape.leftTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[7].
                                                    getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    } else {
                                        if (tempShape.leftTag.equals("No")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("No")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                } else {
                                    int index = findVariable(message[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not "
                                                + "declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        if (num == num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7].
                                                        getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }
                            } else if (message[0].matches("[a-zA-Z]")) {

                                int index = findVariable(message[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error", JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num = RAM.variable[index].getValue();

                                    if (message[1].matches("\\d+")) {
                                        int num2 = Integer.parseInt(message[1]);
                                        if (num == num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    } else {
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num == num2) {
                                                if (tempShape.leftTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            } else {
                                                if (tempShape.leftTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[7].
                                                            getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[3].
                                                            getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (string.contains(">=")) {
                            message = string.split(">=");
                            if (message[0].matches("\\d+")) {

                                int num = Integer.parseInt(message[0]);
                                if (message[1].matches("\\d+")) {
                                    int num2 = Integer.parseInt(message[1]);
                                    if (num >= num2) {
                                        if (tempShape.leftTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[7].
                                                    getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    } else {
                                        if (tempShape.leftTag.equals("No")) {
                                            tempShape = tempShape.pointArray[7].
                                                    getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("No")) {
                                            tempShape = tempShape.pointArray[3].
                                                    getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                } else {
                                    int index = findVariable(message[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null, "Not"
                                                + " declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        if (num >= num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }
                            } else if (message[0].matches("[a-zA-Z]")) {

                                int index = findVariable(message[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null, "Not dec"
                                            + "lare variable!!!", "Error", JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num = RAM.variable[index].getValue();

                                    if (message[1].matches("\\d+")) {
                                        int num2 = Integer.parseInt(message[1]);
                                        if (num >= num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    } else {
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num >= num2) {
                                                if (tempShape.leftTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            } else {
                                                if (tempShape.leftTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (string.contains("<=")) {
                            message = string.split("<=");
                            if (message[0].matches("\\d+")) {

                                int num = Integer.parseInt(message[0]);
                                if (message[1].matches("\\d+")) {
                                    int num2 = Integer.parseInt(message[1]);
                                    if (num <= num2) {
                                        if (tempShape.leftTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("Yes")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    } else {
                                        if (tempShape.leftTag.equals("No")) {
                                            tempShape = tempShape.pointArray[7]
                                                    .getNextPoint()[0].getShape();
                                            leftOn = true;
                                        } else if (tempShape.rightTag.equals("No")) {
                                            tempShape = tempShape.pointArray[3]
                                                    .getNextPoint()[0].getShape();
                                            rightOn = true;
                                        }
                                    }
                                } else {
                                    int index = findVariable(message[1]);
                                    if (!found) {
                                        JOptionPane.showMessageDialog(null,
                                                "Not declare variable!!!", "Error",
                                                JOptionPane.OK_OPTION);
                                        break;
                                    } else {
                                        int num2 = RAM.variable[index].getValue();
                                        if (num <= num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    }
                                }
                            } else if (message[0].matches("[a-zA-Z]")) {

                                int index = findVariable(message[0]);
                                if (!found) {
                                    JOptionPane.showMessageDialog(null,
                                            "Not declare variable!!!", "Error",
                                            JOptionPane.OK_OPTION);
                                    break;
                                } else {
                                    int num = RAM.variable[index].getValue();

                                    if (message[1].matches("\\d+")) {
                                        int num2 = Integer.parseInt(message[1]);
                                        if (num <= num2) {
                                            if (tempShape.leftTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("Yes")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        } else {
                                            if (tempShape.leftTag.equals("No")) {
                                                tempShape = tempShape.pointArray[7]
                                                        .getNextPoint()[0].getShape();
                                                leftOn = true;
                                            } else if (tempShape.rightTag.equals("No")) {
                                                tempShape = tempShape.pointArray[3]
                                                        .getNextPoint()[0].getShape();
                                                rightOn = true;
                                            }
                                        }
                                    } else {
                                        int index2 = findVariable(message[1]);
                                        if (!found) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Not declare variable!!!",
                                                    "Error", JOptionPane.OK_OPTION);
                                            break;
                                        } else {
                                            int num2 = RAM.variable[index2].getValue();
                                            if (num <= num2) {
                                                if (tempShape.leftTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("Yes")) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            } else {
                                                if (tempShape.leftTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[7]
                                                            .getNextPoint()[0].getShape();
                                                    leftOn = true;
                                                } else if (tempShape.rightTag.equals("No")) {
                                                    tempShape = tempShape.pointArray[3]
                                                            .getNextPoint()[0].getShape();
                                                    rightOn = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Syntax error!!!", "Error", JOptionPane.OK_OPTION);
                    break;
                }
            } else if (tempShape.type() == ShapeEnum.Output) {

                string = string.replaceAll("\\s", "");
                boolean match = string.matches("[a-zA-Z]+");
                if (match) {
                    int index = findVariable(string);
                    outputIndex = index;
                    showOutput();
                } else {
                    JOptionPane.showMessageDialog(null, "Not declare variabe!!!", "Error", JOptionPane.OK_OPTION);
                    break;
                }
                for (int i = 0; i < 8; i++) {
                    int nextLength = tempShape.pointArray[i].getNextPointLength();
                    if (nextLength > 1) {
                        tempShape = tempShape.pointArray[i].getNextPoint()[0].getShape();
                        break;
                    }
                }
            }

            showVariable();
        } while (!isEnd(tempShape));
    }

    private boolean isEnd(Shape sh) {

        if (sh.type() == ShapeEnum.Oval) {
            if (sh.readText().getMessage() != null) {
                if (sh.readText().getMessage().equalsIgnoreCase("END")) {
                    return true;
                }
            }
        }
        return false;
    }

    private int findVariable(String name) {
        int num = 0;
        for (int i = 0; i < RAM.variableLength - 1; i++) {
            if (name.equals(RAM.variable[i].getVariant())) {
                num = i;
                found = true;
            }
        }
        return num;
    }

    private void showVariable() {
        String varString = "";
        for (Variable var : RAM.variable) {
            if (var != null) {
                varString = varString + var.getVariant() + " : " + var.getValue() + ";";
            }
        }
        DisplayOutput.showVar(varString);
    }

    private void showOutput() {
        String outputString = "";
        outputString = outputString + RAM.variable[outputIndex].getVariant() + " : " + RAM.variable[outputIndex].getValue();
        DisplayOutput.showOutput(outputString);
    }
}
