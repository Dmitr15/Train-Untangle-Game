package Game.Trains;

import Game.Direction;
import Game.Field;
import Game.Train_path;
import Game.event.TrainActionEvent;
import Game.event.TrainActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Train {
    protected Direction direction;
    protected Point2D position;
    protected boolean isActive;

    private int numOfTurns = 0;

    private List<Point2D> movementHistory = new ArrayList<>();

    public Train(Point2D position, Direction direction){
        this.position = position;
        this.direction = direction;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    private ArrayList<TrainActionListener> trainListListener = new ArrayList<>();

    public void addTrainActionListener(TrainActionListener listener) {
        trainListListener.add(listener);
    }

//    public void removeTrainActionListener(TrainActionListener listener) {
//        trainListListener.remove(listener);
//    }

    protected void fireTrainIsTeleported() {
        for (TrainActionListener listener: trainListListener) {
            TrainActionEvent event = new TrainActionEvent(listener);
            event.setTrain(this);
            listener.trainIsTeleported(event);
        }
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

    public Point2D findNextPoint(List<Point2D> pathPoints){
        if (this.direction == Direction.DOWN) {
            for (Point2D point: pathPoints) {
                if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                    return point;
                }
            }
        } else if (this.direction == Direction.RIGHT) {
            for (Point2D point: pathPoints) {
                if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                    return point;
                }
            }
        } else if (this.direction == Direction.LEFT) {
            for (Point2D point: pathPoints) {
                if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                    return point;
                }
            }
        } else if (this.direction == Direction.UP) {
            for (Point2D point: pathPoints) {
                if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                    return point;
                }
            }
        } else if (this.direction == Direction.RIGHTDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.RIGHTUP) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTUP) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.DOWNLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.DOWNRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTBACKWARDDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTBACKWARDUP) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.RIGHTBACKWARDDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.RIGHTBACKWARDUP) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.BACKWARDDOWNRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.BACKWARDDOWNLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPBACKWARDLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPBACKWARDRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && pathPoints.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && pathPoints.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: pathPoints) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        }
        return null;
    }

    public Point2D calculateNextPosition(List<Train_path> paths) {
        List<Point2D> pathPoints = findPathForTrain(paths);
        return findNextPoint(pathPoints);
    }

    public void resetNum(){
        if (this.numOfTurns != 0) {
            this.numOfTurns = 0;
        }
    }

    public void moveAlongPath(List<Train_path> paths, Field field) {
        Point2D nextPosition = calculateNextPosition(paths);

        for (Train_path path : paths) {
            if (path.getPositionOfTrains(this).contains(this.position)) {
                return; // Столкновение - не двигаемся
            }
        }

        Platform platformAtNext = getPlatformAt(this.position, field);

        if (nextPosition == null) {
            this.deactivate();
            fireTrainIsTeleported();
            if (platformAtNext != null) {
                platformAtNext.deactivate();
            }
            return;
        }

        // Проверка столкновения с платформой
        if (platformAtNext != null && platformAtNext.isActive()) {
            Point2D platformNextPos = calculateNextPositionForPlatform(platformAtNext.getPosition());
            if (field.isAvailablePosition(platformNextPos)) {
                platformAtNext.setPosition(platformNextPos);
            } else {
                platformAtNext.deactivate();
            }
            if (getPlatformAt(nextPosition, field) == null) {
                platformAtNext.deactivate();
            }
        }


        // Проверяем, находится ли позиция на пути
        boolean validPosition = false;
        for (Train_path path : paths) {
            if (path.get_route().contains(nextPosition)) {
                validPosition = true;
                break;
            }
        }

        // Проверка возможности перемещения
        if (validPosition) {
            movementHistory.add(new Point2D.Double(position.getX(), position.getY()));
            position = nextPosition;
        } else {
            deactivate();
            fireTrainIsTeleported();
        }
    }

    private Platform getPlatformAt(Point2D position, Field field) {
        for (Platform platform : field.getPlatforms()) {
            if (platform.isActive() && platform.getPosition().equals(position)) {
                return platform;
            }
        }
        return null;
    }

    private Point2D calculateNextPositionForPlatform(Point2D platformPosition) {
        double dx = 0, dy = 0;
        switch (direction) {
            case UP: dy = -10; break;
            case DOWN: dy = 10; break;
            case LEFT: dx = -10; break;
            case RIGHT: dx = 10; break;
            case RIGHTUP:  dy = -10; break;
            case LEFTUP:  dy = -10; break;
            case DOWNRIGHT: dx = 10;  break;
            case DOWNLEFT: dx = -10; break;
            case UPRIGHT: dx = 10;  break; // Аналогично RIGHTUP
            case UPLEFT: dx = -10;  break; // Аналогично LEFTUP
            case LEFTDOWN:  dy = 10; break; // Аналогично DOWNLEFT
            case RIGHTDOWN:  dy = 10; break; // Аналогично DOWNRIGHT

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
        return new Point2D.Double(platformPosition.getX() + dx, platformPosition.getY() + dy );
    }

    public List<Point2D> getMovementHistory() {
        return movementHistory;
    }

    public void resetMovementHistory() {
        movementHistory.clear();
    }

    private List<Point2D> findPathForTrain(List<Train_path> paths) {
        List<Point2D> allPoints = new ArrayList<>();

        for (Train_path path : paths) {
            if (path.get_route().contains(this.position)) {
                allPoints.addAll(path.get_route());
            }
        }
        return allPoints;
    }
}