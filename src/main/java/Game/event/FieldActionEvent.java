package Game.event;
import Game.Trains.Train;

import java.util.EventObject;

public class FieldActionEvent extends EventObject {

    private Train train;

    public FieldActionEvent(Object source) {
        super(source);
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Train getTrain() {
        return train;
    }

}
