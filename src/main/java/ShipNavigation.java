import navigation.Position;
import navigation.ShipPosition;

import java.nio.file.Path;

public class ShipNavigation extends InputReader {

    public ShipNavigation(String input) {
        super(input);
    }

    public ShipNavigation(Path input) {
        super(input);
    }

    void turnWaypoint(Position waypoint, int degrees) {
        int newX = cos(waypoint.x(), degrees) - sin(waypoint.y(), degrees);
        int newY = cos(waypoint.y(), degrees) + sin(waypoint.x(), degrees);
        waypoint.setX(newX);
        waypoint.setY(newY);
    }

    private int cos(int coordinate, int deg) {
        return coordinate * (int)Math.cos(Math.toRadians(deg));
    }
    private int sin(int coordinate, int deg) {
        return coordinate * (int)Math.sin(Math.toRadians(deg));
    }

    public int manhattanDistanceWithWaypoint() {
/*
    Action N means to move the waypoint north by the given value.
    Action S means to move the waypoint south by the given value.
    Action E means to move the waypoint east by the given value.
    Action W means to move the waypoint west by the given value.
    Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
    Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
    Action F means to move forward to the waypoint a number of times equal to the given value.
 */
        Position waypoint = new Position(10, 1, 90);
        Position ship = new Position(0, 0, 90);
        for(String movement : data()) {
            char direction = movement.charAt(0);
            int amount = Integer.parseInt(movement.substring(1));

            switch(direction) {
                case 'L':
                    turnWaypoint(waypoint, amount);
                    break;
                case 'R':
                    turnWaypoint(waypoint, -1 * amount);
                    break;
                case 'W':
                    waypoint.setX(waypoint.x() - amount);
                    break;
                case 'E':
                    waypoint.setX(waypoint.x() + amount);
                    break;
                case 'S':
                    waypoint.setY(waypoint.y() - amount);
                    break;
                case 'N':
                    waypoint.setY(waypoint.y() + amount);
                    break;
                case 'F':
                    ship.setX(ship.x() + (amount * waypoint.x()));
                    ship.setY(ship.y() + (amount * waypoint.y()));
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized direction: '" + direction + "'.");
            }
        }

        return ship.totalDistance();
    }

    public int manhattanDistance() {
        /*

    Action N means to move north by the given value.
    Action S means to move south by the given value.
    Action E means to move east by the given value.
    Action W means to move west by the given value.
    Action L means to turn left the given number of degrees.
    Action R means to turn right the given number of degrees.
    Action F means to move forward by the given value in the direction the ship is currently facing.

        Ship starts pointing EAST (90 degree angle)
        Things to track:
        1. Direction ship is facing (determined by degrees):
            0/360 -> North
            90 -> East
            180 -> South
            270 -> West
        2. Direction ship moves in (does NOT take into account what direction the ship is facing)
         */
        // Start
        ShipPosition shipPosition = new ShipPosition(0, 0, 90);

        for(String movementLine : data()) {
            shipPosition.determinePosition(movementLine);
        }

        return shipPosition.totalDistance();
    }

}
