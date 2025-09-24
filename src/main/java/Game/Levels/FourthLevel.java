package Game.Levels;
import Game.Direction;

import Game.Trains.Train;

import java.awt.geom.Point2D;
import java.util.List;
public class FourthLevel extends GameLevel{

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
        //Train train_1 = field.createTrain(new Point2D.Double(210, 40), Direction.UPBACKWARDRIGHT);
    }

    @Override
    protected void addTrain(Point2D position, Direction direction) {
        field.createTrain(position, direction);
    }

    @Override
    protected void addTrainPaths() {
        List<Point2D> first_path = setUpFirstPoints();
        field.createAPath(first_path);

        List<Point2D> second_path =setUpSecondPoints();
        field.createAPath(second_path);

        List<Point2D> third_path =setUpThirdPoints();
        field.createAPath(third_path);

        List<Point2D> fourth_path =setUpSecondPoints();
        field.createAPath(fourth_path);

        List<Point2D> fifth_path =setUpThirdPoints();
        field.createAPath(fifth_path);
    }

    private static List<Point2D> setUpFirstPoints() {
        Point2D first_path_1 = new Point2D.Double(10, 30);
        Point2D first_path_2 = new Point2D.Double(10, 40);
        Point2D first_path_3 = new Point2D.Double(10, 50);
        Point2D first_path_4 = new Point2D.Double(10, 60);
        Point2D first_path_5 = new Point2D.Double(10, 70);
        Point2D first_path_6 = new Point2D.Double(10, 80);

        return List.of(first_path_1, first_path_2, first_path_3, first_path_4, first_path_5, first_path_6);
    }

    private static List<Point2D> setUpSecondPoints() {
        Point2D second_path_1 = new Point2D.Double(0, 70);
        Point2D second_path_2 = new Point2D.Double(10, 70);
        Point2D second_path_3 = new Point2D.Double(20, 70);
        Point2D second_path_4 = new Point2D.Double(30, 70);

        return List.of(second_path_1, second_path_2, second_path_3, second_path_4);
    }

    private static List<Point2D> setUpThirdPoints() {
        Point2D third_path_1 = new Point2D.Double(10, 50);
        Point2D third_path_2 = new Point2D.Double(20, 50);
        Point2D third_path_3 = new Point2D.Double(30, 50);
        Point2D third_path_4 = new Point2D.Double(40, 50);
        Point2D third_path_5 = new Point2D.Double(50, 50);
        Point2D third_path_6 = new Point2D.Double(60, 50);
        Point2D third_path_7 = new Point2D.Double(70, 50);
        Point2D third_path_8 = new Point2D.Double(80, 50);

        return List.of(third_path_1, third_path_2, third_path_3, third_path_4, third_path_5, third_path_6, third_path_7, third_path_8);
    }
}
