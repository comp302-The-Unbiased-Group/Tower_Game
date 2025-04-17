package application;
import java.util.List;
import javafx.geometry.Point2D;

public class enemy_Goblin extends Enemy {
	
	  public enemy_Goblin(List<Point2D> path) {
	        super(60, 0, 100, "/tiles/goblin.png", path); // hp, armor, speed, path
	    }
	  
	  @Override
	    public String getType() {
	        return "Goblin";
	    }

