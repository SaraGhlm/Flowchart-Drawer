package Form;

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
public class DiamondSetText extends SetText{

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
    
    public DiamondSetText(Frame parent, boolean modal) {
        super(parent, modal);
        
        setSize(550, 350);
    }
    
    @Override
    protected void setComponent() {
        super.setComponent();
        
        fontLable.setBounds(280, 30, 50, 20);
        fontComboBox.setBounds(350, 30, 130, 20);
        
        styleLable.setBounds(40, 70, 50, 20);
        styleComboBox.setBounds(110, 70, 100, 20);
        
        sizeLable.setBounds(280, 70, 50, 20);
        sizeSpinner.setBounds(350, 70, 100, 20);
                
        textTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String msg = textTextField.getText();
                msg = msg.replaceAll("\\s+","");
                if (msg.matches("((\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1}(>|>=|=|<=|<){1}(\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1})")){
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
                String msg = textTextField.getText();
                msg = msg.replaceAll("\\s+","");
                if (msg.matches("((\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1}(>|>=|=|<=|<){1}(\\d+|[a-zA-Z]+|\\d+(\\+|-|\\*|/){1}\\d+|\\d+(\\+|-|\\*|/){1}[a-zA-Z]+|[a-zA-Z]+(\\+|-|\\*|/){1}\\d+|[a-zA-Z]+(\\+|-|\\*|/){1}[a-zA-Z]+){1})")){
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
        leftLabel.setBounds(40, 110, 50, 20);

        String[] yesNo = {"Yes", "No"};

        smLeft = new SpinnerListModel(yesNo);
        leftSpinner = new JSpinner(smLeft);
        leftSpinner.setEnabled(false);
        pane.add(leftSpinner);
        leftSpinner.setBounds(110, 110, 100, 20);

        rightLabel = new JLabel("Right tag");
        rightLabel.setToolTipText("Tag of the right output when the if statement is like x>y");
        rightLabel.setEnabled(false);
        pane.add(rightLabel);
        rightLabel.setBounds(280, 110, 50, 20);

        smRight = new SpinnerListModel(yesNo);
        rightSpinner = new JSpinner(smRight);
        rightSpinner.setEnabled(false);
        pane.add(rightSpinner);
        rightSpinner.setBounds(350, 110, 100, 20);
        
        left2Label = new JLabel("Left tag");
        left2Label.setToolTipText("Tag of the left output when the if statement is like x");
        left2Label.setEnabled(false);
        pane.add(left2Label);
        left2Label.setBounds(40, 150, 50, 20);

        left2TextField = new JTextField(60);
        left2TextField.setEnabled(false);
        pane.add(left2TextField);
        left2TextField.setBounds(110, 150, 100, 20);

        right2Label = new JLabel("Right tag");
        right2Label.setToolTipText("Tag of the right output when the if statement is like x");
        right2Label.setEnabled(false);
        pane.add(right2Label);
        right2Label.setBounds(280, 150, 50, 20);

        right2TextField = new JTextField(60);
        right2TextField.setEnabled(false);
        pane.add(right2TextField);
        right2TextField.setBounds(350, 150, 100, 20);

        downLabel = new JLabel("Down tag");
        downLabel.setToolTipText("Tag of the down output when the if statement is like x");
        downLabel.setEnabled(false);
        pane.add(downLabel);
        downLabel.setBounds(40, 190, 60, 20);

        downTextField = new JTextField(60);
        downTextField.setEnabled(false);
        pane.add(downTextField);
        downTextField.setBounds(110, 190, 100, 20);

        okButton.setBounds(100, 250, 100, 30);
        cancelButton.setBounds(300, 250, 100, 30);
    }
    
    @Override
    protected void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        super.okButtonActionPerformed(evt);
        
        if(leftSpinner.isEnabled()){
            String leftTag =(String) leftSpinner.getValue();
            String rightTag =(String) rightSpinner.getValue();
            
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setLeftTag(leftTag);
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setRightTag(rightTag);
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setDownTag(null);
        } else if(left2TextField.isEnabled()){
            String leftTag = left2TextField.getText();
            String rightTag =right2TextField.getText();
            String downTag =downTextField.getText();
            
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setLeftTag(leftTag);
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setRightTag(rightTag);
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setDownTag(downTag);
        } else{
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setLeftTag(null);
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setRightTag(null);
            DrawPanel.addShape.getShape()[DrawPanel.tempIndex].setDownTag(null);
        }  
    }
}
