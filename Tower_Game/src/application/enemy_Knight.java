package application;
import java.util.List;
import javafx.geometry.Point2D;

public class enemy_Knight extends Enemy {
    public enemy_Knight(List<Point2D> path) {
        super(120, 10, 60, "/tiles/knight.png", path);
    }

    @Override
    public String getType() {
        return "Knight";
    }
}
