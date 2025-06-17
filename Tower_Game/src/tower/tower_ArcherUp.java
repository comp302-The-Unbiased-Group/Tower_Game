package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;

import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_ArcherUp extends tower_Archer {

   
	
    public tower_ArcherUp(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, Tower.calculateRange((int)(3.6 * tileWidth)), 100, 7, 2.4, gameScreen);
    	
      
        this.cost = 100;
    }
    

    
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/archer up.png"); 
    }
}