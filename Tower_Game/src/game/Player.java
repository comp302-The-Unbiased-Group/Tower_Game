package game;
import utils.OptionsHandler;

public class Player {
	private int gold;
    private int lives;
    private int maxlives;

    public Player(int gold, int lives) {
        this.gold = gold;
        this.lives = lives;
    }

    public boolean can_afford(int cost) {
        return gold >= cost;
    }

    public void add_gold(int amount) {
        gold += amount;
    }

    public void add_lives(int amount) {
        lives += amount;
    }

    public void reduce_life() {
        lives = Math.max(0, lives - OptionsHandler.getInstance().getPlayerDamage());
    }


    public int get_lives() {
        return lives;
    }

    public int get_gold() {
        return gold;
    }
    
public int get_maxlives() {
        return maxlives;
}

}