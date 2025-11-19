package trains;

import Game.Direction;
import Game.Trains.Train;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import Game.Field;
import org.junit.jupiter.api.BeforeEach;

public class TrainPathTest {
    private Field field;

    @BeforeEach
    public void setUp() {
        field = new Field(100, 100);
    }

    @Test
    public void testCorrectRouteInitialization() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0),
                new Point2D.Double(10, 10)
        );
        field.createAPath(route);
        assertNotNull(field.getPaths());
        assertEquals(3, field.getPaths().size());
        assertTrue(field.getPaths().containsAll(route));
    }

    @Test
    public void testIncorrectRouteInitialization() {
        List<Point2D> badRoute = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(15, 5)
        );

        int initialPathCount = field.getPaths().size();
        field.createAPath(badRoute);

        assertEquals(initialPathCount, field.getPaths().size());
    }

    @Test
    public void testRouteWithSinglePoint() {
        List<Point2D> singlePointRoute = List.of(
                new Point2D.Double(10, 10)
        );

        int initialPathCount = field.getPaths().size();
        field.createAPath(singlePointRoute);

        assertEquals(initialPathCount, field.getPaths().size());
    }

    @Test
    public void testRouteOutsideFieldBounds() {
        List<Point2D> outOfBoundsRoute = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(150, 150)
        );

        int initialPathCount = field.getPaths().size();
        field.createAPath(outOfBoundsRoute);

        assertEquals(initialPathCount, field.getPaths().size());
    }

    @Test
    public void testAddAndRemoveTrain() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0)
        );
        field.createAPath(route);

        Train train = field.createTrain(new Point2D.Double(0, 0), Direction.RIGHT);
        assertNotNull(train);
        assertEquals(1, field.getTrains().size());

        field.deleteTrain(train);
        assertEquals(0, field.getTrains().size());
    }

    @Test
    public void testTrainPositionOnPath() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0),
                new Point2D.Double(20, 0)
        );
        field.createAPath(route);

        Train train1 = field.createTrain(new Point2D.Double(0, 0), Direction.RIGHT);
        Train train2 = field.createTrain(new Point2D.Double(10, 0), Direction.RIGHT);

        assertNotNull(train1);
        assertNotNull(train2);

        assertEquals(new Point2D.Double(0, 0), train1.getPosition());
        assertEquals(new Point2D.Double(10, 0), train2.getPosition());

        assertTrue(field.getPaths().contains(train1.getPosition()));
        assertTrue(field.getPaths().contains(train2.getPosition()));
    }

    @Test
    public void testTrainMovementAlongPath() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(10, 10),
                new Point2D.Double(20, 10),
                new Point2D.Double(30, 10)
        );
        field.createAPath(route);

        Train train = field.createTrain(new Point2D.Double(10, 10), Direction.RIGHT);
        assertNotNull(train);

        Point2D startPosition = train.getPosition();

        field.move(train);

        Point2D newPosition = train.getPosition();
        assertNotEquals(startPosition, newPosition);

        assertTrue(field.getPaths().contains(newPosition));

        assertTrue(train.isActive());
    }

    @Test
    public void testTrainCreationOffPath() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0)
        );
        field.createAPath(route);

        Train train = field.createTrain(new Point2D.Double(50, 50), Direction.RIGHT);
        assertNull(train);
    }

    @Test
    public void testMultiplePaths() {
        List<Point2D> route1 = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0),
                new Point2D.Double(20, 0)
        );

        List<Point2D> route2 = Arrays.asList(
                new Point2D.Double(0, 10),
                new Point2D.Double(10, 10),
                new Point2D.Double(20, 10)
        );

        field.createAPath(route1);
        field.createAPath(route2);

        assertEquals(6, field.getPaths().size());
        assertTrue(field.getPaths().containsAll(route1));
        assertTrue(field.getPaths().containsAll(route2));
    }

    @Test
    public void testPathValidationWithDiagonalMovement() {
        List<Point2D> diagonalRoute = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 10) // Диагональное движение
        );

        int initialPathCount = field.getPaths().size();
        field.createAPath(diagonalRoute);

        assertEquals(initialPathCount, field.getPaths().size());
    }

    @Test
    public void testConflictingDirectionsOnPath() {
        List<Point2D> route = Arrays.asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(10, 0),
                new Point2D.Double(20, 0)
        );
        field.createAPath(route);

        Train train1 = field.createTrain(new Point2D.Double(0, 0), Direction.RIGHT);
        assertNotNull(train1);

        Train train2 = field.createTrain(new Point2D.Double(20, 0), Direction.LEFT);

        if (train2 != null) {
            assertEquals(2, field.getTrains().size());
        } else {
            assertEquals(1, field.getTrains().size());
        }
    }
}