package Game.event;

import Game.Trains.Train;
import java.util.EventObject;

public class GameActionEvent extends EventObject {

    public GameActionEvent(Object source) {
        super(source);
    }

    private Train train;

    public void setTrain( Train train) {
        this.train = train;
    }

    public Train getTrain() {
        return train;
    }

}
