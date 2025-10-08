package Game;

import Game.Trains.Platform;
import Game.Trains.Train;
import Game.Trains.abstractPlatform;
import Game.event.FieldActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Field {
    private int width;
    private int height;
    private abstractPlatform platform;
    private List<Point2D> paths = new ArrayList<>();
    private List<abstractPlatform> trains = new ArrayList<>();

    //для проверки 1
    public boolean ifPositionFree(Point2D position, Train current_train){
        for (abstractPlatform train : trains) {
            if (train != current_train) {
                if (train.getPosition().equals(position)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean ifPlatformExists(){
        return platform != null;
    }

    public boolean ifPositionFree(Point2D position){
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= width || position.getY() >= height)
        {
            return false;
        }
        for (abstractPlatform train : trains) {
                if (train.getPosition().equals(position)) {
                    return false;
                }
        }
        return true;
    }

    public boolean isNextOtherTrain(Point2D position){
        for (abstractPlatform train : trains) {
            if (train.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    //для проверки 2
    public boolean isPlatformOnPosition(Point2D position){
        return platform.getPosition().equals(position);
    }

    public void deactivatePlatform(){
        this.platform.deactivate();
    }

    public void createPlatform(Point2D position) {
        if (isOnPath(position) && isAvailablePosition(position)) {
            platform = new Platform(position, this.paths);
        }
    }

    private boolean isOnPath(Point2D position) {
        return paths.contains(position);
    }

    public abstractPlatform getPlatforms() {
        return platform;
    }

    public void movePlatform(Direction direction){
        platform.moveAlong(this, direction);
    }

    public Field(int width, int height){
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");
        this.width = width;
        this.height = height;
    }

    public List<Point2D> getPaths() {
        return paths;
    }

    public boolean conflictingDirectionsOnTheSamePath(Point2D position, Direction direction){
            if (this.paths.contains(position)) {
                for (abstractPlatform train: getTrains()){
                    if (this.paths.contains(train.getPosition())) {
                        if (train.getDirection() == direction.getOppositeDirection()) {
                            return false;
                        }
                    }
                }
            }
        return true;
    }

    public void move(abstractPlatform train) {
        train.moveAlongPath(this);
    }

    public Train createTrain(Point2D position, Direction direction){
        boolean train_path_contains_position = false;
            for (Point2D point_of_path: paths) {
                if (point_of_path.equals(position)) {
                    train_path_contains_position = true;
                    break;
                }
            }
        if (train_path_contains_position && isAvailablePosition(position) && conflictingDirectionsOnTheSamePath(position, direction)) {
                if (paths.contains(position)) {
                    Train train = new Train(position, direction, this.paths);
                    this.trains.add(train);
                    return train;
                }
        }
        return null;
    }

    public List<Train > getTrains() {
       // return this.trains;
        List<Train> trainList = new ArrayList<>();
        for (abstractPlatform platform : this.trains) {
            if (platform instanceof Train) {
                trainList.add((Train) platform);
            }
        }
        return trainList;
    }

    public void setPaths(Point2D paths) {
        this.paths.add(paths);
    }

    public boolean isAvailablePosition(Point2D position){
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= width || position.getY() >= height)
        {
            return false;
        }
        boolean valid = true;
        for (abstractPlatform train: trains){
            if (train.getPosition() == position) {
                valid = false;
                break;
            }
        }

        // Проверка платформ
        if (platform != null) {
            if (platform.isActive() && platform.getPosition().equals(position)) {
                return true;
            }
        }
        return valid;
    }

    private boolean isAvailablePosition(List<Point2D> position){
        boolean is_available = true;
        for (Point2D point: position) {
            if (this.width <= point.getX() || this.height <= point.getY()) {
                is_available = false;
            }
        }
        return is_available;
    }

    public void createAPath(List<Point2D> route){
        if (isAvailablePosition(route) && route.size() > 1 && isCorrectRout(route)) {
            paths.addAll(route);
        }
    }

    private boolean isCorrectRout(List<Point2D> route){
        if (route == null || route.size() < 2) return false;

        for (int i = 1; i < route.size(); i++) {
            Point2D prev = route.get(i-1);
            Point2D current = route.get(i);

            double dx = Math.abs(prev.getX() - current.getX());
            double dy = Math.abs(prev.getY() - current.getY());

            // Допустимы только горизонтальные, вертикальные и диагональные движения
            if ((dx > 0 && dy > 0 && dx != dy) || (dx == 0 && dy == 0)) {
                return false;
            }
        }
        return true;
    }

    public void deleteTrain(Train train){
        if (!this.trains.isEmpty()) {
            this.trains.remove(train);
        }
    }

    // -------------------- События --------------------

    private ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    public void addFieldActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
