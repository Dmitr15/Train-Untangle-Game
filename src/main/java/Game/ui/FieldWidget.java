package Game.ui;

import Game.Field;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import Game.Trains.Carriage;
import Game.Trains.abstractPlatform;

public class FieldWidget {
    private final Field field;
    private final List<PathWidget> pathWidgets;
    private final List<PlatformWidget> platformWidgets;
    private List<TrainWidget> trainWidgets;

    public FieldWidget(Field field) {
        this.field = field;
        // Инициализация виджетов путей
        this.pathWidgets = new ArrayList<>();
        pathWidgets.add(new PathWidget(this.field.getPaths()));
        // Инициализация виджетов платформ
        this.platformWidgets = new ArrayList<>();
        if (this.field.getPlatforms() != null){
            if (this.field.getPlatforms() instanceof Carriage) {
                platformWidgets.add(new PlatformWidget(this.field.getPlatforms(), new Color(87, 10, 170)));
            }else {
                platformWidgets.add(new PlatformWidget(this.field.getPlatforms(), new Color(139, 69, 19)));
            }
        }
        // Инициализация виджетов поездов
        this.trainWidgets = new ArrayList<>();
        updateTrainWidgets(new HashMap<>(), null);
    }

    public void updateTrainWidgets(Map<abstractPlatform, Point2D> animatedPositions, abstractPlatform selectedTrain) {
        if (field == null) return;

        // Очищаем текущий список виджетов поездов
        this.trainWidgets.clear();

        // Для каждого поезда создаем виджет
        for (abstractPlatform train : field.getTrains()) {
            // Получаем позицию для анимации или текущую позицию поезда
            Point2D position = animatedPositions.getOrDefault(train, train.getPosition());

            // Создаем виджет поезда
            TrainWidget widget = new TrainWidget(train, train == selectedTrain, position);

            // Добавляем виджет в список
            this.trainWidgets.add(widget);
        }
    }

    public void draw(Graphics2D g2) {
        // Отрисовка фона
        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, 0, 1000, 1000);

        if (field == null) return;

        // Отрисовка путей
        if (pathWidgets != null) {
            for (PathWidget pathWidget : pathWidgets) {
                pathWidget.draw(g2);
            }
        }

        // Отрисовка платформ
        if (platformWidgets != null) {
            for (PlatformWidget platformWidget : platformWidgets) {
                platformWidget.draw(g2);
            }
        }

        // Отрисовка поездов
        if (trainWidgets != null) {
            for (TrainWidget trainWidget : trainWidgets) {
                trainWidget.draw(g2);
            }
        }

        // Отрисовка координатной сетки
        g2.setColor(new Color(200, 200, 200, 100));
        g2.setStroke(new BasicStroke(1f));
        int width = field.getWidth();
        int height = field.getHeight();

        for (int x = 0; x <= width; x += 10) {
            int xPos = 30 + x * 17;
            g2.drawLine(xPos, 30, xPos, 30 + height * 17);
        }

        for (int y = 0; y <= height; y += 10) {
            int yPos = 30 + y * 17;
            g2.drawLine(30, yPos, 30 + width * 17, yPos);
        }
    }
}