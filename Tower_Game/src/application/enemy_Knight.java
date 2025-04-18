package application;




import java.awt.Point;
import java.util.List;

public class enemy_Knight extends Enemy {
	
    public enemy_Knight(List<Point> path) {
        super(path, 60, 1.5, 10);
    }

}