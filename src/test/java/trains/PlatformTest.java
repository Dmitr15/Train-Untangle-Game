package trains;

import Game.Field;
import Game.Direction;
import Game.Trains.Carriage;
import Game.Trains.abstractPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlatformTest {
    private Field field;
    private List<Point2D> testPath;

    @BeforeEach
    void setUp() {
        field = new Field(100, 100);

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
    void testPlatformCreation() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        assertNotNull(platform);
        assertEquals(new Point2D.Double(10, 10), platform.getPosition());
        assertTrue(platform.isActive());
        assertNull(platform.getDirection());
    }

    @Test
    void testPlatformMovementWithDirection() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);
        platform.setDirection(Direction.RIGHT);

        platform.moveAlong();

        assertEquals(new Point2D.Double(20, 10), platform.getPosition());
        assertTrue(platform.isActive());
    }

    @Test
    void testPlatformWithoutDirection() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        assertThrows(NullPointerException.class, platform::moveAlong);

        assertEquals(new Point2D.Double(10, 10), platform.getPosition());
        assertTrue(platform.isActive());
    }

    @Test
    void testPlatformDeactivation() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        assertTrue(platform.isActive());
        platform.deactivate();
        assertFalse(platform.isActive());
    }

    @Test
    void testPlatformPreviousPosition() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);
        platform.setDirection(Direction.RIGHT);

        Point2D previousPosition = platform.getPosition();
        platform.moveAlong();

        assertEquals(new Point2D.Double(10, 10), platform.getPreviousPosition());
        assertNotEquals(previousPosition, platform.getPosition());
    }

    @Test
    void testFindNextPointForPlatform() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        assertThrows(NullPointerException.class, platform::moveAlong);

        platform.setDirection(Direction.RIGHT);
        Point2D nextPoint = platform.findNextPoint();
        assertEquals(new Point2D.Double(20, 10), nextPoint);
    }

    @Test
    void testComplexDirectionsForPlatform() {
        Carriage platform = new Carriage(new Point2D.Double(30, 30), testPath, field);

        platform.setDirection(Direction.RIGHTUP);
        Point2D nextPoint = platform.findNextPoint();
        assertNotNull(nextPoint);

        platform.setDirection(Direction.LEFTDOWN);
        nextPoint = platform.findNextPoint();
        assertNotNull(nextPoint);

        platform.setDirection(Direction.BACKWARDDOWNRIGHT);
        nextPoint = platform.findNextPoint();
        assertNotNull(nextPoint);
    }

    @Test
    void testPlatformInheritance() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        assertInstanceOf(abstractPlatform.class, platform);
        assertTrue(platform.isActive());
    }

    @Test
    void testPlatformMultipleMovements() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);
        platform.setDirection(Direction.RIGHT);

        platform.moveAlong();
        assertEquals(new Point2D.Double(20, 10), platform.getPosition());

        platform.moveAlong();
        assertEquals(new Point2D.Double(30, 10), platform.getPosition());

        platform.moveAlong();
        assertEquals(new Point2D.Double(40, 10), platform.getPosition());

        assertTrue(platform.isActive());
    }

    @Test
    void testPlatformFieldReference() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        assertNotNull(platform.getField());
        assertEquals(field, platform.getField());
    }

    @Test
    void testPlatformPositionUpdate() {
        Carriage platform = new Carriage(new Point2D.Double(10, 10), testPath, field);

        Point2D newPosition = new Point2D.Double(25, 25);
        platform.setPosition(newPosition);

        assertEquals(newPosition, platform.getPosition());
    }
}