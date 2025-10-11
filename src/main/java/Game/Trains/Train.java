package Game.Trains;

import Game.Direction;
import Game.Field;
import Game.event.TrainActionEvent;
import Game.event.TrainActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Train  extends abstractPlatform{
    private int numOfTurns = 0;
    private List<Point2D> movementHistory = new ArrayList<>();

    public Train(Point2D position, Direction direction, List<Point2D> paths, Field field){
        super(position, direction, true, paths, field);
    }

    private ArrayList<TrainActionListener> trainListListener = new ArrayList<>();

    public void addTrainActionListener(TrainActionListener listener) {
        trainListListener.add(listener);
    }

    protected void fireTrainIsTeleported() {
        for (TrainActionListener listener: trainListListener) {
            TrainActionEvent event = new TrainActionEvent(listener);
            event.setTrain(this);
            listener.trainIsTeleported(event);
        }
    }

    public Point2D findNextPoint(){
        if (this.direction == Direction.DOWN) {
            for (Point2D point: this.paths) {
                if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                    return point;
                }
            }
        } else if (this.direction == Direction.RIGHT) {
            for (Point2D point: this.paths) {
                if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                    return point;
                }
            }
        } else if (this.direction == Direction.LEFT) {
            for (Point2D point: this.paths) {
                if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                    return point;
                }
            }
        } else if (this.direction == Direction.UP) {
            for (Point2D point: this.paths) {
                if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                    return point;
                }
            }
        } else if (this.direction == Direction.RIGHTDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.RIGHTUP) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTUP) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.DOWNLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.DOWNRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTBACKWARDDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.LEFTBACKWARDUP) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.RIGHTBACKWARDDOWN) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.RIGHTBACKWARDUP) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.BACKWARDDOWNRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.BACKWARDDOWNLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()+10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPBACKWARDLEFT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()-10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()-10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        } else if (this.direction == Direction.UPBACKWARDRIGHT) {
            //при повороте
            if (this.numOfTurns == 0 && this.paths.contains(new Point2D.Double(this.position.getX()+10, this.position.getY()))) {
                this.numOfTurns++;
            } else if (this.numOfTurns == 1 && this.paths.contains(new Point2D.Double(this.position.getX(), this.position.getY()-10))) {
                this.numOfTurns++;
            }

            if (this.numOfTurns == 0) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()+10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 1) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY() && point.getX() == this.position.getX()+10) {
                        return point;
                    }
                }
            } else if (this.numOfTurns == 2) {
                for (Point2D point: this.paths) {
                    if (point.getY() == this.position.getY()-10 && point.getX() == this.position.getX()) {
                        return point;
                    }
                }
            }
        }
        return null;
    }


    public void resetNum(){
        if (this.numOfTurns != 0) {
            this.numOfTurns = 0;
        }
    }

    @Override
    public void moveAlong() {
        Point2D nextPosition = findNextPoint();
            if (!field.ifPositionFree(this.position, this)) {
                return; // Столкновение - не двигаемся
            }
        if (nextPosition == null) {
            this.deactivate();
            fireTrainIsTeleported();
            if (field.ifPlatformExists()) {
                if (field.isPlatformOnPosition(this.position)) {
                    field.deactivatePlatform();
                }
            }
            return;
        }
        // Проверка столкновения с платформой
        if (field.ifPlatformExists() && field.isPlatformOnPosition(this.position)) {
            field.movePlatform(this.direction);
        }

        // Проверяем, находится ли позиция на пути
        boolean validPosition = false;
            if (paths.contains(nextPosition)) {
                validPosition = true;
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

    public List<Point2D> getMovementHistory() {
        return movementHistory;
    }

    public void resetMovementHistory() {
        movementHistory.clear();
    }
}