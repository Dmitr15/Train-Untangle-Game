// Путь (PathWidget.java)
package Game.ui;

import Game.Train_path;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class PathWidget {
    private static final int CELL_SIZE = 17;
    private static final int OFFSET = 30;
    private final Train_path path;

    public PathWidget(Train_path path) {
        this.path = path;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(100, 100, 100)); // RAIL_COLOR
        g2.setStroke(new BasicStroke(3f));

        List<Point2D> route = path.get_route();
        Point2D prev = route.get(0);
        for (int i = 1; i < route.size(); i++) {
            Point2D current = route.get(i);
            int x1 = OFFSET + (int) (prev.getX() * CELL_SIZE);
            int y1 = OFFSET + (int) (prev.getY() * CELL_SIZE);
            int x2 = OFFSET + (int) (current.getX() * CELL_SIZE);
            int y2 = OFFSET + (int) (current.getY() * CELL_SIZE);
            g2.drawLine(x1, y1, x2, y2);
            prev = current;
        }

        // Отрисовка станций
        g2.setColor(Color.BLACK);
        int stationRadius = 4;
        for (Point2D point : route) {
            int x = OFFSET + (int) (point.getX() * CELL_SIZE) - stationRadius;
            int y = OFFSET + (int) (point.getY() * CELL_SIZE) - stationRadius;
            g2.fillOval(x, y, stationRadius * 2, stationRadius * 2);
        }
    }
}