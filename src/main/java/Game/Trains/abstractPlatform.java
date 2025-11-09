package Game.Trains;

import Game.Direction;
import Game.Field;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class abstractPlatform {
    protected Direction direction;
    protected Point2D position;
    protected boolean isActive;
    protected List<Point2D> paths = new ArrayList<>();
    protected Field field;

    public abstractPlatform(Point2D position, Direction direction, boolean isActive, List<Point2D> paths, Field field) {
        this.position = position;
        this.direction = direction;
        this.isActive = isActive;
        this.paths = paths;
        this.field = field;
    }

    public boolean isActive() {
        return isActive;
    }

    public Direction getDirection() {
        return direction;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Field getField() {
        return field;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void deactivate(){
        this.isActive=false;
    }

    public abstract Point2D findNextPoint();

    public  void moveAlong(){
        if (!isActive) return;

        Point2D nextPos = findNextPoint();
        if (nextPos == null) {
            if (!field.ifPositionFree(position, this)) {
                handleCollision();
            }
            else {
                handleInvalidMovement();
                return;
            }
        }

        if (field.ifPositionFree(position, this)) {
            performMovement(nextPos);
        }
    }

    protected abstract void performMovement(Point2D nextPosition);
    protected abstract void handleInvalidMovement();
    protected abstract void handleCollision();
}
