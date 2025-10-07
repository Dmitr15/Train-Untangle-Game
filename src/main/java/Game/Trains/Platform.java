package Game.Trains;

import Game.Direction;
import Game.Field;
import java.awt.geom.Point2D;
import java.util.List;

public class Platform extends abstractPlatform{
    private Point2D previousPosition;

    public Platform(Point2D position, List<Point2D> paths) {
        this.position = position;
        this.paths= paths;
        this.previousPosition = position;
        this.isActive = true;
    }

    public Point2D getPreviousPosition() {
        return previousPosition;
    }

    protected Point2D findNextPoint(Direction dir) {
        double dx = 0, dy = 0;
        switch (dir) {
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

    @Override
    protected Point2D findNextPoint() {
        return null;
    }

    @Override
    protected void moveAlong(Field field) {
    }

    public void moveAlong(Field field, Direction dir) {
        Point2D nextPos = findNextPoint(dir);
        if (field.ifPositionFree(nextPos)) {
            this.previousPosition = this.getPosition();
            this.setPosition(nextPos);
        }
        else {
            deactivate();
        }
    }

    @Override
    protected Point2D calculateNextPosition() {
        return null;
    }
}