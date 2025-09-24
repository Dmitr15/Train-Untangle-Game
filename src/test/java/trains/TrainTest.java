package trains;

import Game.Direction;
import Game.Field;
import Game.Trains.Platform;
import Game.Train_path;
import Game.Trains.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainTest {
    private Train train;
    private List<Train_path> trainPaths;
    private Field testField;

    private static List<Point2D> createStraightPath(int startX, int startY, int endX, int endY, int step) {
        List<Point2D> path = new ArrayList<>();

        int x = startX;
        int y = startY;
        int dx = Integer.compare(endX, startX);
        int dy = Integer.compare(endY, startY);

        path.add(new Point2D.Double(x, y));

        while (x != endX || y != endY) {
            x += dx * step;
            y += dy * step;
            path.add(new Point2D.Double(x, y));
        }

        return path;
    }

    private static List<Point2D> createDiagonalPath(int startX, int startY, int endX, int endY, int step) {
        List<Point2D> path = new ArrayList<>();

        int x = startX;
        int y = startY;
        int dx = Integer.compare(endX, startX);
        int dy = Integer.compare(endY, startY);

        path.add(new Point2D.Double(x, y));

        while (x != endX && y != endY) {
            x += dx * step;
            y += dy * step;
            path.add(new Point2D.Double(x, y));
        }

        return path;
    }

    @BeforeEach
    void setUp() {
        testField = new Field(100, 100);

        // Создаем тестовые пути
        List<Point2D> horizontalPath = createStraightPath(0, 50, 100, 50, 10);
        List<Point2D> verticalPath = createStraightPath(50, 0, 50, 100, 10);
        List<Point2D> verticalPath1 = createStraightPath(70, 0, 70, 100, 10);
        List<Point2D> horizontalPath2 = createStraightPath(0, 70, 100, 70, 10);

        trainPaths = new ArrayList<>();
        trainPaths.add(new Train_path(horizontalPath));
        trainPaths.add(new Train_path(verticalPath));
        trainPaths.add(new Train_path(verticalPath1));
        trainPaths.add(new Train_path(horizontalPath2));

        // Добавляем пути в поле
        testField.createAPath(horizontalPath);
        testField.createAPath(verticalPath);
        testField.createAPath(verticalPath1);
        testField.createAPath(horizontalPath2);
    }

    @Test
    void testTrainMovesUp() {
        train = new Train(new Point2D.Double(50, 50), Direction.UP);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 40), train.getPosition());
    }

    @Test
    void testTrainMovesDown() {
        train = new Train(new Point2D.Double(50, 50), Direction.DOWN);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 60), train.getPosition());
    }

    @Test
    void testTrainMovesLeft() {
        train = new Train(new Point2D.Double(50, 50), Direction.LEFT);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(40, 50), train.getPosition());
    }

    @Test
    void testTrainMovesRight() {
        train = new Train(new Point2D.Double(50, 50), Direction.RIGHT);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(60, 50), train.getPosition());
    }

    @Test
    void testTrainMovesRightUp() {
        train = new Train(new Point2D.Double(40, 50), Direction.RIGHTUP);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 40), train.getPosition());
    }

    @Test
    void testTrainMovesLeftUp() {
        train = new Train(new Point2D.Double(60, 50), Direction.LEFTUP);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 40), train.getPosition());
    }

    @Test
    void testTrainMovesDownRight() {
        train = new Train(new Point2D.Double(50, 40), Direction.DOWNRIGHT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(60, 50), train.getPosition());
    }

    @Test
    void testTrainMovesDownLeft() {
        train = new Train(new Point2D.Double(50, 40), Direction.DOWNLEFT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(40, 50), train.getPosition());
    }

    @Test
    void testTrainMovesUpRight() {
        train = new Train(new Point2D.Double(50, 60), Direction.UPRIGHT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);

        assertEquals(new Point2D.Double(60, 50), train.getPosition());
    }

    @Test
    void testTrainMovesUpLeft() {
        train = new Train(new Point2D.Double(50, 60), Direction.UPLEFT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(40, 50), train.getPosition());
    }

    @Test
    void testTrainMovesLeftDown() {
        train = new Train(new Point2D.Double(60, 50), Direction.LEFTDOWN);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 60), train.getPosition());
    }

    @Test
    void testTrainMovesRightDown() {
        train = new Train(new Point2D.Double(40, 50), Direction.RIGHTDOWN);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 60), train.getPosition());
    }

    @Test
    void testTrainMovesBackwardDownRight() {
        train = new Train(new Point2D.Double(50, 60), Direction.BACKWARDDOWNRIGHT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(70, 60), train.getPosition());
    }

    @Test
    void testTrainMovesBackwardDownLeft() {
        train = new Train(new Point2D.Double(70, 60), Direction.BACKWARDDOWNLEFT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 60), train.getPosition());
    }

    @Test
    void testTrainMovesRightBackwardDown() {
        train = new Train(new Point2D.Double(60, 50), Direction.RIGHTBACKWARDDOWN);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(60, 70), train.getPosition());
    }

    @Test
    void testTrainMovesRightBackwardUp() {
        train = new Train(new Point2D.Double(60, 70), Direction.RIGHTBACKWARDUP);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(60, 50), train.getPosition());
    }

    @Test
    void testTrainMovesLeftBackwardUp() {
        train = new Train(new Point2D.Double(40, 70), Direction.LEFTBACKWARDUP);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(40, 50), train.getPosition());
    }

    @Test
    void testTrainMovesLeftBackwardDown() {
        train = new Train(new Point2D.Double(40, 50), Direction.LEFTBACKWARDDOWN);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(40, 70), train.getPosition());
    }

    @Test
    void testTrainMovesUpBackwardRight() {
        train = new Train(new Point2D.Double(50, 60), Direction.UPBACKWARDRIGHT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(70, 60), train.getPosition());
    }

    @Test
    void testTrainMovesUpBackwardLeft() {
        train = new Train(new Point2D.Double(70, 60), Direction.UPBACKWARDLEFT);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);
        assertEquals(new Point2D.Double(50, 60), train.getPosition());
    }

    @Test
    void testTrainDeactivatesWhenNoPath() {
        train = new Train(new Point2D.Double(150, 150), Direction.RIGHT);
        assertTrue(train.isActive());
        train.moveAlongPath(trainPaths, testField);
        assertFalse(train.isActive());
    }

    @Test
    void testTrainStopsWhenCollision() {
        // Создаем первый поезд
        Train train1 = new Train(new Point2D.Double(50, 50), Direction.RIGHT);

        // Создаем второй поезд на пути первого
        Train train2 = new Train(new Point2D.Double(60, 50), Direction.LEFT);

        // Размещаем поезда на поле
        testField.createTrain(train1.getPosition(), train1.getDirection());
        testField.createTrain(train2.getPosition(), train2.getDirection());

        // Двигаем первый поезд
        train1.moveAlongPath(trainPaths, testField);

        // Поезд не должен двигаться из-за столкновения
        assertEquals(new Point2D.Double(60, 50), train1.getPosition());
    }

    @Test
    void testTrainPushesPlatform() {
        // Создаем поезд
        train = new Train(new Point2D.Double(10, 50), Direction.RIGHT);


        for (Train_path path: trainPaths) {
            testField.setPaths(path);
        }

        // Создаем платформу на пути поезда
        testField.createPlatform(new Point2D.Double(20, 50));

        // Двигаем поезд
        train.moveAlongPath(trainPaths, testField);

        // Проверяем что поезд переместился
        assertEquals(new Point2D.Double(20, 50), train.getPosition());

        // Двигаем поезд
        train.moveAlongPath(trainPaths, testField);

        // Проверяем что платформа сдвинулась
        Platform platform = testField.getPlatforms().get(0);
        assertEquals(new Point2D.Double(30, 50), platform.getPosition());
    }

    @Test
    void testTrainDeactivatesPlatformWhenOffPath() {
        // Создаем поезд в конце пути
        train = new Train(new Point2D.Double(90, 50), Direction.RIGHT);

        // Создаем платформу на последней точке пути
        testField.createPlatform(new Point2D.Double(100, 50));

        // Двигаем поезд
        train.moveAlongPath(trainPaths, testField);
        train.moveAlongPath(trainPaths, testField);

        // Платформа должна деактивироваться
        //Platform platform = testField.getPlatforms().get(0);
        assertTrue(testField.getPlatforms().size()== 0);
        //assertFalse(platform.isActive());
    }

    @Test
    void testMovementHistory() {
        train = new Train(new Point2D.Double(50, 50), Direction.RIGHT);

        // Первое движение
        train.moveAlongPath(trainPaths, testField);
        assertEquals(1, train.getMovementHistory().size());
        assertEquals(new Point2D.Double(50, 50), train.getMovementHistory().get(0));

        // Второе движение
        train.moveAlongPath(trainPaths, testField);
        //assertEquals(2, train.getMovementHistory().size());
        assertTrue(train.getMovementHistory().size() == 2);
        assertEquals(new Point2D.Double(60, 50), train.getMovementHistory().get(1));

        // Сброс истории
        train.resetMovementHistory();
        assertEquals(0, train.getMovementHistory().size());
    }
}