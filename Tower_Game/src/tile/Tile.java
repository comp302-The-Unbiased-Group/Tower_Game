package tile;

import javafx.scene.image.Image;

public abstract class Tile {
	
    protected int x, y;
    protected Image image;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Image getImage() {
        return image;
    }
    
    public int getX() { 
    	return x; 
    }
    public int getY() { 
    	return y; 
    }


}
