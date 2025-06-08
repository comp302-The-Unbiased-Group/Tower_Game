package tower;
import enemy.Enemy;
import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;
import java.awt.geom.Point2D;




public class tower_ArtilleryUp extends tower_Artillery {
	
    
   

    public tower_ArtilleryUp(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.4 * tileWidth), 100, 18, 1, gameScreen);  //20% larger range and 20% more damage
        
    }
    
  
  
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/artillery up.png");
    }

}

   

