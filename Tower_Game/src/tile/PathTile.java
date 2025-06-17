package tile;

import javafx.scene.image.Image;

public class PathTile extends Tile {
		
	public enum PathType {
        HORIZONTAL, VERTICAL,
        CORNER_TOPLEFT, CORNER_TOPRIGHT,
        CORNER_BOTTOMLEFT, CORNER_BOTTOMRIGHT
    }
	
	private PathType type;
	
	public PathTile(int x, int y, PathType type) {
        super(x, y);
        this.type = type;
    }
	
	@Override
    public Image getImage() {
        switch (type) {
            case HORIZONTAL:
                return new Image("file:Tower_Game/Tiles/horizontal_path.png");
            case VERTICAL:
                return new Image("file:Tower_Game/Tiles/vertical_path.png");
            case CORNER_TOPLEFT:
                return new Image("file:Tower_Game/Tiles/bottom_right.png");
            case CORNER_TOPRIGHT:
                return new Image("file:Tower_Game/Tiles/bottom_left.png");
            case CORNER_BOTTOMLEFT:
                return new Image("file:Tower_Game/Tiles/top_right.png");
            case CORNER_BOTTOMRIGHT:
                return new Image("file:Tower_Game/Tiles/top_left.png");
            default:
                return null;
        }
    }
}
