package application;

import javafx.geometry.Point2D;
import java.util.List;

public class enemy_Knight extends Enemy {
    public enemy_Knight(List<Point2D> path) {
        super(120, 10, 60, "/Enemies/Warrior_Blue.png", path);
    }

    @Override
    public String getType() {
        return "Knight";
    }
}
