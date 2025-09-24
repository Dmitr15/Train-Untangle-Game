package Game;

public enum Direction {
    DOWN, RIGHT, LEFT, RIGHTUP, LEFTUP, DOWNRIGHT, DOWNLEFT, BACKWARDDOWNRIGHT, BACKWARDDOWNLEFT, RIGHTBACKWARDDOWN, RIGHTBACKWARDUP, LEFTBACKWARDUP, LEFTBACKWARDDOWN, UP, UPRIGHT, UPLEFT, UPBACKWARDRIGHT, UPBACKWARDLEFT, LEFTDOWN, RIGHTDOWN;
    private Direction opposite;

    static  {
        UP.opposite = DOWN;
        DOWN.opposite = UP;

        RIGHT.opposite = LEFT;
        LEFT.opposite = RIGHT;

        RIGHTUP.opposite = DOWNLEFT;
        DOWNLEFT.opposite = RIGHTUP;

        DOWNRIGHT.opposite = LEFTUP;
        LEFTUP.opposite = DOWNRIGHT;

        UPRIGHT.opposite = LEFTDOWN;
        LEFTDOWN.opposite = UPRIGHT;

        UPLEFT.opposite = RIGHTDOWN;
        RIGHTDOWN.opposite = UPLEFT;

        LEFTBACKWARDUP.opposite = RIGHTBACKWARDUP;
        RIGHTBACKWARDUP.opposite = LEFTBACKWARDUP;

        LEFTBACKWARDDOWN.opposite = RIGHTBACKWARDDOWN;
        RIGHTBACKWARDDOWN.opposite = LEFTBACKWARDDOWN;

        BACKWARDDOWNRIGHT.opposite = UPBACKWARDRIGHT;
        UPBACKWARDRIGHT.opposite = BACKWARDDOWNRIGHT;

        BACKWARDDOWNLEFT.opposite = UPBACKWARDLEFT;
        UPBACKWARDLEFT.opposite = BACKWARDDOWNLEFT;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }
}