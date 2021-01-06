package navigation;

public class Position {

    protected int x;

    protected int y;

    protected int rotationAngle;

    public Position(int x, int y, int degrees) {
        this.x = x;
        this.y = y;
        rotationAngle = degrees;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public Position setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
        return this;
    }

    public int rotationAngle() {
        return rotationAngle;
    }

    public int totalDistance() {
        return Math.abs(x) + Math.abs(y);
    }

}
