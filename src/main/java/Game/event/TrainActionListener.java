package Game.event;
import java.util.EventListener;
public interface TrainActionListener extends EventListener{
    void trainIsTeleported(TrainActionEvent event);
}
