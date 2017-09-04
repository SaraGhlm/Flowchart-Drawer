package flowcharter;

/**
 *
 * @author Sara
 */
public class Variable {

    private final String variant;
    private int value;

    public Variable(String variant, int value) {
        this.value = value;
        this.variant = variant;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getVariant() {
        return variant;
    }
}
