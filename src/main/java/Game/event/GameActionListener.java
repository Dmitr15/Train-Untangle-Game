package Game.event;

import java.util.EventListener;

public interface GameActionListener extends EventListener {

    void trainIsRemoved(GameActionEvent event);
    void gameStatusChanged(GameActionEvent event);
}
