package Game;

import Game.Trains.Platform;
import Game.Trains.Train;
import Game.event.FieldActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class Field {
    private int width;
    private int height;
    private List<Train_path> paths = new ArrayList<>();
    private List<Platform> platforms = new ArrayList<>();

    public boolean createPlatform(Point2D position) {
        if (isOnPath(position) && isAvailablePosition(position)) {
            platforms.add(new Platform(position));
            return true;
        }
        return false;
    }

    private boolean isOnPath(Point2D position) {
        for (Train_path path : paths) {
            if (path.get_route().contains(position)) {
                return true;
            }
        }
        return false;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public Field(int width, int height){
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");
        this.width = width;
        this.height = height;
    }

    public Field(){
        this.width = 100;
        this.height = 100;
    }

    public List<Train_path> getPaths() {
        return paths;
    }

    public boolean conflictingDirectionsOnTheSamePath(Point2D position, Direction direction){
        for (Train_path path: paths) {
            if (path.get_route().contains(position)) {
                for (Train train: getTrains()){
                    if (path.get_route().contains(train.getPosition())) {
                        if (train.getDirection() == direction.getOppositeDirection()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void move(Train train) {
        train.moveAlongPath(this.paths, this);
    }

    public Train createTrain(Point2D position, Direction direction){
        boolean train_path_contains_position = false;

        for (Train_path path: paths) {
            for (Point2D point_of_path: path.get_route()) {
                if (point_of_path.equals(position)) {
                    train_path_contains_position = true;
                    break;
                }
            }
        }
        if (train_path_contains_position && isAvailablePosition(position) && conflictingDirectionsOnTheSamePath(position, direction)) {
            for (Train_path train_path: paths) {
                if (train_path.get_route().contains(position)) {
                    Train train = new Train(position, direction);
                    train_path.place_train(train);
                    return train;
                }
            }
        }
        return null;
    }

    public List<Train> getTrains() {
        List<Train> trains = new ArrayList<>();
        for (Train_path path: paths){
            trains.addAll(path.getTrains());
        }
        return trains;
    }

    public void setPaths(Train_path paths) {
        this.paths.add(paths);
    }

    public boolean isAvailablePosition(Point2D position){
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= width || position.getY() >= height)
        {
            return false;
        }
        boolean valid = true;
        List<Train> train1 = getTrains();
        for (Train train: train1){
            if (train.getPosition() == position) {
                valid = false;
                break;
            }
        }

        // Проверка платформ
        for (Platform platform : platforms) {
            if (platform.isActive() && platform.getPosition().equals(position)) {
                //return false;
                return true;
            }
        }

        return valid;
    }

    public boolean isAvailablePosition(List<Point2D> position){
        boolean is_available = true;
        for (Point2D point: position) {
            if (this.width <= point.getX() || this.height <= point.getY()) {
                is_available = false;
            }
        }
        return is_available;
    }

    public Train_path createAPath(List<Point2D> route){
        if (isAvailablePosition(route) && route.size() > 1) {
            Train_path path = new Train_path(route);
            setPaths(path);
            return path;
        }
        return null;
    }

    public void deleteTrain(Train train){
        if (!this.getTrains().isEmpty()) {
            for (Train_path path : paths) {
                if (path.getTrains().contains(train)) {
                    path.removeTrain(train);
                }
            }
        }
    }

    // -------------------- События --------------------

    private ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    public void addFieldActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    public void removeFieldCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
