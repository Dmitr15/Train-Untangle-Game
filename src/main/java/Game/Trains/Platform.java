package Game.Trains;

import java.awt.geom.Point2D;

public class Platform {
    private Point2D position;
    private boolean active = true;

    public Platform(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}