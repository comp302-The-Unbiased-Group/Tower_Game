package utils;

import java.util.Map;

public class OptionsHandler {


    private static OptionsHandler instance;

    // Default values
    protected String difficulty;
    protected int waveCount = 3;
    protected String delay = "2s";
    protected int PlayerHP = 20;
    protected String towerRange;
    protected int playerDamage = 1;
    protected String Enemy_composition = "Mixed";
    protected String EnemySpeed;
    protected int earnedGold= 10;
    protected int startGold = 100;


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
            }  else if (key1.equals("Tower Range")) {

                this.towerRange = value;
            }
            else if (key1.equals("Player Lives")) {

                this.PlayerHP= Integer.parseInt(value);
            }else if (key1.equals("Player Hit Damage")) {

                this.playerDamage = Integer.parseInt(value);
            } else if (key1.equals("Composition")) {

                this.Enemy_composition = value;
            } else if (key1.equals("Movement Speed")) {

                this.EnemySpeed = value;
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



    public String getTowerRange() {
        return towerRange;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public String getComposition() {
        return Enemy_composition;
    }

    public String getMovementSpeed() {
        return EnemySpeed;
    }

    public int getEarnedGold() {
        return earnedGold;
    }

    public int getStartGold() {
        return startGold;
    }
    public int getPlayerHP() {
        return PlayerHP;
    }
}
   
