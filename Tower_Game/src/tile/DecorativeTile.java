package tile;

import javafx.scene.image.Image;

public class DecorativeTile extends Tile {
	
	private String decor_type;
	public DecorativeTile(int x, int y,String decor_type) {
		
        super(x, y);
        this.decor_type = decor_type;
       
    
    }
	
	@Override
    public Image getImage() {
        switch (decor_type) {
            case "mushroom":
                return new Image("file:Tower_Game/decorations/03-removebg-preview.png");
            case "gold_mine":
                return new Image("file:Tower_Game/decorations/GoldMine_Active-removebg-preview.png");
            case "castle":
                return new Image("file:Tower_Game/Towers/Castle128.png");
            case "tree":
                return new Image("file:Tower_Game/decorations/tileset__4_2_-removebg-preview.png");
            case "skelaton":
                return new Image("file:Tower_Game/decorations/16-removebg-preview.png");
                
                
                
                
            default:
                return null;
        }
}
}