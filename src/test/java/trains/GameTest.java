package trains;

import Game.Levels.FirstLevel;
import Game.Levels.GameLevel;
import Game.Trains.Train;
import Game.event.GameActionEvent;
import Game.event.GameActionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.List;
import Game.Game;
import Game.Field;
import Game.Trains.abstractPlatform;

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

        int firstLevelIndex = game.getCurrentIndex();

        game.skip_a_turn();

        assertNotNull(game.getLevel());
        assertNotEquals(firstLevelIndex, game.getCurrentIndex());
        assertEquals(1, eventLevelChangedCount);
    }

    @Test
    void testTrainMovementAndRemoval() {
        FirstLevel level = new FirstLevel();
        level.createField();
        game.setUpGameLevel(level);
        game.start();

        Field field = game.getLevel().getField();
        List<Train> trains = field.getTrains();

        if (!trains.isEmpty()) {
            Train train = trains.getFirst();

            Point2D originalPosition = train.getPosition();

            field.move(train);

            assertNotEquals(originalPosition, train.getPosition());

            assertTrue(train.isActive());
        }
    }

    @Test
    void testLevelCompletion() {
        game.setUpGameLevel();
        game.start();

        int initialLevelIndex = game.getCurrentIndex();
        Field field = game.getLevel().getField();
        List<Train> trains = field.getTrains();

        for (Train train : trains) {
            field.deleteTrain(train);
        }

        assertTrue(field.getTrains().isEmpty());
        assertEquals(initialLevelIndex, game.getCurrentIndex());
    }

    @Test
    void testPlatformInteraction() {
        FirstLevel level = new FirstLevel();
        level.createField();
        game.setUpGameLevel(level);
        game.start();

        Field field = game.getLevel().getField();

        abstractPlatform platform = field.getPlatforms();
        assertNotNull(platform);
        assertTrue(platform.isActive());

        assertNotNull(platform.getPosition());
    }

    @Test
    void testCurrentLevelIndex() {
        game.setUpGameLevel();
        game.start();

        assertEquals(0, game.getCurrentIndex());

        game.skip_a_turn();
        assertEquals(1, game.getCurrentIndex());

        game.skip_a_turn();
        assertEquals(2, game.getCurrentIndex());

        game.skip_a_turn();
        assertEquals(0, game.getCurrentIndex());
    }

    @Test
    void testGameStateManagement() {
        assertFalse(game.gameState().isThe_game_on());

        game.setUpGameLevel();
        assertTrue(game.gameState().isThe_game_on());

        game.exit();
        assertFalse(game.gameState().isThe_game_on());

        game.setUpGameLevel();
        assertTrue(game.gameState().isThe_game_on());
    }

    @Test
    void testMultipleLevelSetup() {
        FirstLevel customLevel = new FirstLevel();
        game.setUpGameLevel(customLevel);

        assertTrue(game.gameState().isThe_game_on());
        assertNotNull(game.getLevel());
        assertEquals(customLevel, game.getLevel());

        game.setUpGameLevel();
        assertNotNull(game.getLevel());
    }

    @Test
    void testEventNotifications() {
        assertEquals(0, eventTrainRemovedCount);
        assertEquals(0, eventLevelChangedCount);

        game.setUpGameLevel();
        game.skip_a_turn();

        assertEquals(1, eventLevelChangedCount);

        Field field = game.getLevel().getField();
        if (!field.getTrains().isEmpty()) {
            Train train = field.getTrains().getFirst();
            field.deleteTrain(train);
        }
    }
}