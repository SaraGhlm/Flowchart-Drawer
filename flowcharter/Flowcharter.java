package flowcharter;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Sara
 */
public class Flowcharter extends JFrame {

    /**
     * @param args the command line arguments
     */
    private final DrawPanel myPanel;
    private final DisplayOutput output;
    
    public Flowcharter() {

        super("Flowcharter");
        myPanel = new DrawPanel();
        output = new DisplayOutput();

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(myPanel);
        c.add(output);

        setSize(1100, 700);
        show();
    }

    public static void main(String[] args) {

        Flowcharter flowchart = new Flowcharter();

        flowchart.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
    }

}
