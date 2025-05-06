package tile;

import javafx.scene.image.Image;

public class DecorativeTile extends Tile {


	public DecorativeTile(int x, int y) {
        super(x, y);
        this.image = new Image("file:Tower_Game/Tiles/tree1.png");
    }
}