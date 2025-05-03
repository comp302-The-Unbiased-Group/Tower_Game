package application;




import java.awt.Point;
import java.util.List;

import javafx.scene.image.Image;

public class enemy_Knight extends Enemy {
	private static final String IMAGE_PATH = "file:Tower_Game/Enemies/knight_single.png";
    private static Image image;
	
    public enemy_Knight(List<Point> path) {
        super(path, 60, 1.5, 10);
        if (image == null) {
            image = new Image(IMAGE_PATH);
        }
    }

    public Image getImage() {
        return image;
    }

}