package flowcharter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Sara
 */
public class DisplayOutput extends JPanel {

    private JLabel varLabel;
    private JLabel outputLabel;

    private static JTextArea varArea;
    private static JTextArea outputArea;

    private static String varSt = "Variables: ";
    private static String outputSt = "Output: ";

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(174, 700);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(new Color(226,222,234));
        Font f = new Font("Calibri (Body)", Font.PLAIN, 14);
        g.setFont(f);
    }

    public DisplayOutput() {

        setLayout(null);

        varLabel = new JLabel("                 Variable");
        varLabel.setToolTipText("Display the value of each variable.");
        this.add(varLabel);
        varLabel.setBounds(0, 0, 174, 30);

        varArea = new JTextArea("Variables: ");
        varArea.setEditable(false);
        varArea.setBackground(new Color(247,239,246));
        this.add(varArea);
        varArea.setBounds(0, 30, 174, 300);

        outputLabel = new JLabel("                  Output");
        outputLabel.setToolTipText("Display the value of output.");
        this.add(outputLabel);
        outputLabel.setBounds(0, 330, 174, 30);

        outputArea = new JTextArea("Output: ");
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(247,239,246));
        this.add(outputArea);
        outputArea.setBounds(0, 360, 174, 280);
    }

    public static void showVar(String st) {
        varSt = varSt + "\n" + st;
        varArea.setText(varSt);
    }

    public static void showOutput(String st) {
        outputSt = outputSt + "\n" + st;
        outputArea.setText(outputSt);
    }

    public static void reset() {
        outputArea.setText(null);
        outputSt = "Output: ";
        outputArea.setText(outputSt);

        varArea.setText(null);
        varSt = "Variables: ";
        varArea.setText(varSt);
    }
}
