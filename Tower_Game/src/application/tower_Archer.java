package application;

import java.util.List;

import javafx.scene.image.Image;



public class tower_Archer extends Tower {
	private static final String ARCHER_TOWER_IMAGE_PATH = "file:Tower_Game/Towers/Tower_archer256.png";
    private static Image towerImage;
    
    public tower_Archer(int x, int y) {
        super(x, y, 100, 50, 2);
    }

    @Override
    public void attack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (range_tower(enemy)) {
                enemy.take_damage(10);
                break;
            }
        }
    }
   
    public Image getImage() {
        return towerImage;
    }
}