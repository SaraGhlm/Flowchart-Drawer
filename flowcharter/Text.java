package flowcharter;

/**
 *
 * @author Sara
 */
public class Text {

    private String message;
    private String font;
    private String style;
    private int size;

    public Text(String message, String font, String style, int size) {
        this.font = font;
        this.size = size;
        this.style = style;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getFont() {
        return font;
    }

    public String getStyle() {
        return style;
    }

    public int getSize() {
        return size;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
