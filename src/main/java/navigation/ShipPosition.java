package navigation;

public class ShipPosition extends Position {

    public ShipPosition(int x, int y, int degrees) {
        super(x, y, degrees);
    }

    public void determinePosition(String movement) {
        char direction = movement.charAt(0);
        int amountToMove = Integer.parseInt(movement.substring(1));

        switch(direction) {
            case 'L':
                rotationAngle = rotationAngle - amountToMove < 0
                        ? (rotationAngle - amountToMove) + 360
                        : rotationAngle - amountToMove;
                break;
            case 'R':
                rotationAngle = rotationAngle + amountToMove >= 360
                        ? (rotationAngle + amountToMove) - 360
                        : rotationAngle + amountToMove;
                break;
            case 'W':
                x -= amountToMove;
                break;
            case 'E':
                x += amountToMove;
                break;
            case 'S':
                y -= amountToMove;
                break;
            case 'N':
                y += amountToMove;
                break;
            case 'F':
                moveAlongRotation(amountToMove);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized direction: '" + direction + "'.");
        }
    }

    private void moveAlongRotation(int amountToMove) {
        if(rotationAngle < 90 && rotationAngle >= 0) {
            y += amountToMove;
        } else if(rotationAngle < 180 && rotationAngle >= 90) {
            x += amountToMove;
        } else if(rotationAngle < 270 && rotationAngle >= 180) {
            y -= amountToMove;
        } else if(rotationAngle < 360 && rotationAngle >= 270) {
            x -= amountToMove;
        }
    }
}
