package UI;

import javafx.geometry.Insets;
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
import javafx.stage.Stage;

import java.util.*;

import application.OptionsHandler;

public class OptionScreen {

    private final Map<String, ComboBox<String>> select = new HashMap<>();
    private Button save;

    public Scene newScene(Stage stage) {

        StackPane root = new StackPane();


        Image banner = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage bg = new BackgroundImage(
                banner,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1280, 720, false, false, false, false)
        );
        root.setBackground(new Background(bg));

        // filter
        Rectangle overlay = new Rectangle(1280, 720);
        overlay.setFill(new Color(0, 0, 0, 0.35));

        VBox content = new VBox(12);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);

        // settings
        createOption(content, "Difficulty", Arrays.asList("Easy", "Normal", "Expert"));

        createOption(content, "Wave Count", Arrays.asList("1", "2", "3"));

        createOption(content, "Delay", Arrays.asList("No Delay","Short(5s)", "Long(30s)"));

        createOption(content, "Wave Speed", Arrays.asList("Slowed", "Normal", "Fast"));

        createOption(content, "Player Hit Damage", Arrays.asList("20", "40", "60"));

        createOption(content, "Composition", Arrays.asList("Mixed", "Goblin only", "Knight only"));

        createOption(content, "Tower Range", Arrays.asList("Short", "Medium", "Long"));

        createOption(content, "Movement Speed", Arrays.asList("Slowed", "Normal", "Fast"));

        createOption(content, "Gold Amount Earned", Arrays.asList("25", "50", "75"));

        createOption(content, "Gold Amount Start", Arrays.asList("50", "100", "200"));

        // save settings
        save = new Button("SAVE");
        save.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        content.getChildren().add(save);

        save.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Map<String, String> options = getSelected();
            OptionsHandler.getInstance().process(options);
            MainMenu mainMenu = new MainMenu(stage);
            stage.setScene(mainMenu.newScene());
        });


        root.getChildren().addAll(overlay, content);

        // initialize
        return new Scene(root, 1280, 720);
    }

    private void createOption(VBox container, String label, List<String> choices) {

        Label optionsL = new Label(label);

        optionsL.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        optionsL.setTextFill(Color.WHITE);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(choices);
        comboBox.setStyle("-fx-background-color: yellow;");
        comboBox.getSelectionModel().selectFirst();

        select.put(label, comboBox);

        HBox row = new HBox(15, optionsL, comboBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5));
        container.getChildren().add(row);
    }

    public Map<String, String> getSelected() {

        Map<String, String> res = new HashMap<>();
        for (Map.Entry<String, ComboBox<String>> entry : select.entrySet()) {
            res.put(entry.getKey(), entry.getValue().getValue());
        }

        return res;
    }
}

