package application;

import javafx.geometry.Point2D;
import java.util.List;

public class enemy_Goblin extends Enemy {
    public enemy_Goblin(List<Point2D> path) {
        super(60, 0, 100, "/Enemies/Goblin_Red.png", path);
    }

    @Override
    public String getType() {
        return "Goblin";
    }
}