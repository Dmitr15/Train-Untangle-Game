package Game;

import Game.Levels.FirstLevel;
import Game.Levels.GameLevel;
import Game.Levels.SecondLevel;
import Game.Levels.ThirdLevel;
import Game.Trains.Train;
import Game.event.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private stateOfTheGame game_status = new stateOfTheGame();
    private GameLevel currentLevel;
    private List<GameLevel> levels = new ArrayList<>();

    private int currentLevelIndex = 0;

    public List<GameLevel> getLevels() {
        return levels;
    }

    public void setUpGameLevel(){
        levels.clear();
        GameLevel firstLevel = new FirstLevel();
        GameLevel secondLevel = new SecondLevel();
        GameLevel thirdLevel = new ThirdLevel();
        levels.add(firstLevel);
        levels.add(secondLevel);
        levels.add(thirdLevel);

        if (!levels.isEmpty()) {
            currentLevel = levels.get(currentLevelIndex);
            currentLevel.createField();
            game_status.set_the_game_is_on();
        }
    }

    public void setUpGameLevel(GameLevel level){
        if (!this.game_status.isThe_game_on()) {
            this.game_status.set_the_game_is_on();
            this.currentLevel = level;
            this.currentLevel.createField();
        }
    }

    public GameLevel getLevel(){
        return this.currentLevel;
    }

    public void skip_a_turn(){

        if (levels.isEmpty()) return;

        // Переходим на следующий уровень
        currentLevelIndex = (currentLevelIndex + 1) % levels.size();
        currentLevel = levels.get(currentLevelIndex);
        currentLevel.createField();
        start(); // Регистрируем обработчики для новых поездов

        // Оповещаем UI о смене уровня
        fireLevelChanged();
    }

    public void start(){
        if (this.currentLevel != null) {
            List<Train> trains = this.currentLevel.getField().getTrains();
            for (Train train: trains) {
                train.addTrainActionListener(new TrainObserver());
            }
        }
    }

    public void exit(){
        game_status.unset_the_game_is_on();

    }

    public stateOfTheGame gameState(){
        return game_status;
    }

    private ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    public void addGameActionListener( GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void removeGameActionListener(GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    private class TrainObserver implements TrainActionListener{
        @Override
        public void trainIsTeleported(TrainActionEvent event) {
            fireTrainIsTeleported(event.getTrain());
            determineOutcomeGame();
        }
    }

    private void fireTrainIsTeleported(Train train){
        this.currentLevel.getField().deleteTrain(train);
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setTrain(train);
            listener.trainIsRemoved(event);
        }
    }

    private void nextLevel() {
        if (levels.isEmpty()) return;
        currentLevelIndex = (currentLevelIndex + 1) % levels.size();
        currentLevel = levels.get(currentLevelIndex);
        currentLevel.createField();
        start();

        // Оповещаем UI о смене уровня
        fireLevelChanged();
    }

    private void determineOutcomeGame() {
        if (currentLevel != null && currentLevel.getField().getTrains().isEmpty()) {
            nextLevel();
        }
    }

    private void fireLevelChanged() {
        for (GameActionListener listener : gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            listener.gameStatusChanged(event);
        }
    }
    public int getCurrentIndex(){
        return currentLevelIndex;
    }
}
