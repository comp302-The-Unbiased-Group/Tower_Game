package UI;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

import application.OptionsHandler;

public class OptionScreen extends VBox {

    private Map<String, ComboBox<String>> select = new HashMap<>();
    private Button save;

    public OptionScreen(Stage stage) {
    	
        Image banner = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage bg = new BackgroundImage(
                banner,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
        );
        setBackground(new Background(bg));
    	
        setPadding(new Insets(20));
        setSpacing(12);

        
        createOption("Difficulty", Arrays.asList("Easy", "Normal", "Expert"));
        
        createOption("Wave Count", Arrays.asList("1", "2", "3"));
        
        createOption("Delay", Arrays.asList("No Delay","Short(5s)", "Long(30s)"));
        
        createOption("Wave Speed", Arrays.asList("Slowed", "Normal", "Fast"));
        
        createOption("Player Hit Damage", Arrays.asList("20", "40", "60"));
        
        createOption("Composition", Arrays.asList("Mixed", "Goblin only", "Knight only"));
        
        createOption("Tower Range", Arrays.asList("Short", "Medium", "Long"));
        
        createOption("Movement Speed", Arrays.asList("Slowed", "Normal", "Fast"));
        
        createOption("Gold Amount Earned", Arrays.asList("25", "50", "75"));
        
        createOption("Gold Amount Start", Arrays.asList("50", "100", "200"));

        
        save = new Button("SAVE");
        
        save.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Map<String, String> options = getSelected();
            OptionsHandler.process(options);
            
            MainMenu mainMenu = new MainMenu(stage);  // Create MainMenu instance
            Scene mainMenuScene = mainMenu.newScene();  // Get the main menu scene
            stage.setScene(mainMenuScene);
            stage.setFullScreen(true);
        });

        getChildren().add(save);
    }

    private void createOption(String label, List<String> choices) {
    	
        Label optionsL = new Label(label);
        optionsL.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        
        ComboBox<String> comboBox = new ComboBox<>();
        
        comboBox.getItems().addAll(choices);
        comboBox.getSelectionModel().selectFirst(); 
        
        select.put(label, comboBox);

        HBox row = new HBox(10, optionsL, comboBox);
        row.setPadding(new Insets(5));
        getChildren().add(row);
    }

    public Scene newScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new Scene(this, screenBounds.getWidth(), screenBounds.getHeight());
    }

    public Map<String, String> getSelected() {
    	
        Map<String, String> res = new HashMap<>();
        for (Map.Entry<String, ComboBox<String>> entry : select.entrySet()) {
        	
            res.put(entry.getKey(), entry.getValue().getValue());
        }
        return res;
    }
}
