package application;

public class Player {
	private int gold;
    private int lives;

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

    public void reduce_life() {
        lives--;
    }

    public int get_lives() {
        return lives;
    }

    public int get_gold() {
        return gold;
    }
    


}