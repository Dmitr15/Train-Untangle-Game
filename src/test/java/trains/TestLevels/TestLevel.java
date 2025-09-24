package trains.TestLevels;

import Game.Direction;
import Game.Levels.GameLevel;
//import Game.Trains.Abstract_Train;
import Game.Trains.Train;

import java.awt.geom.Point2D;
import java.util.List;

public class TestLevel extends GameLevel {
    private static final int FIELD_HEIGHT = 100;
    private static final int FIELD_WIDTH = 100;

    @Override
    protected int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    protected int fieldWidth() {
        return FIELD_WIDTH;
    }

    @Override
    protected void addTrains() {
        Train train_1 = field.createTrain(new Point2D.Double(20, 10), Direction.UPRIGHT);
        //Train train_2 = field.createTrain(new Point2D.Double(20, 30), Direction.RIGHT);
    }

    @Override
    protected void addTrain(Point2D position, Direction direction) {

    }



    @Override
    protected void addTrainPaths() {
        List<Point2D> first_path = setUpFirstPoints();
        field.createAPath(first_path);

        List<Point2D> second_path =setUpSecondPoints();
        field.createAPath(second_path);
    }

    private static List<Point2D> setUpFirstPoints() {
        Point2D first_path_1 = new Point2D.Double(20, 30);
        Point2D first_path_2 = new Point2D.Double(30, 30);
        Point2D first_path_3 = new Point2D.Double(40, 30);

        return List.of(first_path_1, first_path_2, first_path_3);
    }

    private static List<Point2D> setUpSecondPoints() {
        Point2D second_path_1 = new Point2D.Double(20, 10);
        Point2D second_path_2 = new Point2D.Double(20, 20);
        Point2D second_path_3 = new Point2D.Double(20, 30);
        Point2D second_path_4 = new Point2D.Double(20, 40);

        return List.of(second_path_1, second_path_2, second_path_3, second_path_4);
    }

}