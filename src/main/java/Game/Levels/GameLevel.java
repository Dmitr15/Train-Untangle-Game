package Game.Levels;

import Game.Direction;
import Game.Field;
import java.awt.geom.Point2D;

public abstract class GameLevel {

    protected Field field;

    public Field createField(){
        this.field = new Field(fieldHeight(), fieldWidth());
        addTrainPaths();
        addTrains();
        return this.field;
    }

    public Field getField() {
        return field;
    }
    protected abstract int fieldHeight();
    protected abstract int fieldWidth();
    protected abstract void addTrains();
    protected abstract void addTrain(Point2D position, Direction direction);
    protected abstract void addTrainPaths();
}
