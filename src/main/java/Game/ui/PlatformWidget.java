package Game.ui;

import java.awt.*;
import java.awt.geom.Point2D;
import Game.Trains.abstractPlatform;

public class PlatformWidget extends abstractWidget {
    private final abstractPlatform platform;
    private final Color color;

    public PlatformWidget(abstractPlatform platform, Color color) {
        super(platform.getPosition());
        this.platform = platform;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!platform.isActive()) return;

        Point2D pos = platform.getPosition();
        int x = OFFSET + (int) Math.round(pos.getX() * CELL_SIZE);
        int y = OFFSET + (int) Math.round(pos.getY() * CELL_SIZE);
        g2.setColor(this.color);
        int platformSize = CELL_SIZE - 6;
        int platformX = x - platformSize / 2;
        int platformY = y - platformSize / 2;
        g2.fillRect(platformX, platformY, platformSize, platformSize);
    }
}