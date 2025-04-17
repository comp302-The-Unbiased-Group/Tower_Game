package application;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;


public abstract class Enemy {
	
	protected int armor;
	protected double speed;
	protected int maxHealth;
	protected int currentHealth;
    protected ImageView sprite;
	protected List<Point2D> path;
	protected int currentTargetIndex = 0; //path index
    protected Point2D position; 

    public Enemy(int hp, int armor, double speed, String imagePath, List<Point2D> path) {
    	
        this.maxHealth = hp;
        this.currentHealth = hp;
        this.armor = armor;
        this.speed = speed;
        this.path = path;
        this.position = path.get(0);

        Image img = new Image(getClass().getResourceAsStream(imagePath));
        this.sprite = new ImageView(img);
        this.sprite.setFitWidth(40);
        this.sprite.setFitHeight(40);
        updateSpritePosition();
    }
    
    public void update(double deltaTime) {
    	
        if (hasReachedEnd()) return;

        Point2D target = path.get(currentTargetIndex);
        Point2D direction = target.subtract(position);

        
        if (direction.magnitude() < 1) {
          
            currentTargetIndex++;
            if (!hasReachedEnd()) {
                target = path.get(currentTargetIndex);
                direction = target.subtract(position);
            } else {
                onReachEnd();
                return;
            }
            
        }
        if (direction.magnitude() > 0) { //normalize direction
        	
            Point2D velocity = direction.normalize().multiply(speed * deltaTime);
            position = position.add(velocity);
            updateSpritePosition();
        }
      }

    
    private void updateSpritePosition() {
    	
        sprite.setTranslateX(position.getX());
        sprite.setTranslateY(position.getY());
    }
    
    public boolean isDead() {
        return currentHealth <= 0;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public Point2D getPosition() {
        return position;
    }

    public abstract String getType();
    
    public boolean hasReachedEnd() { //CHECK IF REACHED END
		return currentTargetIndex >= path.size();
	}
    
    public double getHealthBar() { //UI HEALTH BAR
        return (double) currentHealth / maxHealth;
    }
    
    public boolean isInRange(Point2D towerPos, double range) { //CHECK IF IN RANGE
    	
        return towerPos.distance(position) <= range;
    }
    
    public void onDeath() {
    	
    	//enemy is dead 
    	//work in progress
    }
	 
    public void onReachEnd() {
    	
    	//enemy reached end
		//work in progress
    }
} 