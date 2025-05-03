package application;




import java.awt.Point;
import java.util.List;

import javafx.scene.image.Image;

public class enemy_Goblin extends Enemy {
	private static final String IMAGE_PATH = "file:Tower_Game/Enemies/goblin_single.png";
    private static Image image;
	
    public enemy_Goblin(List<Point> path) {
        super(path, 30, 2.5, 5);
        if (image == null) {
            image = new Image(IMAGE_PATH);
        }
    }

    public Image getImage() {
        return image;
    }
}
