package Game.Trains;

import Game.Field;
import java.awt.geom.Point2D;
import java.util.List;

public class UniquePlatform extends Platform {
    private final int maxSteps;
    private int currentSteps;
    private boolean isMoveable;
    private Train pushingTrain;

    public void setMoveable(boolean moveable) {
        isMoveable = moveable;
    }

    public void setPushingTrain(Train pushingTrain) {
        this.pushingTrain = pushingTrain;
    }

    public void setCurrentSteps(int currentSteps) {
        this.currentSteps = currentSteps;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public boolean isMoveable() {
        return isMoveable;
    }

    public Train getPushingTrain() {
        return pushingTrain;
    }

    public UniquePlatform(Point2D position, List<Point2D> paths, Field field, int steps) {
        super(position, paths, field);
        this.maxSteps = steps;
        this.currentSteps = 0;
        this.pushingTrain = null;
        this.isMoveable = true;
    }

    @Override
    protected void performMovement(Point2D nextPosition) {
        if (currentSteps < maxSteps) {
              currentSteps++;

            // Если это был первый шаг, запоминаем поезд и его исходную позицию
            if (currentSteps == 1) {
                for (abstractPlatform train : getField().getTrains()) {
                    boolean isQ = train.getPosition().equals(position);
                    if (train instanceof Train && isQ) {
                        pushingTrain = (Train) train;
                        break;
                    }
                }
            }

            super.performMovement(nextPosition);

        } else {
            this.isMoveable = false;
            // Достигнут лимит шагов - вызываем столкновение для поезда
            if (pushingTrain != null) {
                pushingTrain.handleCollision();
                pushingTrain = null;
                currentSteps = 0;
                this.setDirection(null);
            }
        }
    }

    @Override
    protected void handleInvalidMovement() {
        // При невалидном движении сбрасываем счетчик
        currentSteps = 0;
        pushingTrain = null;
        this.isMoveable = false;
        super.handleInvalidMovement();
    }

    @Override
    protected void handleCollision() {
        // При столкновении сбрасываем счетчик
        currentSteps = 0;
        pushingTrain = null;
        this.isMoveable = false;
        super.handleCollision();
    }

    public int getSteps() {
        return maxSteps;
    }

    public int getCurrentSteps() {
        return currentSteps;
    }
}