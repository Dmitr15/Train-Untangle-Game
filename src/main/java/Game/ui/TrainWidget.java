// Поезд (TrainWidget.java)
package Game.ui;

import Game.Direction;
import Game.Trains.Train;
import Game.Trains.abstractPlatform;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class TrainWidget {
    private static final int CELL_SIZE = 17;
    private static final int OFFSET = 30;
    private final abstractPlatform train;
    private final boolean selected;
    private final Point2D position;

    public TrainWidget(abstractPlatform train, boolean selected, Point2D position) {
        this.train = train;
        this.selected = selected;
        this.position = position;
    }

    public void draw(Graphics2D g2) {
        int x = OFFSET + (int) Math.round(position.getX() * CELL_SIZE);
        int y = OFFSET + (int) Math.round(position.getY() * CELL_SIZE);

        Color trainColor = selected ? new Color(255, 204, 0) : new Color(200, 50, 50);

        g2.setColor(trainColor);
        int trainWidth = CELL_SIZE - 6;
        int trainHeight = CELL_SIZE - 6;
        int trainX = x - trainWidth / 2;
        int trainY = y - trainHeight / 2;
        g2.fillRoundRect(trainX, trainY, trainWidth+4, trainHeight+4, 50, 50);

        drawDirectionArrow(g2, x, y, train.getDirection(), trainColor);
    }

    private void drawDirectionArrow(Graphics2D g2, int x, int y, Direction direction, Color color) {
        double angle = 0;
        switch (direction) {
            case UP: angle = -Math.PI/2; break;
            case DOWN: angle = Math.PI/2; break;
            case LEFT: angle = Math.PI; break;
            case RIGHT: angle = 0; break;
            case RIGHTUP: angle = -Math.PI/4; break;
            case LEFTUP: angle = -3*Math.PI/4; break;
            case DOWNRIGHT: angle = Math.PI/4; break;
            case DOWNLEFT: angle = 3*Math.PI/4; break;
            case UPRIGHT: angle = -Math.PI/4; break;
            case UPLEFT: angle = -3*Math.PI/4; break;
            case LEFTDOWN: angle = 3*Math.PI/4; break;
            case RIGHTDOWN: angle = Math.PI/4; break;
            case BACKWARDDOWNRIGHT: angle = Math.PI/4; break;
            case BACKWARDDOWNLEFT: angle = 3*Math.PI/4; break;
            case RIGHTBACKWARDDOWN: angle = Math.PI/2; break;
            case RIGHTBACKWARDUP: angle = -Math.PI/2; break;
            case LEFTBACKWARDUP: angle = -Math.PI/2; break;
            case LEFTBACKWARDDOWN: angle = Math.PI/2; break;
            default: angle = 0;
        }

        // Создаем трансформацию для поворота и перемещения стрелки
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);     // Перемещаем в позицию поезда
        transform.rotate(angle);       // Поворачиваем на нужный угол
        g2.setColor(Color.WHITE);
        g2.drawString(direction.name(), x + 10, y - 10);
    }
}