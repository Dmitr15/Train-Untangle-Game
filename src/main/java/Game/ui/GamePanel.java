package Game.ui;

import Game.Game;
import Game.Trains.Train;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Game.Trains.abstractPlatform;
import Game.event.GameActionEvent;
import Game.event.GameActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private FieldWidget fieldWidget;
    private final Game game;
    private static final int CELL_SIZE = 17; // Увеличенный размер клетки
    private static final int OFFSET = 30;
    private static final int ANIMATION_SPEED = 2;
    private final Map<abstractPlatform, Point2D> animatedPositions = new HashMap<>();
    private final Map<abstractPlatform, Point2D.Double> targetPositions = new HashMap<>();
    private abstractPlatform selectedTrain = null;


    public GamePanel(Game game) {
        this.game = game;
        this.fieldWidget = null;

        setBackground(new Color(34, 139, 34));
        setPreferredSize(new Dimension(500, 500));

        Timer timer = new Timer(30, e -> updateAnimation());
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getPoint());
            }
        });

        game.addGameActionListener(new GameController());

        // Инициализация при первом доступном уровне
        initializeFieldWidget();
    }

    private void initializeFieldWidget() {
        if (game.getLevel() != null && game.getLevel().getField() != null) {
            this.fieldWidget = new FieldWidget(game.getLevel().getField());
        }
    }

    private final class GameController implements GameActionListener {
        @Override
        public void trainIsRemoved(GameActionEvent event) {
            repaint();
        }

        @Override
        public void gameStatusChanged(GameActionEvent event) {
            animatedPositions.clear();
            selectedTrain = null;
            initializeFieldWidget();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (fieldWidget != null) {
            fieldWidget.updateTrainWidgets(animatedPositions, selectedTrain);
            fieldWidget.draw(g2);
        } else {
            // Отрисовка заглушки при отсутствии поля
            g2.setColor(Color.WHITE);
            g2.drawString("Загрузка уровня...", 50, 50);
        }

        // Отрисовка названия уровня
        if (game.getLevel() != null) {
            String levelName = game.getLevel().getClass().getSimpleName();
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
        }
    }

    /** Обработка клика по поезду */
    private void handleClick(Point point) {
        if (game.getLevel() == null || fieldWidget == null) return;

        List<Train> trains = game.getLevel().getField().getTrains();

        for (abstractPlatform train : trains) {
            Point2D animPos = animatedPositions.get(train);
            if (animPos == null) continue;

            int x = OFFSET + (int) Math.round(animPos.getX() * CELL_SIZE);
            int y = OFFSET + (int) Math.round(animPos.getY() * CELL_SIZE);

            Rectangle hitbox = new Rectangle(x - CELL_SIZE, y - CELL_SIZE, CELL_SIZE * 2, CELL_SIZE * 2);

            if (hitbox.contains(point)) {
                selectedTrain = train;

                //  Запускаем анимацию движения
                startTrainAnimation(train);
                return;
            }
        }
    }

    private void startTrainAnimation(abstractPlatform train) {
        if (fieldWidget == null) return;
        // Сохраняем исходную позицию ДО начала движения
        Point2D.Double originalPosition = new Point2D.Double(train.getPosition().getX(), train.getPosition().getY());

        // Сбрасываем историю движений
        train.resetMovementHistory();

        // Создаем таймер для движения по пути
        Timer pathTimer = new Timer(300, e -> {
            if (!train.isActive() || !game.getLevel().getField().getTrains().contains(train)) {
                ((Timer)e.getSource()).stop();
                return;
            }

            // Сохраняем текущую позицию перед движением
            Point2D.Double currentPosition = new Point2D.Double(train.getPosition().getX(), train.getPosition().getY());

            // Двигаем поезд
            game.getLevel().getField().move(train);

            // Получаем новую позицию
            Point2D.Double newPosition = new Point2D.Double(train.getPosition().getX(), train.getPosition().getY());

            if (currentPosition.equals(newPosition)) {
                // Поезд не смог двигаться - столкновение
                List<Point2D> history = train.getMovementHistory();
                if (!history.isEmpty()) {
                    // Анимация возврата по всему пути
                    train.resetNum();
                    animateReturnAlongPath(train, history, originalPosition);
                }
                ((Timer)e.getSource()).stop();
            } else {
                // Плавное движение к следующей точке
                animateTrainMove(train, currentPosition, newPosition);
            }
        });
        pathTimer.start();
    }

    private void animateReturnAlongPath(abstractPlatform train, List<Point2D> path, Point2D.Double originalPosition) {
        // Создаем полный путь для возврата: текущая позиция + история в обратном порядке + исходная позиция
        List<Point2D> returnPath = new ArrayList<>();
        returnPath.add(new Point2D.Double(train.getPosition().getX(), train.getPosition().getY()));

        // Добавляем историю в обратном порядке
        for (int i = path.size() - 1; i >= 0; i--) {
            returnPath.add(path.get(i));
        }

        // Добавляем исходную позицию
        returnPath.add(originalPosition);

        final int durationPerSegment = 200; // Длительность анимации на сегмент
        final long startTime = System.currentTimeMillis();

        Timer returnTimer = new Timer(16, e -> {
            long now = System.currentTimeMillis();
            float totalElapsed = now - startTime;
            float totalDuration = durationPerSegment * (returnPath.size() - 1);
            float progress = Math.min(1.0f, totalElapsed / totalDuration);

            // Вычисляем текущий сегмент
            int segmentIndex = (int) (progress * (returnPath.size() - 1));
            float segmentProgress = progress * (returnPath.size() - 1) - segmentIndex;

            if (segmentIndex >= returnPath.size() - 1) {
                // Анимация завершена
                train.setPosition(originalPosition);
                animatedPositions.put(train, originalPosition);
                train.resetMovementHistory();
                ((Timer) e.getSource()).stop();
                repaint();
                return;
            }

            // Получаем точки текущего сегмента
            Point2D start = returnPath.get(segmentIndex);
            Point2D end = returnPath.get(segmentIndex + 1);

            // Рассчитываем промежуточную позицию
            double x = start.getX() + (end.getX() - start.getX()) * segmentProgress;
            double y = start.getY() + (end.getY() - start.getY()) * segmentProgress;

            // Обновляем позицию для отрисовки
            animatedPositions.put(train, new Point2D.Double(x, y));
            repaint();
        });
        returnTimer.start();
    }

    /**  Плавная анимация поезда между двумя точками */
    private void animateTrainMove(abstractPlatform train, Point2D.Double start, Point2D.Double end) {
        final long startTime = System.currentTimeMillis();
        final int duration = 300;

        Timer animTimer = new Timer(16, ev -> {
            long now = System.currentTimeMillis();
            float progress = Math.min(1.0f, (now - startTime) / (float) duration);
            double x = start.x + (end.x - start.x) * progress;
            double y = start.y + (end.y - start.y) * progress;

            animatedPositions.put(train, new Point2D.Double(x, y));
            repaint();

            if (progress >= 1.0f) {
                ((Timer) ev.getSource()).stop();
            }
        });
        animTimer.start();
    }

    /**  Плавное движение поездов */
    private void updateAnimation() {
        if (game.getLevel() == null || fieldWidget == null) return;
        List<Train> trains = game.getLevel().getField().getTrains();
        for (abstractPlatform train : trains) {

            Point2D.Double current = (Point2D.Double)animatedPositions.computeIfAbsent(train, t -> new Point2D.Double(train.getPosition().getX(), train.getPosition().getY()));
            Point2D.Double target = targetPositions.computeIfAbsent(train, t -> new Point2D.Double(train.getPosition().getX(), train.getPosition().getY()));

            if (!train.getPosition().equals(new Point2D.Double(target.x, target.y))) {
                target.x = train.getPosition().getX();
                target.y = train.getPosition().getY();
            }

            double dx = target.x - current.getX();
            double dy = target.y - current.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 0.01) {
                double step = Math.min(ANIMATION_SPEED / (double) CELL_SIZE, distance);
                current.x += dx / distance * step;
                current.y += dy / distance * step;
            }
        }
        repaint();
    }

    public void updateFieldWidget() {
        if (game.getLevel() != null && game.getLevel().getField() != null) {
            this.fieldWidget = new FieldWidget(game.getLevel().getField());
            animatedPositions.clear();
            selectedTrain = null;
        }
    }

}