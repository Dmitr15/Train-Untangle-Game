package trains;

import Game.Game;
import Game.Trains.Train;
import Game.event.FieldActionEvent;
import Game.event.FieldActionListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Game.Field;
import Game.Direction;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldTest {

    private int eventCount = 0;

    class FieldObserver implements FieldActionListener {
        @Override
        public void allTrainsAreRemoved(FieldActionEvent event) {
            eventCount += 1;
        }
    }
    private Game game;
    private Field field;

    @BeforeEach
    public void testSetup(){
        eventCount = 0;
        field = new Field(100, 100);
        game = new Game();
        field.addFieldActionListener(new FieldObserver());
    }

    private static List<Point2D> setUpFirstPoints() {
        Point2D first_path_1 = new Point2D.Double(20, 30);
        Point2D first_path_2 = new Point2D.Double(30, 30);
        Point2D first_path_3 = new Point2D.Double(40, 30);

        return List.of(first_path_1, first_path_2, first_path_3);
    }

    @Test
    public void test_create_withNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(-100, 100));
    }

    @Test
    public void test_create_withNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(100, -100));
    }

    @Test
    public void test_create_withZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(100, 0));
    }

    @Test
    public void test_create_withZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(0, 100));
    }

    @Test
    public void test_create_withNegativeHeightAndNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(-100, -100));
    }

    @Test
    public void test_create_withZeroHeightAndZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(0, 0));
    }

    @Test
    public void test_create_train_not_success() {
        Train train = field.createTrain(new Point2D.Double(20, 20), Direction.UPRIGHT);
        Assertions.assertNull(train);
    }

    @Test
    public void test_create_train_success() {
        List<Point2D> first_path = setUpFirstPoints();
        field.createAPath(first_path);

        Train train = field.createTrain(new Point2D.Double(20, 30), Direction.UPRIGHT);
        Assertions.assertNotNull(train);
    }



}
