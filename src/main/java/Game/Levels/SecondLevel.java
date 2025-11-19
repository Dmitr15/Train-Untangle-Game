package Game.Levels;

import Game.Direction;
import java.awt.geom.Point2D;
import java.util.List;

public class SecondLevel extends GameLevel{

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
        field.createTrain(new Point2D.Double(10, 20), Direction.RIGHT);
        field.createTrain(new Point2D.Double(30, 10), Direction.DOWNLEFT);
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


        field.createUniquePlatform(new Point2D.Double(40, 20), 2);
    }

    private static List<Point2D> setUpFirstPoints() {
        Point2D first_path_1 = new Point2D.Double(10, 20);
        Point2D first_path_2 = new Point2D.Double(20, 20);
        Point2D first_path_3 = new Point2D.Double(30, 20);
        Point2D first_path_4 = new Point2D.Double(40, 20);
        Point2D first_path_5 = new Point2D.Double(50, 20);
        Point2D second_path_5 = new Point2D.Double(60, 20);
        Point2D second_path_6 = new Point2D.Double(70, 20);

        return List.of(first_path_1, first_path_2, first_path_3, first_path_4, first_path_5, second_path_5, second_path_6);
    }

    private static List<Point2D> setUpSecondPoints() {
        Point2D second_path_1 = new Point2D.Double(30, 10);
        Point2D second_path_2 = new Point2D.Double(30, 20);
        Point2D second_path_3 = new Point2D.Double(30, 30);
        Point2D second_path_4 = new Point2D.Double(30, 40);

        return List.of(second_path_1, second_path_2, second_path_3, second_path_4);
    }
}
