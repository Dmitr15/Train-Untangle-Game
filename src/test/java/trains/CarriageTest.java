package trains;

import Game.Field;
import Game.Direction;
import Game.Trains.Platform;
import Game.Trains.Train;
import Game.Trains.abstractPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarriageTest {
    private Field field;
    private List<Point2D> testPath;

    @BeforeEach
    void setUp() {
        field = new Field(100, 100);

        // Создаем тестовый путь
        testPath = Arrays.asList(
                new Point2D.Double(10, 10),
                new Point2D.Double(20, 10),
                new Point2D.Double(30, 10),
                new Point2D.Double(40, 10),
                new Point2D.Double(50, 10)
        );
        field.createAPath(testPath);
    }

    @Test
    void testCarriageCreation() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        assertNotNull(carriage);
        assertEquals(new Point2D.Double(10, 10), carriage.getPosition());
        assertTrue(carriage.isActive());
        assertTrue(carriage.isMoveable());
        assertEquals(3, carriage.getMaxSteps());
        assertEquals(0, carriage.getCurrentSteps());
        assertNull(carriage.getPushingTrain());
    }

    @Test
    void testCarriageMovementWithinLimit() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);
        carriage.setDirection(Direction.RIGHT);

        carriage.moveAlong();
        assertEquals(new Point2D.Double(20, 10), carriage.getPosition());
        assertEquals(1, carriage.getCurrentSteps());
        assertTrue(carriage.isMoveable());
        assertTrue(carriage.isActive());

        carriage.moveAlong();
        assertEquals(new Point2D.Double(30, 10), carriage.getPosition());
        assertEquals(2, carriage.getCurrentSteps());
        assertTrue(carriage.isMoveable());

        carriage.moveAlong();
        assertEquals(new Point2D.Double(40, 10), carriage.getPosition());
        assertEquals(3, carriage.getCurrentSteps());
        assertTrue(carriage.isMoveable());
    }

    @Test
    void testCarriageStopsAfterMaxSteps() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 2);
        carriage.setDirection(Direction.RIGHT);

        Train mockTrain = new Train(new Point2D.Double(10, 10), Direction.RIGHT, testPath, field);
        carriage.setPushingTrain(mockTrain);

        carriage.moveAlong();
        assertEquals(1, carriage.getCurrentSteps());
        assertTrue(carriage.isMoveable());

        carriage.moveAlong();
        assertEquals(2, carriage.getCurrentSteps());
        assertTrue(carriage.isMoveable());

        carriage.moveAlong();
        assertEquals(0, carriage.getCurrentSteps());
        assertFalse(carriage.isMoveable()); // Должен стать неподвижным

        assertThrows(NullPointerException.class, carriage::moveAlong);
    }

    @Test
    void testCarriageResetOnInvalidMovement() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);
        carriage.setDirection(Direction.UP); // Неправильное направление

        Train mockTrain = new Train(new Point2D.Double(10, 10), Direction.RIGHT, testPath, field);
        carriage.setPushingTrain(mockTrain);

        carriage.moveAlong();

        assertEquals(0, carriage.getCurrentSteps());
        assertNull(carriage.getPushingTrain());
        assertFalse(carriage.isMoveable());
    }

    @Test
    void testCarriageWithPushingTrain() {
        Platform carriage = new Platform(new Point2D.Double(20, 10), testPath, field, 2);

        Train train = field.createTrain(new Point2D.Double(20, 10), Direction.RIGHT);
        assertNotNull(train);

        carriage.setDirection(Direction.RIGHT);

        carriage.moveAlong();

        assertEquals(1, carriage.getCurrentSteps());
        assertNotNull(carriage.getPushingTrain());
        assertEquals(train, carriage.getPushingTrain());
    }

    @Test
    void testCarriageDeactivation() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 2);

        assertTrue(carriage.isActive());
        carriage.deactivate();
        assertFalse(carriage.isActive());
    }

    @Test
    void testCarriageInheritance() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        assertInstanceOf(abstractPlatform.class, carriage);
        assertTrue(carriage.isActive());
    }

    @Test
    void testFindNextPointForCarriage() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        assertThrows(NullPointerException.class, carriage::moveAlong);

        carriage.setDirection(Direction.RIGHT);
        Point2D nextPoint = carriage.findNextPoint();
        assertEquals(new Point2D.Double(20, 10), nextPoint);
    }

    @Test
    void testCarriageSetters() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        carriage.setMoveable(false);
        assertFalse(carriage.isMoveable());

        carriage.setMoveable(true);
        assertTrue(carriage.isMoveable());

        Train train = new Train(new Point2D.Double(10, 10), Direction.RIGHT, testPath, field);
        carriage.setPushingTrain(train);
        assertEquals(train, carriage.getPushingTrain());

        carriage.setCurrentSteps(2);
        assertEquals(2, carriage.getCurrentSteps());
    }

    @Test
    void testCarriageFieldReference() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        assertNotNull(carriage.getField());
        assertEquals(field, carriage.getField());
    }

    @Test
    void testCarriagePositionUpdate() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        Point2D newPosition = new Point2D.Double(25, 25);
        carriage.setPosition(newPosition);

        assertEquals(newPosition, carriage.getPosition());
    }

    @Test
    void testCarriageDirectionUpdate() {
        Platform carriage = new Platform(new Point2D.Double(10, 10), testPath, field, 3);

        assertNull(carriage.getDirection());

        carriage.setDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, carriage.getDirection());

        carriage.setDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, carriage.getDirection());
    }

    @Test
    void testTrainPushingCarriage() {
        List<Point2D> extendedPath = Arrays.asList(
                new Point2D.Double(10, 10),
                new Point2D.Double(20, 10),
                new Point2D.Double(30, 10),
                new Point2D.Double(40, 10)
        );
        field.createAPath(extendedPath);

        Platform carriage = new Platform(new Point2D.Double(20, 10), extendedPath, field, 2);

        Train train = field.createTrain(new Point2D.Double(10, 10), Direction.RIGHT);

        carriage.setDirection(Direction.RIGHT);

        field.move(train);

        assertEquals(new Point2D.Double(20, 10), train.getPosition());

        field.move(train);
        assertEquals(new Point2D.Double(30, 10), train.getPosition());
    }

    @Test
    void testCarriageBlockingTrainAfterMaxSteps() {
        List<Point2D> path = Arrays.asList(
                new Point2D.Double(10, 10),
                new Point2D.Double(20, 10),
                new Point2D.Double(30, 10)
        );
        field.createAPath(path);

        field.createUniquePlatform(new Point2D.Double(20, 10), 1);

        Train train = field.createTrain(new Point2D.Double(10, 10), Direction.RIGHT);

        field.move(train);
        assertEquals(new Point2D.Double(20, 10), train.getPosition());

        field.move(train);

        assertEquals(new Point2D.Double(30, 10), train.getPosition());
    }
}