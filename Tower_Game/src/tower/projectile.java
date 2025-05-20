package tower;

import enemy.Enemy;
import java.awt.geom.Point2D;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class projectile {

    private Point2D.Double position;
    private Enemy target;
    private double speed;
    private int damage;
    private Image image;
    private boolean isHit = false;
    private String type;
    private List<Enemy> enemies; 
    private Point2D.Double impactPosition; 

    public projectile(Point2D.Double start, Enemy target, double speed, int damage, Image image, String type, List<Enemy> enemies) {
    	
        this.position = new Point2D.Double(start.x, start.y);
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.image = image;
        this.type = type;
        this.enemies = enemies;
    }

    public void update() {
    	
        if (target == null || !target.is_alive()) {
            isHit = true;
            return;
        }

        Point2D.Double targetPos = target.getPosition();
        double dx = targetPos.x - position.x;
        double dy = targetPos.y - position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
           
            impactPosition = new Point2D.Double(position.x, position.y);
            onHit();
            isHit = true;
            return;
        }

        double dirX = dx / distance;
        double dirY = dy / distance;
        position.x += dirX * speed;
        position.y += dirY * speed;
    }

    public void onHit() {
    	
        if (type.equals("archer")) {
            target.take_damage(damage);
            
        } else if (type.equals("mage")) {
            target.take_damage(damage);
            target.setSpeed(target.getSpeed() * 0.8);
            
        } else if (type.equals("artillery")) {
        	
            if (impactPosition == null) {
                impactPosition = new Point2D.Double(position.x, position.y);
            }
            for (Enemy e : enemies) {
            	
                if (e.is_alive() && impactPosition.distance(e.getPosition()) < 50) {
                    e.take_damage(damage);
                }
            }
        }
    }

    public void draw(GraphicsContext g) {
        g.drawImage(image, position.x, position.y, 48, 48);
    }

    public boolean hasHit() {
        return isHit;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public Image getImage() {
        return image;
    }
}
