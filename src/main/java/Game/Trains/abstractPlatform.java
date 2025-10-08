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

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void deactivate(){
        this.isActive=false;
    }

    public abstract void moveAlongPath(Field field);

    public abstract void resetMovementHistory();

    public abstract void resetNum();

    public abstract List<Point2D> getMovementHistory();

    protected void setPaths(List<Point2D> paths){
        this.paths = paths;
    }

    protected abstract Point2D findNextPoint();

    public abstract void moveAlong(Field field, Direction dir);

    protected abstract void moveAlong(Field field);

    protected abstract Point2D calculateNextPosition();

}
