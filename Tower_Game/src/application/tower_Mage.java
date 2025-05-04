package application;

import java.util.List;

import javafx.scene.image.Image;



public class tower_Mage extends Tower {
	private static final String MAGE_TOWER_IMAGE_PATH = "file:Tower_Game/Towers/Tower_spell256.png";
    private static Image towerImage;
	
    public tower_Mage(int x, int y) {
        super(x, y, 80, 60, 3);
    }

    @Override
    public void attack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (range_tower(enemy)) {
                enemy.take_damage(20);
                break;
            }
        }
    }
    
    public Image getImage() {
        return towerImage;
    }
}