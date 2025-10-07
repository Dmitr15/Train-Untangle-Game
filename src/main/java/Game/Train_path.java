//package Game;
//
//import Game.Trains.Train;
//import java.awt.geom.Point2D;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class Train_path {
//    private List<Point2D> route = new ArrayList<>();
//    private List<Train> trains = new ArrayList<>();
//
//    public List<Point2D> get_route(){
//        return route;
//    }
//
//    public Train_path(List<Point2D> route) {
//        if (isCorrectRout(route)) {
//            this.route = route;
//        }
//        else {
//            this.route = null;
//        }
//    }
//
//    private boolean isCorrectRout(List<Point2D> route){
//        if (route == null || route.size() < 2) return false;
//
//        for (int i = 1; i < route.size(); i++) {
//            Point2D prev = route.get(i-1);
//            Point2D current = route.get(i);
//
//            double dx = Math.abs(prev.getX() - current.getX());
//            double dy = Math.abs(prev.getY() - current.getY());
//
//            // Допустимы только горизонтальные, вертикальные и диагональные движения
//            if ((dx > 0 && dy > 0 && dx != dy) || (dx == 0 && dy == 0)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void removeTrain(Train train){
//        trains.remove(train);
//    }
//
//    public boolean place_train(Train train){
//        this.trains.add(train);
//        return false;
//    }
//
//    public Collection<? extends Train> getTrains() {
//        return (Collection<? extends Train>) trains;
//    }
//
//    public List<Point2D> getPositionOfTrains(Train current_Train){
//        List<Point2D> positions = new ArrayList<>();
//        for (Train train: trains) {
//            if (train != current_Train) {
//                positions.add(train.getPosition());
//            }
//        }
//        return positions;
//    }
//
//    public Point2D get_point(Point2D point) {
//        for (Point2D p : route) {
//            if (p.getX() == point.getX() && p.getY() == point.getY()) {
//                return p;
//            }
//        }
//        return null;
//    }
//}
