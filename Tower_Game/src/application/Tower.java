package application;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Tower {

	protected Point2D position;
	protected double range;
	protected int damage;
	protected double cd = 0;
	protected ImageView sprite;
	protected double fireRate;
	
	public Tower(Point2D position, double range, int damage, double fireRate, String imagePath) {
		this.position = position;
		this.range = range;
		this.damage = damage;
		this.fireRate = fireRate;

		Image img = new Image(getClass().getResourceAsStream(imagePath));
		sprite = new ImageView(img);
		sprite.setFitWidth(40);
		sprite.setFitHeight(40);
        sprite.setTranslateX(position.getX());
        sprite.setTranslateY(position.getY());
	
	}
	
	
	
	
	
	
	protected void attack(Enemy enemy) {  //Attack enemy -- add animation later
		int trueDamage = damage - enemy.armor;
		enemy.currentHealth -= trueDamage;
	}
		
	
	
	
	
	
	
	
	
	 public ImageView getSprite() {
	        return sprite;
	    }

	    public Point2D getPosition() {
	        return position;
	    }
	
}