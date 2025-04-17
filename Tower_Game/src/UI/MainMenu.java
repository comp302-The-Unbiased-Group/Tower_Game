package UI;


import javafx.scene.layout.VBox;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import application.*;

public class MainMenu extends VBox {
	

	public MainMenu(Stage stage) {
		
        setAlignment(Pos.CENTER);
        setSpacing(40);
	
	
	
        Button start_button = new Button("Start Game");
        Button map_editor_button = new Button("Map Editor");
        Button options_button = new Button("Options");
        Button quit_button = new Button("Quit");
        

        start_button.setOnAction(e -> {
        GameScreen game = new GameScreen(stage);
        stage.setScene(new Scene(game, 1280, 720));
        });

        map_editor_button.setOnAction(e -> {
        MapEditorSetting editor = new MapEditorSetting(stage);
        stage.setScene(new Scene(editor, 1280, 720));
         });

        options_button.setOnAction(e -> {
        OptionScreen options = new OptionScreen(stage);
        stage.setScene(new Scene(options, 1280, 720));
         });

        quit_button.setOnAction(e -> stage.close());

        getChildren().addAll(start_button, map_editor_button, options_button, quit_button);
         }
        
        
       




}
