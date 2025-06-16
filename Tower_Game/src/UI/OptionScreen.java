package UI;
import utils.OptionsHandler;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;



public class OptionScreen {

    private final Map<String, ComboBox<String>> select = new HashMap<>();
    private Button save;
    private Button resetButton;

    public Scene newScene(Stage stage) {
    	
    	OptionsHandler.getInstance().loadFromFile();
    	 
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        StackPane root = new StackPane();

        Image banner = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage bg = new BackgroundImage(
                banner,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(screenWidth, screenHeight, false, false, false, false)
        );
        root.setBackground(new Background(bg));

        // overlay filter
        Rectangle overlay = new Rectangle(screenWidth, screenHeight);
        overlay.setFill(new Color(0, 0, 0, 0.45));

        VBox content = new VBox(15);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);
        content.setMaxWidth(450);
        content.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.7);" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2;"
        );

        // settings
        createOption(content, "Tower Range", Arrays.asList("Short", "Normal", "Long"));
        createOption(content, "Enemy Speed", Arrays.asList("Low", "Normal", "Fast"));
        createOption(content, "Wave Count", Arrays.asList("5", "8", "11"));
        createOption(content, "Delay Between Waves", Arrays.asList("No Delay", "Normal(4s)", "Long(10s)"));
        createOption(content, "Player Hit Point", Arrays.asList("10", "25", "50"));
        createOption(content, "Enemy Hit Damage", Arrays.asList("1", "2", "3"));
        createOption(content, "Gold Amount Start", Arrays.asList("50", "100", "200"));
        
        // save settings
        save = new Button("SAVE");
        save.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        save.setTextFill(Color.WHITE);
        save.setStyle(
            "-fx-background-color: linear-gradient(to right, #4CAF50, #388E3C);" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10 30 10 30;"
        );
        
        VBox.setMargin(save, new Insets(20, 0, 0, 0));
        
        save.setMaxWidth(Double.MAX_VALUE);
        save.setAlignment(Pos.CENTER);
        
        
        save.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Map<String, String> options = getSelected();
            OptionsHandler.getInstance().process(options);
            OptionsHandler.getInstance().saveToFile();
            MainMenu mainMenu = new MainMenu(stage);
            Scene mainMenuScene = mainMenu.newScene();
            stage.setScene(mainMenuScene);
            stage.setFullScreen(false);
        });
        
     
        resetButton = new Button("RESET TO DEFAULTS");
        resetButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resetButton.setTextFill(Color.WHITE);
        resetButton.setStyle(
                "-fx-background-color: #c0392b;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 10 30 10 30;"
        );
        VBox.setMargin(resetButton, new Insets(10, 0, 0, 0));
        resetButton.setMaxWidth(Double.MAX_VALUE);
        resetButton.setAlignment(Pos.CENTER);

        resetButton.setOnAction(e -> {
            OptionsHandler.getInstance().resetToDefaults();
            OptionsHandler.getInstance().saveToFile();
            updateComboBoxesWithCurrentOptions();
        });

        content.getChildren().addAll(save, resetButton);
        
        VBox container = new VBox(content);
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(screenWidth, screenHeight);

        root.getChildren().addAll(overlay, container);


        return new Scene(root, screenWidth, screenHeight);
    }

    private void createOption(VBox container, String label, List<String> choices) {
    	 Label optionsL = new Label(label);
    	    optionsL.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    	    optionsL.setTextFill(Color.WHITE);
    	    optionsL.setPrefWidth(180);

    	    ComboBox<String> comboBox = new ComboBox<>();
    	    comboBox.getItems().addAll(choices);
    	    comboBox.setStyle(
    	        "-fx-background-color: white;" +
    	        "-fx-text-fill: white;" +
    	        "-fx-border-radius: 5;" +
    	        "-fx-background-radius: 5;"
    	    );
    	    comboBox.getSelectionModel().selectFirst();
    	    comboBox.setPrefWidth(180);
    	    
    	 
            String initial = getCurrentValueForLabel(label);
            if (choices.contains(initial))
                comboBox.setValue(initial);
            else
                comboBox.getSelectionModel().selectFirst();

    	    HBox row = new HBox(15, optionsL, comboBox);
    	    row.setAlignment(Pos.CENTER_LEFT);
    	    row.setPadding(new Insets(5, 0, 5, 0));
    	    container.getChildren().add(row);

    	    select.put(label, comboBox);
    }
    
    
    
    private void updateComboBoxesWithCurrentOptions() {
        for (Map.Entry<String, ComboBox<String>> entry : select.entrySet()) {
            String label = entry.getKey();
            ComboBox<String> combo = entry.getValue();
            String newValue = getCurrentValueForLabel(label);
            if (combo.getItems().contains(newValue)) {
                combo.setValue(newValue);
            }
        }
    }
    
    private String getCurrentValueForLabel(String label) {
        OptionsHandler oh = OptionsHandler.getInstance();
        switch (label) {
            case "Tower Range":
                return oh.getTowerRange();
            case "Movement Speed":
                return oh.getMovementSpeed();
            case "Wave Count":
                return String.valueOf(oh.getWaveCount());
            case "Delay between waves":
                return oh.getDelay();
            case "Player Hit Point":
                return String.valueOf(oh.getPlayerHP());
            case "Player Hit Damage":
                return String.valueOf(oh.getPlayerDamage());
            case "Gold Amount Start":
                return String.valueOf(oh.getStartGold());
            default:
                return "";
        }
    }
    
    public Map<String, String> getSelected() {
        Map<String, String> res = new HashMap<>();
        for (Map.Entry<String, ComboBox<String>> entry : select.entrySet()) {
            res.put(entry.getKey(), entry.getValue().getValue());
        }
        return res;
    }
    
    public void setSaveHandler(Runnable saveHandler) {
        save.setOnAction(e -> {
            Map<String, String> options = getSelected();
            OptionsHandler.getInstance().process(options);
            OptionsHandler.getInstance().saveToFile();
            saveHandler.run();
        });
    }
    
    
}

