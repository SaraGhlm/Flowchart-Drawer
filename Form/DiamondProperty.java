package Form;

import Shapes.Diamond;
import flowcharter.DrawPanel;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

/**
 *
 * @author Sara
 */
public class DiamondProperty extends Property {

    private JLabel leftLabel;
    private JLabel rightLabel;

    private JSpinner leftSpinner;
    private JSpinner rightSpinner;

    private SpinnerModel smLeft;
    private SpinnerModel smRight;

    private JLabel downLabel;
    private JLabel left2Label;
    private JLabel right2Label;

    private JTextField left2TextField;
    private JTextField right2TextField;
    private JTextField downTextField;

    public DiamondProperty(Frame parent, boolean modal) {
        super(parent, modal);
        setSize(550, 430);
    }

    @Override
    protected void setComponent() {
        super.setComponent();

        messageTextField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                String msg = messageTextField.getText();
                msg = msg.replaceAll("\\s+","");
                if (msg.matches("((\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1}(>|>=|=|<=|<){1}(\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1})")) {
                    leftLabel.setEnabled(true);
                    rightLabel.setEnabled(true);
                    leftSpinner.setEnabled(true);
                    rightSpinner.setEnabled(true);

                    left2Label.setEnabled(false);
                    left2TextField.setEnabled(false);
                    right2Label.setEnabled(false);
                    right2TextField.setEnabled(false);
                    downLabel.setEnabled(false);
                    downTextField.setEnabled(false);
                } else if (msg.matches("[a-zA-Z]")) {
                    leftLabel.setEnabled(false);
                    rightLabel.setEnabled(false);
                    leftSpinner.setEnabled(false);
                    rightSpinner.setEnabled(false);

                    left2Label.setEnabled(true);
                    left2TextField.setEnabled(true);
                    right2Label.setEnabled(true);
                    right2TextField.setEnabled(true);
                    downLabel.setEnabled(true);
                    downTextField.setEnabled(true);
                } else {
                    leftLabel.setEnabled(false);
                    rightLabel.setEnabled(false);
                    leftSpinner.setEnabled(false);
                    rightSpinner.setEnabled(false);

                    left2Label.setEnabled(false);
                    left2TextField.setEnabled(false);
                    right2Label.setEnabled(false);
                    right2TextField.setEnabled(false);
                    downLabel.setEnabled(false);
                    downTextField.setEnabled(false);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String msg = messageTextField.getText();
                msg = msg.replaceAll("\\s+","");
                if (msg.matches("((\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1}(>|>=|=|<=|<){1}(\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1})")) {
                    leftLabel.setEnabled(true);
                    rightLabel.setEnabled(true);
                    leftSpinner.setEnabled(true);
                    rightSpinner.setEnabled(true);

                    left2Label.setEnabled(false);
                    left2TextField.setEnabled(false);
                    right2Label.setEnabled(false);
                    right2TextField.setEnabled(false);
                    downLabel.setEnabled(false);
                    downTextField.setEnabled(false);
                } else if (msg.matches("[a-zA-Z]")) {
                    leftLabel.setEnabled(false);
                    rightLabel.setEnabled(false);
                    leftSpinner.setEnabled(false);
                    rightSpinner.setEnabled(false);

                    left2Label.setEnabled(true);
                    left2TextField.setEnabled(true);
                    right2Label.setEnabled(true);
                    right2TextField.setEnabled(true);
                    downLabel.setEnabled(true);
                    downTextField.setEnabled(true);
                } else {
                    leftLabel.setEnabled(false);
                    rightLabel.setEnabled(false);
                    leftSpinner.setEnabled(false);
                    rightSpinner.setEnabled(false);

                    left2Label.setEnabled(false);
                    left2TextField.setEnabled(false);
                    right2Label.setEnabled(false);
                    right2TextField.setEnabled(false);
                    downLabel.setEnabled(false);
                    downTextField.setEnabled(false);
                }
            } 
        });

        leftLabel = new JLabel("Left tag");
        leftLabel.setToolTipText("Tag of the left output when the if statement is like x>y");
        leftLabel.setEnabled(false);
        pane.add(leftLabel);
        leftLabel.setBounds(40, 190, 50, 20);

        String[] yesNo = {"Yes", "No"};

        smLeft = new SpinnerListModel(yesNo);
        leftSpinner = new JSpinner(smLeft);
        leftSpinner.setEnabled(false);
        pane.add(leftSpinner);
        leftSpinner.setBounds(110, 190, 100, 20);

        rightLabel = new JLabel("Right tag");
        rightLabel.setToolTipText("Tag of the right output when the if statement is like x>y");
        rightLabel.setEnabled(false);
        pane.add(rightLabel);
        rightLabel.setBounds(280, 190, 50, 20);

        smRight = new SpinnerListModel(yesNo);
        rightSpinner = new JSpinner(smRight);
        rightSpinner.setEnabled(false);
        pane.add(rightSpinner);
        rightSpinner.setBounds(350, 190, 100, 20);

        left2Label = new JLabel("Left tag");
        left2Label.setToolTipText("Tag of the left output when the if statement is like x");
        left2Label.setEnabled(false);
        pane.add(left2Label);
        left2Label.setBounds(40, 230, 50, 20);

        left2TextField = new JTextField(60);
        left2TextField.setEnabled(false);
        pane.add(left2TextField);
        left2TextField.setBounds(110, 230, 100, 20);

        right2Label = new JLabel("Right tag");
        right2Label.setToolTipText("Tag of the right output when the if statement is like x");
        right2Label.setEnabled(false);
        pane.add(right2Label);
        right2Label.setBounds(280, 230, 50, 20);

        right2TextField = new JTextField(60);
        right2TextField.setEnabled(false);
        pane.add(right2TextField);
        right2TextField.setBounds(350, 230, 100, 20);

        downLabel = new JLabel("Down tag");
        downLabel.setToolTipText("Tag of the down output when the if statement is like x");
        downLabel.setEnabled(false);
        pane.add(downLabel);
        downLabel.setBounds(40, 270, 60, 20);

        downTextField = new JTextField(60);
        downTextField.setEnabled(false);
        pane.add(downTextField);
        downTextField.setBounds(110, 270, 100, 20);

        okButton.setBounds(100, 320, 100, 30);
        cancelButton.setBounds(300, 320, 100, 30);
    }

    @Override
    protected void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        super.okButtonActionPerformed(evt);

        if(leftSpinner.isEnabled()){
            String leftTag = (String) leftSpinner.getValue();
            String rightTag = (String) rightSpinner.getValue();

            Diamond diamond = new Diamond(x, y, width, height);
            diamond.writeText(message, font, style, size);
            diamond.createCoordination(x, y, width, height);
            diamond.setLeftTag(leftTag);
            diamond.setRightTag(rightTag);
            diamond.setDownTag(null);
            DrawPanel.addShape.add(diamond);
        } else if(left2TextField.isEnabled()){
            String leftTag = left2TextField.getText();
            String rightTag = right2TextField.getText();
            String downTag = downTextField.getText();
            
            Diamond diamond = new Diamond(x, y, width, height);
            diamond.writeText(message, font, style, size);
            diamond.createCoordination(x, y, width, height);
            diamond.setLeftTag(leftTag);
            diamond.setRightTag(rightTag);
            diamond.setDownTag(downTag);
            DrawPanel.addShape.add(diamond);
        } else {
            Diamond diamond = new Diamond(x, y, width, height);
            diamond.writeText(message, font, style, size);
            diamond.createCoordination(x, y, width, height);
            diamond.setLeftTag(null);
            diamond.setRightTag(null);
            diamond.setDownTag(null);
            DrawPanel.addShape.add(diamond);
        }

    }
}
