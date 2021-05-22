package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class GUIBasic2 extends Application
{
    Stage window;
    Scene scene1, scene2;
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        window = primaryStage;

        // Layout1 - vertical layout
        Label label1 = new Label("This is page/scene 1");
        Button button1 = new Button("Next Page");
        button1.setOnAction(e -> {
            System.out.println("button1 pressed");
            window.setScene(scene2);
        });

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 300, 300);

        // Layout2 - Stackpane
        Label label2 = new Label("This is page/scene 2");
        Button button2 = new Button("Previous Page");
        button2.setOnAction(e -> window.setScene(scene1));

        StackPane layout2 = new StackPane();
        layout2.getChildren().addAll(label2, button2);
        scene2 = new Scene(layout2, 500,500);
        
        window.setScene(scene1);
        window.setTitle("GUI Basics");
        window.show();
    }
}