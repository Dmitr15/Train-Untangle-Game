package trains;

import Game.Direction;
import Game.Field;
import Game.Trains.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import Game.Trains.abstractPlatform;
import java.util.Arrays;

class TrainTest {
    private Field testField;
    private Train train;

    private List<Point2D> createStraightPath(int startX, int startY, int endX, int endY) {
        List<Point2D> path = new ArrayList<>();

        if (startX == endX) {
            for (int y = startY; y <= endY; y += 10) {
                path.add(new Point2D.Double(startX, y));
            }
        } else if (startY == endY) {
            for (int x = startX; x <= endX; x += 10) {
                path.add(new Point2D.Double(x, startY));
            }
        }

        return path;
    }

    @BeforeEach
    void setUp() {
        testField = new Field(100, 100);

        List<Point2D> horizontalPath = createStraightPath(10, 30, 60, 30);
        List<Point2D> verticalPath = createStraightPath(40, 10, 40, 60);

        testField.createAPath(horizontalPath);
        testField.createAPath(verticalPath);
    }

    @Test
    void testTrainMovesUp() {
        train = testField.createTrain(new Point2D.Double(40, 30), Direction.UP);
        assertNotNull(train);

        testField.move(train);
        Point2D newPosition = train.getPosition();

        assertEquals(new Point2D.Double(40, 20), newPosition);
        assertTrue(train.isActive());
    }

    @Test
    void testTrainMovesDown() {
        train = testField.createTrain(new Point2D.Double(40, 50), Direction.DOWN);
        assertNotNull(train);

        testField.move(train);
        assertEquals(new Point2D.Double(40, 60), train.getPosition());
        assertTrue(train.isActive());
    }

    @Test
    void testTrainMovesLeft() {
        train = testField.createTrain(new Point2D.Double(50, 30), Direction.LEFT);
        assertNotNull(train);

        testField.move(train);
        assertEquals(new Point2D.Double(40, 30), train.getPosition());
        assertTrue(train.isActive());
    }

    @Test
    void testTrainMovesRight() {
        train = testField.createTrain(new Point2D.Double(50, 30), Direction.RIGHT);
        assertNotNull(train);

        testField.move(train);
        assertEquals(new Point2D.Double(60, 30), train.getPosition());
        assertTrue(train.isActive());
    }

    @Test
    void testTrainMovesRightUp() {
        List<Point2D> complexPath = Arrays.asList(
                new Point2D.Double(70, 30),
                new Point2D.Double(70, 40)
        );
        testField.createAPath(complexPath);

        train = testField.createTrain(new Point2D.Double(60, 30), Direction.RIGHTUP);
        assertNotNull(train);

        testField.move(train);
        assertEquals(new Point2D.Double(70, 30), train.getPosition());

        testField.move(train);
        assertEquals(new Point2D.Double(70, 30), train.getPosition());
    }

    @Test
    void testTrainDeactivatesWhenNoPath() {
        train = testField.createTrain(new Point2D.Double(150, 150), Direction.RIGHT);
        assertNull(train);
    }

    @Test
    void testTrainStopsWhenCollision() {
        Train train1 = testField.createTrain(new Point2D.Double(40, 30), Direction.RIGHT);
        assertNotNull(train1);

        Train train2 = testField.createTrain(new Point2D.Double(50, 30), Direction.RIGHT);
        assertNotNull(train2);

        testField.move(train1);

        assertEquals(new Point2D.Double(50, 30), train1.getPosition());
    }

    @Test
    void testTrainPushesPlatform() {
        List<Point2D> platformPath = createStraightPath(10, 50, 40, 50);
        testField.createAPath(platformPath);

        train = testField.createTrain(new Point2D.Double(10, 50), Direction.RIGHT);
        assertNotNull(train);

        testField.createPlatform(new Point2D.Double(20, 50));
        abstractPlatform platform = testField.getPlatforms();
        assertNotNull(platform);

        testField.move(train);

        assertEquals(new Point2D.Double(20, 50), train.getPosition());

        assertEquals(new Point2D.Double(20, 50), platform.getPosition());
    }

    @Test
    void testTrainDeactivatesWhenOffPath() {
        List<Point2D> shortPath = Arrays.asList(
                new Point2D.Double(10, 50),
                new Point2D.Double(20, 50)
        );
        testField.createAPath(shortPath);

        train = testField.createTrain(new Point2D.Double(10, 50), Direction.RIGHT);
        assertNotNull(train);

        testField.move(train);
        assertEquals(new Point2D.Double(20, 50), train.getPosition());
        assertTrue(train.isActive());

        testField.move(train);
        assertFalse(train.isActive());
    }

    @Test
    void testMovementHistory() {
        train = testField.createTrain(new Point2D.Double(40, 30), Direction.RIGHT);
        assertNotNull(train);

        testField.move(train);
        List<Point2D> history = train.getMovementHistory();
        assertEquals(1, history.size());
        assertEquals(new Point2D.Double(50, 30), train.getPosition());

        testField.move(train);
        history = train.getMovementHistory();
        assertEquals(2, history.size());
        assertEquals(new Point2D.Double(60, 30), train.getPosition());

        train.resetMovementHistory();
        assertEquals(0, train.getMovementHistory().size());
    }

    @Test
    void testTrainCreationOnValidPath() {
        train = testField.createTrain(new Point2D.Double(40, 40), Direction.RIGHT);
        assertNotNull(train);
        assertTrue(train.isActive());
        assertEquals(new Point2D.Double(40, 40), train.getPosition());
        assertEquals(Direction.RIGHT, train.getDirection());
    }

    @Test
    void testTrainCreationOnInvalidPosition() {
        train = testField.createTrain(new Point2D.Double(25, 25), Direction.RIGHT);
        assertNull(train);
    }

    @Test
    void testMultipleTrainMovements() {
        Train train1 = testField.createTrain(new Point2D.Double(50, 30), Direction.RIGHT);
        Train train2 = testField.createTrain(new Point2D.Double(40, 40), Direction.UP);

        assertNotNull(train1);
        assertNotNull(train2);

        testField.move(train1);
        testField.move(train2);

        assertEquals(new Point2D.Double(60, 30), train1.getPosition());
        assertEquals(new Point2D.Double(40, 30), train2.getPosition());

        assertTrue(train1.isActive());
        assertTrue(train2.isActive());
    }

    @Test
    void testTrainDirectionChangeOnComplexPath() {
        List<Point2D> lShapePath = Arrays.asList(
                new Point2D.Double(70, 30),
                new Point2D.Double(70, 40),
                new Point2D.Double(70, 50),
                new Point2D.Double(60, 50)
        );
        testField.createAPath(lShapePath);

        train = testField.createTrain(new Point2D.Double(60, 30), Direction.LEFTBACKWARDDOWN);
        assertNotNull(train);

        testField.move(train);
        assertEquals(new Point2D.Double(70, 30), train.getPosition());

        testField.move(train);
        assertEquals(new Point2D.Double(70, 40), train.getPosition());

        testField.move(train);
        assertEquals(new Point2D.Double(70, 50), train.getPosition());

        testField.move(train);
        assertEquals(new Point2D.Double(60, 50), train.getPosition());
    }
}