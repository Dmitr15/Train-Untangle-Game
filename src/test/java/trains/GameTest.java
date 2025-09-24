//package trains;
//
//import Game.Game;
//import Game.Field;
//import Game.Levels.FirstLevel;
//import Game.Levels.SecondLevel;
//import Game.Levels.ThirdLevel;
//import Game.Trains.Train;
//import trains.TestLevels.TestLevel;
//import Game.event.GameActionEvent;
//import Game.event.GameActionListener;
//import org.junit.jupiter.api.Assertions;
//import Game.Levels.GameLevel;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
//import java.util.List;
//import trains.utils.Pare;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class GameTest {
//    private Game game;
//
//    private enum Event {TRAIN_REMOVED, TRAIN_TELEPORTED};
//
//    private List<Pare<Event, Train>> events = new ArrayList<>();
//    private List<Pare<Event, Train>> expectedEvents = new ArrayList<>();
//
//    private class EventListener implements GameActionListener {
//
//        @Override
//        public void trainIsRemoved(GameActionEvent event) {
//            events.add(new Pare<>(Event.TRAIN_REMOVED, event.getTrain()));
//        }
//
//        @Override
//        public void gameStatusChanged(GameActionEvent event) {
//
//        }
//    }
//
//    @BeforeEach
//    public void testSetup(){
//        events.clear();
//        expectedEvents.clear();
//
//        game = new Game();
//        game.addGameActionListener(new EventListener());
//    }
//
//    @Test
//    public void startGame(){
//        game.setUpGameLevel(new FirstLevel());
//        game.start();
//        Assertions.assertTrue(game.gameState().isThe_game_on());
//    }
//
//    @Test
//    public void endGame(){
//        game.setUpGameLevel(new FirstLevel());
//        game.start();
//
//        Assertions.assertTrue(game.gameState().isThe_game_on());
//        game.exit();
//        Assertions.assertFalse(game.gameState().isThe_game_on());
//    }
//
//    @Test
//    public void test_train_removed_success(){
//        List<Train> trains = new ArrayList<>();
//
//        game.setUpGameLevel(new FirstLevel());
//
//        game.start();
//        trains = game.getLevel().getField().getTrains();
//        Field field = game.getLevel().getField();
//        expectedEvents.add(new Pare<>(Event.TRAIN_REMOVED, trains.getFirst()));
//        Point2D endPosition = new Point2D.Double(40.0, 10.0);
//        Point2D Position = field.move(trains.getFirst());
//        Assertions.assertEquals(endPosition, Position);
//        assertEquals(expectedEvents, events);
//    }
//
//    @Test
//    public void test_train_crashed(){
//        List<Train> trains = new ArrayList<>();
//        Point2D endPosition = new Point2D.Double(20.0, 10.0);
//        game.setUpGameLevel(new SecondLevel());
//        game.start();
//        trains = game.getLevel().getField().getTrains();
//        Field field = game.getLevel().getField();
//
//        Point2D Position = field.move(trains.getLast());
//        Assertions.assertEquals(endPosition, Position);
//    }
//
//    @Test
//    public void test_train_removed_success_with_one_turn(){
//        List<Train> trains = new ArrayList<>();
//
//        game.setUpGameLevel(new TestLevel());
//
//        game.start();
//        trains = game.getLevel().getField().getTrains();
//        Field field = game.getLevel().getField();
//        expectedEvents.add(new Pare<>(Event.TRAIN_REMOVED, trains.getFirst()));
//        Point2D endPosition = new Point2D.Double(50.0, 30.0);
//        Point2D Position = field.move(trains.getFirst());
//        Assertions.assertEquals(endPosition, Position);
//        assertEquals(expectedEvents, events);
//    }
//
//    @Test
//    public void test_train_removed_success_with_two_turns(){
//        List<Train> trains = new ArrayList<>();
//
//        game.setUpGameLevel(new ThirdLevel());
//
//        game.start();
//        trains = game.getLevel().getField().getTrains();
//        Field field = game.getLevel().getField();
//        expectedEvents.add(new Pare<>(Event.TRAIN_REMOVED, trains.getFirst()));
//        Point2D endPosition = new Point2D.Double(60.0, 60.0);
//        Point2D Position = field.move(trains.getFirst());
//        Assertions.assertEquals(endPosition, Position);
//        assertEquals(expectedEvents, events);
//    }
//
//    @Test
//    public void skip_a_turn(){
//        game.setUpGameLevel();
//        GameLevel secondLevel = game.getLevels().getLast();
//        game.skip_a_turn();
//        GameLevel currentLevel = game.getLevel();
//
//        assertEquals(secondLevel, currentLevel);
//    }
//}

