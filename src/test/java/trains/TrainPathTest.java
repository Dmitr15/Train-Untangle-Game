package trains;
import Game.Direction;
//import Game.Pedestrian;
import Game.Train_path;
//import Game.Trains.Abstract_Train;
import Game.Trains.Train;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainPathTest {

    @Test
    public void testCorrectRouteInitialization() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0),
                new Point2D.Double(10, 10)
        );
        Train_path trainPath = new Train_path(route);
        assertNotNull(trainPath.get_route());
        assertEquals(3, trainPath.get_route().size());
    }

    @Test
    public void testIncorrectRouteInitialization() {
        List<Point2D> badRoute = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(15, 5) // Invalid step
        );
        Train_path trainPath = new Train_path(badRoute);
        assertNull(trainPath.get_route());
    }

    @Test
    public void testAddAndRemoveTrain() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0)
        );
        Train_path trainPath = new Train_path(route);
        Train train = new Train(new Point2D.Double(0, 0), Direction.RIGHT);

        trainPath.place_train(train);
        assertEquals(1, trainPath.getTrains().size());

        trainPath.removeTrain(train);
        assertEquals(0, trainPath.getTrains().size());
    }

    @Test
    public void testGetPositionOfTrains() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0)
        );
        Train_path trainPath = new Train_path(route);
        Train train1 = new Train(new Point2D.Double(0, 0), Direction.RIGHT);
        Train train2 = new Train(new Point2D.Double(10, 0), Direction.LEFT);

        trainPath.place_train(train1);
        trainPath.place_train(train2);

        List<Point2D> positions = trainPath.getPositionOfTrains(train1);
        assertEquals(1, positions.size());
        assertTrue(positions.contains(new Point2D.Double(10, 0)));
    }

    @Test
    public void testGetPointReturnsCorrectReference() {
        Point2D point = new Point2D.Double(10, 10);
        Point2D point1 = new Point2D.Double(20, 10);
        List<Point2D> route = Arrays.asList(point, point1);
        Train_path trainPath = new Train_path(route);

        Point2D result = trainPath.get_point(new Point2D.Double(10, 10));
        assertEquals(point, result);
    }
}
