package Form;

import flowcharter.DrawPanel;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Sara
 */
public class Edit extends JDialog {

    private JLabel leftLabel;
    private JLabel topLabel;
    private JLabel widthLabel;
    private JLabel heightLabel;

    private JTextField leftTextField;
    private JTextField topTextField;

    private JSpinner widthSpinner;
    private JSpinner heightSpinner;

    private SpinnerNumberModel smWidth;
    private SpinnerNumberModel smHeight;

    private JButton okButton;
    private JButton cancelButton;

    private JToggleButton connectionButton;

    private final Container pane;

    public Edit(java.awt.Frame parent, boolean modal) {
        super(parent, "Edit", modal);

        pane = getContentPane();
        pane.setLayout(null);

        setComponent();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 400);

    }

    /**
     * Set all the components on the JDialog
     */
    private void setComponent() {
        leftLabel = new JLabel("Left");
        leftLabel.setToolTipText("Determine the distance from left side");
        pane.add(leftLabel);
        leftLabel.setBounds(40, 30, 50, 20);

        String left = String.valueOf(DrawPanel.addShape.getShape()[DrawPanel.tempIndex].getPoint()[0].getX() - 200);
        leftTextField = new JTextField(left, 60);
        pane.add(leftTextField);
        leftTextField.setBounds(110, 30, 100, 20);

        topLabel = new JLabel("Top");
        topLabel.setToolTipText("Determine the distance from top side");
        pane.add(topLabel);
        topLabel.setBounds(40, 70, 50, 20);

        String top = String.valueOf(DrawPanel.addShape.getShape()[DrawPanel.tempIndex].getPoint()[0].getY());
        topTextField = new JTextField(top, 60);
        pane.add(topTextField);
        topTextField.setBounds(110, 70, 100, 20);

        widthLabel = new JLabel("Width");
        widthLabel.setToolTipText("Determine the width of the Image");
        pane.add(widthLabel);
        widthLabel.setBounds(40, 110, 50, 20);

        Integer width = DrawPanel.addShape.getShape()[DrawPanel.tempIndex].getWidth();
        smWidth = new SpinnerNumberModel(width, new Integer(80), new Integer(200), new Integer(1));
        widthSpinner = new JSpinner(smWidth);
        pane.add(widthSpinner);
        widthSpinner.setBounds(110, 110, 100, 20);

        heightLabel = new JLabel("Height");
        heightLabel.setToolTipText("Determine the height of the Image");
        pane.add(heightLabel);
        heightLabel.setBounds(40, 150, 50, 20);

        Integer height = DrawPanel.addShape.getShape()[DrawPanel.tempIndex].getHeight();
        smHeight = new SpinnerNumberModel(height, new Integer(50), new Integer(100), new Integer(1));
        heightSpinner = new JSpinner(smHeight);
        pane.add(heightSpinner);
        heightSpinner.setBounds(110, 150, 100, 20);

        connectionButton = new JToggleButton("Edit Connection");
        pane.add(connectionButton);
        connectionButton.setBounds(40, 190, 150, 30);
        connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection = new Connection(null, true);
                connection.setLocationRelativeTo(null);
                connection.setVisible(true);
            }
        });

        okButton = new JButton("Ok");
        pane.add(okButton);
        okButton.setBounds(40, 250, 100, 30);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String xText = leftTextField.getText();
                String yText = topTextField.getText();

                int width = (int) widthSpinner.getValue();
                int height = (int) heightSpinner.getValue();

                int top = Integer.parseInt(yText);
                int left = Integer.parseInt(xText) + 200;

                int x = DrawPanel.addShape.getShape()[DrawPanel.tempIndex].getPoint()[0].getX();
                int y = DrawPanel.addShape.getShape()[DrawPanel.tempIndex].getPoint()[0].getY();

                if (x != left && y != top) {
                    left = left + (width / 2);
                    top = top + height / 2;

                    DrawPanel.addShape.getShape()[DrawPanel.tempIndex].coordinationByForm(left, top, width, height);
                }
                if (x != left && y == top) {
                    left = left + (width / 2);

                    DrawPanel.addShape.getShape()[DrawPanel.tempIndex].coordinationByForm(left, top, width, height);
                }

                if (x == left && y != top) {
                    top = top + height / 2;

                    DrawPanel.addShape.getShape()[DrawPanel.tempIndex].coordinationByForm(left, top, width, height);
                }

                if (x == left && y == top) {
                    left = left + (width / 2);
                    top = top + height / 2;

                    DrawPanel.addShape.getShape()[DrawPanel.tempIndex].coordinationByForm(left, top, width, height);
                }

                dispose();
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
}
