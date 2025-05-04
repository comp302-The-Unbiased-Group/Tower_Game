package application;

import java.util.Map;

public class OptionsHandler {

    
    protected static String difficulty;
    
    protected static int waveCount;
    protected static String delay;
    protected static String waveSpeed;
    
    protected static String towerRange;
    protected static int playerDamage;
    
    protected static String composition;
    protected static String movementSpeed;
    
    protected static int earnedGold;
    protected static int startGold;

    public static void process(Map<String, String> selected) {
    	
        for (Map.Entry<String, String> option1 : selected.entrySet()) {
            String key1 = option1.getKey();
            String value = option1.getValue();

            if (key1.equals("Difficulty")) {
                difficulty = value;
                
            } else if (key1.equals("Wave Count")) {
                waveCount = Integer.parseInt(value);
                
            } else if (key1.equals("Delay")) {
                delay = value;
                
            } else if (key1.equals("Wave Speed")) {
                waveSpeed = value;
                
            } else if (key1.equals("Player Hit Damage")) {
                playerDamage = Integer.parseInt(value);
                
            } else if (key1.equals("Composition")) {
                composition = value;
                
            } else if (key1.equals("Tower Range")) {
                towerRange = value;
                
            } else if (key1.equals("Movement Speed")) {
                movementSpeed = value;
                
            } else if (key1.equals("Gold Amount Earned")) {
                earnedGold = Integer.parseInt(value);
                
            } else if (key1.equals("Gold Amount Start")) {
                startGold = Integer.parseInt(value);
                
            } else {
                System.out.println("Unknown option: " + key1);
            }
        }
    }
}

   
