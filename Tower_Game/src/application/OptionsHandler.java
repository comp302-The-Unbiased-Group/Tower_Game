package application;

import java.util.Map;

public class OptionsHandler {

    // Singleton instance
    private static OptionsHandler instance;

    // Default values
    protected String difficulty;
    protected int waveCount = 3;
    protected String delay;
    protected String waveSpeed;
    protected String towerRange;
    protected int playerDamage;
    protected String composition;
    protected String movementSpeed;
    protected int earnedGold;
    protected int startGold;

    // Private constructor
    private OptionsHandler() {}

    // Get the singleton instance
    public static OptionsHandler getInstance() {
        if (instance == null) {
            instance = new OptionsHandler();
        }
        return instance;
    }

    // Process selected options
    public void process(Map<String, String> selected) {
        for (Map.Entry<String, String> option1 : selected.entrySet()) {
            String key1 = option1.getKey();
            String value = option1.getValue();

            if (key1.equals("Difficulty")) {
                this.difficulty = value;
            } else if (key1.equals("Wave Count")) {
                this.waveCount = Integer.parseInt(value);
            } else if (key1.equals("Delay")) {
                this.delay = value;
            } else if (key1.equals("Wave Speed")) {
                this.waveSpeed = value;
            } else if (key1.equals("Tower Range")) {
                this.towerRange = value;
            } else if (key1.equals("Player Hit Damage")) {
                this.playerDamage = Integer.parseInt(value);
            } else if (key1.equals("Composition")) {
                this.composition = value;
            } else if (key1.equals("Movement Speed")) {
                this.movementSpeed = value;
            } else if (key1.equals("Gold Amount Earned")) {
                this.earnedGold = Integer.parseInt(value);
            } else if (key1.equals("Gold Amount Start")) {
                this.startGold = Integer.parseInt(value);
            }
        }
    }


    public String getDifficulty() {
        return difficulty;
    }

    public int getWaveCount() {
        return waveCount;
    }

    public String getDelay() {
        return delay;
    }

    public String getWaveSpeed() {
        return waveSpeed;
    }

    public String getTowerRange() {
        return towerRange;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public String getComposition() {
        return composition;
    }

    public String getMovementSpeed() {
        return movementSpeed;
    }

    public int getEarnedGold() {
        return earnedGold;
    }

    public int getStartGold() {
        return startGold;
    }
}
   