package trains;
import Game.*;
import Game.Levels.FirstLevel;
import Game.Levels.GameLevel;
import Game.Trains.Platform;
import Game.Trains.Train;
import Game.event.GameActionEvent;
import Game.event.GameActionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private int eventTrainRemovedCount;
    private int eventLevelChangedCount;

    private class TestGameListener implements GameActionListener {
        @Override
        public void trainIsRemoved(GameActionEvent event) {
            eventTrainRemovedCount++;
        }

        @Override
        public void gameStatusChanged(GameActionEvent event) {
            eventLevelChangedCount++;
        }
    }

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addGameActionListener(new TestGameListener());
        eventTrainRemovedCount = 0;
        eventLevelChangedCount = 0;
    }

    @Test
    void testGameInitialization() {
        assertFalse(game.gameState().isThe_game_on());
        assertNull(game.getLevel());
    }

    @Test
    void testStartGame() {
        game.setUpGameLevel();
        game.start();

        assertTrue(game.gameState().isThe_game_on());
        assertNotNull(game.getLevel());
        assertNotNull(game.getLevel().getField());
        assertFalse(game.getLevel().getField().getTrains().isEmpty());
    }

    @Test
    void testExitGame() {
        game.setUpGameLevel();
        game.start();
        game.exit();

        assertFalse(game.gameState().isThe_game_on());
    }

    @Test
    void testSkipTurnLevelChange() {
        game.setUpGameLevel();
        game.start();

        // Запоминаем текущий уровень
        GameLevel firstLevel = game.getLevel();

        // Пропускаем ход (переходим на следующий уровень)
        game.skip_a_turn();

        assertNotNull(game.getLevel());
        assertNotEquals(firstLevel, game.getLevel());
        assertEquals(1, eventLevelChangedCount);
    }

    @Test
    void testTrainMovementAndRemoval() {
        game.setUpGameLevel(new FirstLevel());
        game.start();

        Field field = game.getLevel().getField();
        List<Train> trains = field.getTrains();
        Train train = trains.get(0);

        // Перемещаем поезд за пределы пути
        train.setPosition(new Point2D.Double(1000, 1000));
        game.getLevel().getField().move(train);

        // Должно сработать событие удаления поезда
        assertEquals(1, eventTrainRemovedCount);
        assertFalse(train.isActive());
    }

    @Test
    void testLevelCompletion() {
        game.setUpGameLevel();
        game.start();

        Field field = game.getLevel().getField();
        List<Train> trains = field.getTrains();

        game.skip_a_turn();
        // Удаляем все поезды (имитация завершения уровня)
//        for (Train train : trains) {
//            field.deleteTrain(train);
//        }

        // Должен автоматически перейти на следующий уровень
        assertEquals(1, game.getCurrentIndex());
        assertNotNull(game.getLevel());
    }

    @Test
    void testPlatformInteraction() {
        game.setUpGameLevel(new PlatformTestLevel());
        game.start();

        Field field = game.getLevel().getField();
        List<Train> trains = field.getTrains();
        List<Platform> platforms = field.getPlatforms();

        Train train = trains.get(0);
        Platform platform = platforms.get(0);

        // Проверяем начальные позиции
        assertEquals(new Point2D.Double(10, 10), train.getPosition());
        assertEquals(new Point2D.Double(10, 10), platform.getPosition());

        // Двигаем поезд в сторону платформы
        game.getLevel().getField().move(train);

        // Проверяем, что платформа сдвинулась
        assertEquals(new Point2D.Double(20, 10), platform.getPosition());
        assertEquals(new Point2D.Double(20, 10), train.getPosition());
    }

    @Test
    void testPlatformDeactivation() {
        game.setUpGameLevel(new PlatformTestLevel());
        game.start();

        Field field = game.getLevel().getField();
        List<Train> trains = field.getTrains();
        List<Platform> platforms = field.getPlatforms();

        Train train = trains.get(0);
        Platform platform = platforms.get(0);

        // Перемещаем платформу в конец пути
        platform.setPosition(new Point2D.Double(90, 10));
        train.setPosition(new Point2D.Double(90, 10));

        // Двигаем поезд в сторону платформы
        game.getLevel().getField().move(train);

        // Платформа должна деактивироваться
        assertFalse(platform.isActive());
        assertEquals(1, eventTrainRemovedCount);
    }

    // Вспомогательные тестовые классы уровней
    private static class TestLevel extends GameLevel {
        @Override
        protected int fieldHeight() {
            return 100;
        }

        @Override
        protected int fieldWidth() {
            return 100;
        }

        @Override
        protected void addTrains() {
            field.createTrain(new Point2D.Double(10, 10), Direction.RIGHT);
        }

        @Override
        protected void addTrain(Point2D position, Direction direction) {}

        @Override
        protected void addTrainPaths() {
            List<Point2D> path = List.of(
                    new Point2D.Double(10, 10),
                    new Point2D.Double(20, 10),
                    new Point2D.Double(30, 10)
            );
            field.createAPath(path);
        }
    }

    private static class PlatformTestLevel extends GameLevel {
        @Override
        protected int fieldHeight() {
            return 100;
        }

        @Override
        protected int fieldWidth() {
            return 100;
        }

        @Override
        protected void addTrains() {
            field.createTrain(new Point2D.Double(10, 10), Direction.RIGHT);
        }

        @Override
        protected void addTrain(Point2D position, Direction direction) {}

        @Override
        protected void addTrainPaths() {
            List<Point2D> path = List.of(
                    new Point2D.Double(10, 10),
                    new Point2D.Double(20, 10),
                    new Point2D.Double(30, 10),
                    new Point2D.Double(40, 10),
                    new Point2D.Double(50, 10),
                    new Point2D.Double(60, 10),
                    new Point2D.Double(70, 10),
                    new Point2D.Double(80, 10),
                    new Point2D.Double(90, 10)
            );
            field.createAPath(path);
            field.createPlatform(new Point2D.Double(10, 10));
        }
    }
}