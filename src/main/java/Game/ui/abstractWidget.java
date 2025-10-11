package Game.ui;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class abstractWidget {
    protected static final int CELL_SIZE = 17;
    protected static final int OFFSET = 30;
    protected Point2D position;

    public abstract void draw(Graphics2D g2);

    public abstractWidget(Point2D position) {
        this.position = position;
    }

    protected Point2D currentPos(){
        int x = OFFSET + (int) Math.round(position.getX() * CELL_SIZE);
        int y = OFFSET + (int) Math.round(position.getY() * CELL_SIZE);

        return new Point2D.Double(x, y);
    }
}
