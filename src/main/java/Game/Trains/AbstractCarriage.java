package Game.Trains;

import Game.Direction;
import Game.Field;

import java.awt.geom.Point2D;
import java.util.List;

public abstract class AbstractCarriage extends abstractPlatform{
    protected boolean isMoveable;
    protected Train pushingTrain;
    protected Point2D previousPosition;

    @Override
    public Point2D findNextPoint() {
        double dx = 0, dy = 0;
        switch (this.direction) {
            case UP: dy = -10; break;
            case DOWN: dy = 10; break;
            case LEFT: dx = -10; break;
            case RIGHT: dx = 10; break;
            case RIGHTUP:  dy = -10; break;
            case LEFTUP:  dy = -10; break;
            case DOWNRIGHT: dx = 10;  break;
            case DOWNLEFT: dx = -10; break;
            case UPRIGHT: dx = 10;  break;
            case UPLEFT: dx = -10;  break;
            case LEFTDOWN:  dy = 10; break;
            case RIGHTDOWN:  dy = 10; break;

            // Сложные направления
            case BACKWARDDOWNRIGHT: dx = 10; dy = 10; break; // Вниз-вправо
            case BACKWARDDOWNLEFT: dx = -10; dy = 10; break; // Вниз-влево
            case RIGHTBACKWARDDOWN: dx = 10; dy = 10; break; // Вниз-вправо
            case RIGHTBACKWARDUP: dx = 10; dy = -10; break; // Вверх-вправо
            case LEFTBACKWARDUP: dx = -10; dy = -10; break; // Вверх-влево
            case LEFTBACKWARDDOWN: dx = -10; dy = 10; break; // Вниз-влево
            case UPBACKWARDRIGHT: dx = 10; dy = -10; break; // Вверх-вправо
            case UPBACKWARDLEFT: dx = -10; dy = -10; break; // Вверх-влево
        }
        return new Point2D.Double(this.position.getX() + dx, this.position.getY() + dy);
    }


    public AbstractCarriage(Point2D position, Direction direction, boolean isActive, List<Point2D> paths, Field field) {
        super(position, direction, isActive, paths, field);
    }

    @Override
    protected void performMovement(Point2D nextPosition) {
        this.previousPosition = this.position;
        this.position = nextPosition;
    }

    @Override
    protected void handleInvalidMovement() {
        deactivate();
        return;
    }

    @Override
    protected void handleCollision() {
        deactivate();
        return;
    }
}
