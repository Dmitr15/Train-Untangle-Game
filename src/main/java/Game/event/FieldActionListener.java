package Game.event;

import java.util.EventListener;

public interface FieldActionListener extends EventListener {
    void allTrainsAreRemoved(FieldActionEvent event);
}
