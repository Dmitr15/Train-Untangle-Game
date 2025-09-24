package Game;

import Game.event.GameActionEvent;
import Game.event.GameActionListener;

import javax.swing.*;
import java.awt.*;

public class Main {

    static class GamePanel extends JFrame {
        private Game game;
        //private WidgetFactory widgetFactory;

        public GamePanel() throws HeadlessException {
            setVisible(true);

            //widgetFactory = new WidgetFactory();
            game = new Game();
            game.setUpGameLevel();
            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            //content.add(new FieldWidget(game.getLevel().getField(), widgetFactory));

            pack();
            setResizable(false);
            setSize(850, 500);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private final class GameController implements GameActionListener {
            @Override
            public void trainIsRemoved(GameActionEvent event) {

            }

            @Override
            public void gameStatusChanged(GameActionEvent event) {

            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

}