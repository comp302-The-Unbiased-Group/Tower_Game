package application;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
	
	private TileType type;
	private int row;
	private int column;
	private boolean occupied = false;
	
	
	private static final int TILE_SIZE = 50;
	
	public Tile(TileType type, int column, int row) {
	
	super(TILE_SIZE, TILE_SIZE);
	this.row = row;
	this.column = column;
	setType(type);
	setStroke(javafx.scene.paint.Color.BLACK);
	
	}
	
	
	public void setType(TileType type) {
        this.type = type;
        setFill(getImagePattern(type));
    }
	
	public TileType getType() {
		return type;
	}
	
	public boolean isWalkable() {
		return type == TileType.PATH;
	}
	
	public boolean isBuildable() {
        return type == TileType.TOWER_LOT && !occupied;
    }
	
	public void setOccupied(boolean value) {
        this.occupied = value;
    }
	
	ublic boolean isOccupied() {
        return occupied;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return column;
    }
    
    private ImagePattern getImagePattern(TileType type) {
        String path = switch (type) {
            case GRASS -> "/tiles/grass.png";
            case PATH -> "/tiles/path.png";
            case TOWER_LOT -> "/tiles/lot.png";
            case DECORATION -> "/tiles/deco.png";
        };
        Image img = new Image(getClass().getResourceAsStream(path));
        return new ImagePattern(img);
    }
}
