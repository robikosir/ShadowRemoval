public class GradVector {
    private final GradColor x;
    private final GradColor y;

    public GradVector (GradColor x, GradColor y) {
        this.x = x;
        this.y = y;
    }

    public GradColor getX() {
        return x;
    }

    public GradColor getY() {
        return y;
    }

    public int getLengthRed() {
        return returnBetweenRGBValue((int) Math.sqrt(Math.pow(x.getRed(),2) + Math.pow(y.getRed(),2)));
    }
    public int getLengthGreen() {
        return returnBetweenRGBValue((int) Math.sqrt(Math.pow(x.getGreen(),2) + Math.pow(y.getGreen(),2)));
    }
    public int getLengthBlue() {
        return returnBetweenRGBValue((int) Math.sqrt(Math.pow(x.getBlue(),2) + Math.pow(y.getBlue(),2)));
    }

    private int returnBetweenRGBValue(int value) {
        if (value > 255) {
            return 255;
        } else if (value < 0) {
            return 0;
        }
        return value;
    }
}
