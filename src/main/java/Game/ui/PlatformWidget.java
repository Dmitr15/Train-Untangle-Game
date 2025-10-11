package Game.ui;

import java.awt.*;
import java.awt.geom.Point2D;
import Game.Trains.abstractPlatform;

public class PlatformWidget extends abstractWidget {
    private final abstractPlatform platform;

    public PlatformWidget(abstractPlatform platform) {
        super(platform.getPosition());
        this.platform = platform;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!platform.isActive()) return;

        Point2D pos = platform.getPosition();
        int x = OFFSET + (int) Math.round(pos.getX() * CELL_SIZE);
        int y = OFFSET + (int) Math.round(pos.getY() * CELL_SIZE);

        g2.setColor(new Color(139, 69, 19));
        int platformSize = CELL_SIZE - 6;
        int platformX = x - platformSize / 2;
        int platformY = y - platformSize / 2;
        g2.fillRect(platformX, platformY, platformSize, platformSize);
    }
}