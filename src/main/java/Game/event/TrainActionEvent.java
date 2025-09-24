package Game.event;

import Game.Trains.Train;
import java.util.EventObject;

public class TrainActionEvent extends EventObject{
    public TrainActionEvent(Object source) {
        super(source);
    }


    private Train train;

    public void setTrain(Train train) {
        this.train = train;
    }

    public Train getTrain() {
        return train;
    }
}