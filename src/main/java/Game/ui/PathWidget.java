package Game.ui;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class PathWidget {
    private static final int CELL_SIZE = 17;
    private static final int OFFSET = 30;
    private final List<Point2D> path;

    public PathWidget(List<Point2D> path) {
        this.path = path;
    }

    public void draw(Graphics2D g2) {
        if (path == null || path.size() < 2) return;

        g2.setColor(new Color(100, 100, 100)); // RAIL_COLOR
        g2.setStroke(new BasicStroke(3f));

        for (int i = 0; i < path.size() - 1; i++) {
            Point2D current = path.get(i);
            Point2D next = path.get(i + 1);

            // Проверяем, что точки соседние по горизонтали или вертикали
            if (areAdjacentPoints(current, next)) {
                int x1 = OFFSET + (int) (current.getX() * CELL_SIZE);
                int y1 = OFFSET + (int) (current.getY() * CELL_SIZE);
                int x2 = OFFSET + (int) (next.getX() * CELL_SIZE);
                int y2 = OFFSET + (int) (next.getY() * CELL_SIZE);
                g2.drawLine(x1, y1, x2, y2);
            }
        }

        // Отрисовка станций
        g2.setColor(Color.BLACK);
        int stationRadius = 4;
        for (Point2D point : path) {
            int x = OFFSET + (int) (point.getX() * CELL_SIZE) - stationRadius;
            int y = OFFSET + (int) (point.getY() * CELL_SIZE) - stationRadius;
            g2.fillOval(x, y, stationRadius * 2, stationRadius * 2);
        }
    }

    private boolean areAdjacentPoints(Point2D p1, Point2D p2) {
        double dx = Math.abs(p1.getX() - p2.getX());
        double dy = Math.abs(p1.getY() - p2.getY());

        return (dx == 10.0 && dy == 0.0) || (dy == 10.0 && dx == 0.0);
    }
}