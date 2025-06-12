package tile;

import javafx.scene.image.Image;

public class PreplacedTowerTile extends BuildableTile {
    private final String towerType;

    public PreplacedTowerTile(int x, int y, String towerType) {
        super(x, y);
        this.towerType = towerType;
    }

    public String getTowerType() {
        return towerType;
    }
    public Image getImage() {
        return new Image("file:Tower_Game/Tiles/tower_tile.png"); // fallback visual (optional)
    }

}