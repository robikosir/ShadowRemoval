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
        return (int) (Math.pow(x.getRed(),2) + Math.pow(y.getRed(),2));
    }
    public int getLengthGreen() {
        return (int) (Math.pow(x.getGreen(),2) + Math.pow(y.getGreen(),2));
    }
    public int getLengthBlue() {
        return (int) (Math.pow(x.getBlue(),2) + Math.pow(y.getBlue(),2));
    }
}
