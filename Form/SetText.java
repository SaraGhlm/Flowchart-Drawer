package Form;

import flowcharter.DrawPanel;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Sara
 */
public class SetText extends JDialog {

    private JLabel textLabel;
    protected JLabel sizeLable;
    protected JLabel fontLable;
    protected JLabel styleLable;

    protected JTextField textTextField;

    protected JButton okButton;
    protected JButton cancelButton;

    protected JSpinner sizeSpinner;

    protected JComboBox fontComboBox;
    protected JComboBox styleComboBox;

    private SpinnerNumberModel smSize;

    protected final Container pane;

    public SetText(java.awt.Frame parent, boolean modal) {
        super(parent, "Set text", modal);

        pane = getContentPane();
        pane.setLayout(null);

        setComponent();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 400);
    }

    /**
     * Set all the components on the JDialog
     */
    protected void setComponent() {
        textLabel = new JLabel("Text");
        textLabel.setToolTipText("The text will display on the screen");
        pane.add(textLabel);
        textLabel.setBounds(40, 30, 50, 20);

        String message = DrawPanel.addShape.getShape()[DrawPanel.tempIndex].readText().getMessage();
        textTextField = new JTextField(message, 60);
        pane.add(textTextField);
        textTextField.setBounds(110, 30, 100, 20);

        sizeLable = new JLabel("Size");
        sizeLable.setToolTipText("Set size of text");
        pane.add(sizeLable);
        sizeLable.setBounds(40, 70, 50, 20);

        int size = DrawPanel.addShape.getShape()[DrawPanel.tempIndex].readText().getSize();
        smSize = new SpinnerNumberModel(size, 12, 22, 1);
        sizeSpinner = new JSpinner(smSize);
        pane.add(sizeSpinner);
        sizeSpinner.setBounds(110, 70, 100, 20);

        fontLable = new JLabel("Font");
        fontLable.setToolTipText("Set font of text");
        pane.add(fontLable);
        fontLable.setBounds(40, 110, 50, 20);

        String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox(font);
        pane.add(fontComboBox);
        fontComboBox.setBounds(110, 110, 130, 20);

        styleLable = new JLabel("Style");
        styleLable.setToolTipText("Set style of text");
        pane.add(styleLable);
        styleLable.setBounds(40, 150, 50, 20);

        String[] style = {"PLAIN", "ITALIC", "BOLD"};
        styleComboBox = new JComboBox(style);
        pane.add(styleComboBox);
        styleComboBox.setBounds(110, 150, 100, 20);

        okButton = new JButton("Ok");
        pane.add(okButton);
        okButton.setBounds(40, 250, 100, 30);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton = new JButton("Cancel");
        pane.add(cancelButton);
        cancelButton.setBounds(150, 250, 100, 30);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
    }

    protected void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String Text = textTextField.getText();
        String font = (String) fontComboBox.getSelectedItem();
        String style = (String) styleComboBox.getSelectedItem();
        int size = (int) sizeSpinner.getValue();

        DrawPanel.addShape.getShape()[DrawPanel.tempIndex].writeText(Text, font, style, size);
        this.dispose();
    }
}
