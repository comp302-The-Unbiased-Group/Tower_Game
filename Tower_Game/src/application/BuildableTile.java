package application;

import javafx.scene.image.Image;

public class BuildableTile extends Tile {
	
    public BuildableTile(int x, int y) {
        super(x, y);
        this.image = new Image("file:Tower_Game/Tiles/tower_tile.png");
    }
}
