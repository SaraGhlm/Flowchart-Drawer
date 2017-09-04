package Form;

import Shapes.Rectangle;
import Shapes.Parallelogram;
import Shapes.Output;
import Shapes.Oval;
import flowcharter.DrawPanel;
import flowcharter.ShapeEnum;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
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
public class Property extends JDialog {

    private JLabel messageLabel;
    private JLabel leftLabel;
    private JLabel topLabel;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel sizeLable;
    private JLabel fontLable;
    private JLabel styleLable;

    protected JTextField messageTextField;
    private JTextField leftTextField;
    private JTextField topTextField;

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JSpinner sizeSpinner;

    private JComboBox fontComboBox;
    private JComboBox styleComboBox;

    private SpinnerNumberModel smWidth;
    private SpinnerNumberModel smHeight;
    private SpinnerNumberModel smSize;

    protected JButton okButton;
    protected JButton cancelButton;

    protected final Container pane;
    
    protected int size;
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected String message;
    protected String font;
    protected String style;

    public Property(java.awt.Frame parent, boolean modal) {
        super(parent, "Properties", modal);

        pane = getContentPane();
        pane.setLayout(null);

        setComponent();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(550, 300);

    }

    /**
     * Set all the components on the JDialog
     */
    protected void setComponent() {
        messageLabel = new JLabel("Text");
        messageLabel.setToolTipText("The text will display on the screen");
        pane.add(messageLabel);
        messageLabel.setBounds(40, 30, 50, 20);

        messageTextField = new JTextField(60);
        pane.add(messageTextField);
        messageTextField.setBounds(110, 30, 100, 20);

        fontLable = new JLabel("Font");
        fontLable.setToolTipText("Set font of text");
        pane.add(fontLable);
        fontLable.setBounds(280, 30, 50, 20);

        String[] fontSt = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox(fontSt);
        pane.add(fontComboBox);
        fontComboBox.setBounds(350, 30, 130, 20);

        styleLable = new JLabel("Style");
        styleLable.setToolTipText("Set style of text");
        pane.add(styleLable);
        styleLable.setBounds(40, 70, 50, 20);

        String[] styleSt = {"PLAIN", "ITALIC", "BOLD"};
        styleComboBox = new JComboBox(styleSt);
        pane.add(styleComboBox);
        styleComboBox.setBounds(110, 70, 100, 20);

        sizeLable = new JLabel("Size");
        sizeLable.setToolTipText("Set size of text");
        pane.add(sizeLable);
        sizeLable.setBounds(280, 70, 50, 20);

        smSize = new SpinnerNumberModel(new Integer(12), new Integer(12), new Integer(22), new Integer(1));
        sizeSpinner = new JSpinner(smSize);
        pane.add(sizeSpinner);
        sizeSpinner.setBounds(350, 70, 100, 20);

        leftLabel = new JLabel("Left");
        leftLabel.setToolTipText("Determine the distance from left side");
        pane.add(leftLabel);
        leftLabel.setBounds(40, 110, 50, 20);

        leftTextField = new JTextField(60);
        pane.add(leftTextField);
        leftTextField.setBounds(110, 110, 100, 20);

        topLabel = new JLabel("Top");
        topLabel.setToolTipText("Determine the distance from top side");
        pane.add(topLabel);
        topLabel.setBounds(280, 110, 50, 20);

        topTextField = new JTextField(60);
        pane.add(topTextField);
        topTextField.setBounds(350, 110, 100, 20);

        widthLabel = new JLabel("Width");
        widthLabel.setToolTipText("Determine the width of the Image");
        pane.add(widthLabel);
        widthLabel.setBounds(40, 150, 50, 20);

        smWidth = new SpinnerNumberModel(new Integer(80), new Integer(80), new Integer(200), new Integer(1));
        widthSpinner = new JSpinner(smWidth);
        pane.add(widthSpinner);
        widthSpinner.setBounds(110, 150, 100, 20);

        heightLabel = new JLabel("Height");
        heightLabel.setToolTipText("Determine the height of the Image");
        pane.add(heightLabel);
        heightLabel.setBounds(280, 150, 50, 20);

        smHeight = new SpinnerNumberModel(new Integer(50), new Integer(50), new Integer(100), new Integer(1));
        heightSpinner = new JSpinner(smHeight);
        pane.add(heightSpinner);
        heightSpinner.setBounds(350, 150, 100, 20);

        okButton = new JButton("Ok");
        pane.add(okButton);
        okButton.setBounds(100, 200, 100, 30);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton = new JButton("Cancel");
        pane.add(cancelButton);
        cancelButton.setBounds(300, 200, 100, 30);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
    }
    
    /**
     *
     * @param evt
     */
    protected void okButtonActionPerformed(ActionEvent evt) {
        message = messageTextField.getText();
        font = (String) fontComboBox.getSelectedItem();
        style = (String) styleComboBox.getSelectedItem();
        String xText = leftTextField.getText();
        String yText = topTextField.getText();

        size = (int) sizeSpinner.getValue();
        width = (int) widthSpinner.getValue();
        height = (int) heightSpinner.getValue();
        y = Integer.parseInt(yText);
        x = Integer.parseInt(xText);

        x = x + (width / 2) + 200;
        y = y + height / 2;

        if (DrawPanel.clickedShape == ShapeEnum.Rectangle) {
            Rectangle rectangle = new Rectangle(x, y, width, height);
            rectangle.writeText(message, font, style, size);
            rectangle.createCoordination(x, y, width, height);
            DrawPanel.addShape.add(rectangle);
        }

        if (DrawPanel.clickedShape == ShapeEnum.Output) {
            Output output = new Output(x, y, width, height);
            output.writeText(message, font, style, size);
            output.coordinationByForm(x, y, width, height);
            DrawPanel.addShape.add(output);
        }
        if (DrawPanel.clickedShape == ShapeEnum.Oval) {
            Oval oval = new Oval(x, y, width, height);
            oval.writeText(message, font, style, size);
            oval.createCoordination(x, y, width, height);
            DrawPanel.addShape.add(oval);
        }
        if (DrawPanel.clickedShape == ShapeEnum.Parallelogram) {
            Parallelogram parallelogram = new Parallelogram(x, y, width, height);
            parallelogram.writeText(message, font, style, size);
            parallelogram.createCoordination(x, y, width, height);
            DrawPanel.addShape.add(parallelogram);
        }
        dispose();
    }
}