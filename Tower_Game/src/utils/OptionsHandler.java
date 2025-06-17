package utils;

import java.util.Map;

public class OptionsHandler {


    private static OptionsHandler instance;

    // Default values
   
    protected int waveCount = 8;
    protected String delay = "4s";
    protected int PlayerHP = 20;
    protected String towerRange = "Normal";
    protected int playerDamage = 1;
    protected int startGold = 100;
    protected double goblinSpeed = 3.5;
    protected double knightSpeed = 2.5;
    protected double tntSpeed = 1.5;
    protected double barrelSpeed = 2.0;
    protected double healerSpeed = 2.3;
    protected String movementSpeed = "Normal";
    protected double speedmultiplier=1;
    private static final String PROPS_FILE = "options.properties";


    private OptionsHandler() {}

    // Get the singleton instance
    public static OptionsHandler getInstance() {

        if (instance == null) {
            instance = new OptionsHandler();
            instance.loadFromFile(); 
        }
        return instance;
    }

    // Process selected options
    public void process(Map<String, String> selected) {
        for (Map.Entry<String, String> option1 : selected.entrySet()) {
            String key1 = option1.getKey();
            String value = option1.getValue();

            if (key1.equals("Wave Count")) {

                this.waveCount = Integer.parseInt(value);
            } else if (key1.equals("Delay between waves")) {

                this.delay = value;
            }  else if (key1.equals("Tower Range")) {

                this.towerRange = value;
            }
            else if (key1.equals("Player Hit Point")) {

                this.PlayerHP= Integer.parseInt(value);
            }else if (key1.equals("Player Hit Damage")) {

                this.playerDamage = Integer.parseInt(value);
            } 
            
            else if (key1.equals("Movement Speed")) {
                this.movementSpeed = value;
                switch (value) {
                    case "Low":
                        this.speedmultiplier = 0.7;
                        break;
                    case "Normal":
                        this.speedmultiplier = 1.0;
                        break;
                    case "Fast":
                        this.speedmultiplier = 1.5;
                        break;
                    default:
                        this.speedmultiplier = 1.0;
                }
            }
        
            else if (key1.equals("Gold Amount Start")) {
                this.startGold = Integer.parseInt(value);
            }
        }
        saveToFile();
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

    

    public String getMovementSpeed() {
        return movementSpeed;
    }

    

    public int getStartGold() {
        return startGold;
    }
    public int getPlayerHP() {
        return PlayerHP;
    }

    

    public double getspeedmultiplier() {
        return speedmultiplier;
    }
    
    public double getGoblinSpeed() {
    	return goblinSpeed; 
    	}
    public double getKnightSpeed() { 
    	return knightSpeed; 
    	}
    public double getTntSpeed() { 
    	return tntSpeed; 
    	}
    public double getBarrelSpeed() { 
    	return barrelSpeed;
    	}
    public double getHealerSpeed() {
    	return healerSpeed; 
    }
    
    public long getWaveDelayMs() {
        
        switch (delay) {
            case "No Delay": return 0;
            case "Normal(4s)": return 4000;
            case "Long(10s)": return 10000;
            default:
                if (delay.endsWith("s")) {
                    try {
                        return Long.parseLong(delay.replaceAll("\\D+", "")) * 1000;
                    } catch (Exception e) {
                        return 4000; 
                    }
                }
                return 4000;
        }
    }
    
    public void resetToDefaults() {
        waveCount = 8;
        delay = "Normal(4s)";
        PlayerHP = 25;
        towerRange = "Normal";
        playerDamage = 1;
        startGold = 100;
        movementSpeed = "Normal";
        speedmultiplier = 1.0;
        saveToFile();
    }
    
    public void saveToFile() {
        try (java.io.FileOutputStream out = new java.io.FileOutputStream(PROPS_FILE)) {
            java.util.Properties props = new java.util.Properties();
            props.setProperty("waveCount", String.valueOf(waveCount));
            props.setProperty("delay", delay);
            props.setProperty("PlayerHP", String.valueOf(PlayerHP));
            props.setProperty("towerRange", towerRange);
            props.setProperty("playerDamage", String.valueOf(playerDamage));
            props.setProperty("startGold", String.valueOf(startGold));
            props.setProperty("movementSpeed", movementSpeed);
            props.store(out, "Game Options");
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void loadFromFile() {
        java.io.File f = new java.io.File(PROPS_FILE);
        if (!f.exists()) return;
        try (java.io.FileInputStream in = new java.io.FileInputStream(f)) {
            java.util.Properties props = new java.util.Properties();
            props.load(in);
            waveCount = Integer.parseInt(props.getProperty("waveCount", "8"));
            delay = props.getProperty("delay", "Normal(4s)");
            PlayerHP = Integer.parseInt(props.getProperty("PlayerHP", "25"));
            towerRange = props.getProperty("towerRange", "Normal");
            playerDamage = Integer.parseInt(props.getProperty("playerDamage", "1"));
            startGold = Integer.parseInt(props.getProperty("startGold", "100"));
            movementSpeed = props.getProperty("movementSpeed", "Normal");
            speedmultiplier = switch (movementSpeed) {
            	case "Low" -> 0.7;
            	case "Fast" -> 1.5;
            default -> 1.0;
        };
        } catch (Exception e) { e.printStackTrace(); }
    }

}
   
