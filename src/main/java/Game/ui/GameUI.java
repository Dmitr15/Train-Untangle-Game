package Game.ui;

import Game.Game;
import Game.event.GameActionEvent;
import Game.event.GameActionListener;
import javax.swing.*;
import java.awt.*;

public class GameUI extends JFrame {
    private final Game game;
    private final GamePanel gamePanel;
    private JLabel levelLabel = new JLabel("Уровень: ");

    public GameUI() {
        super("Train Game");
        this.game = new Game();
        this.game.addGameActionListener(new GameEventHandler());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new BorderLayout());

        // Панель управления
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton skipTurnButton = new JButton("Skip Turn");
        JButton exitButton = new JButton("Exit");

        controlPanel.add(startButton);
        controlPanel.add(skipTurnButton);
        controlPanel.add(exitButton);
        add(controlPanel, BorderLayout.NORTH);

        // Игровое поле
        gamePanel = new GamePanel(game);
        add(gamePanel, BorderLayout.CENTER);

        // Добавляем label для отображения уровня
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        levelLabel.setForeground(Color.BLUE);
        controlPanel.add(levelLabel);

        // Логика кнопок
        startButton.addActionListener(e -> {
            game.setUpGameLevel();
            game.start();
            gamePanel.updateFieldWidget();
            updateLevelLabel();
            gamePanel.repaint();
        });

        skipTurnButton.addActionListener(e -> {
            game.skip_a_turn();
            updateLevelLabel();
            gamePanel.repaint();
        });

        exitButton.addActionListener(e -> exitGame());

        setVisible(true);
    }

    private void updateLevelLabel() {
        if (game.getLevel() != null) {
            String levelName = game.getLevel().getClass().getSimpleName();
            levelLabel.setText("Уровень: " + levelName.replace("Level", ""));
        }
    }

    private class GameEventHandler implements GameActionListener {
        @Override
        public void trainIsRemoved(GameActionEvent event) {
            gamePanel.repaint();
        }

        @Override
        public void gameStatusChanged(GameActionEvent event) {
            updateLevelLabel();
            gamePanel.repaint();
        }
    }

    private void exitGame() {
        game.exit();
        int choice = JOptionPane.showConfirmDialog(this, "Вы действительно хотите выйти из игры?", "Подтверждение выхода", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameUI::new);
    }
}