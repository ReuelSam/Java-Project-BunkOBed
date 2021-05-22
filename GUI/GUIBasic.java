package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class GUIBasic extends Application
{
    Button button;
    Stage window;
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        window = primaryStage;
        window.setTitle("GUI App");

        window.setOnCloseRequest( e -> { 
            e.consume();        // prevents X button from closing the window
            closeWindow();
        }); // sets method to the x button 

        button  = new Button();
        button.setText("This is a button");

        // lambda function -> arrow function
        button.setOnAction(e -> {
            closeWindow();
        });         

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 350);
        window.setScene(scene);
        window.show();
    }

    private void closeWindow()
    {
        Boolean result = ConfirmBox.display("Confirm Exit", "Are you sure you wish to leave?");
        if ( result == true )
        {
            System.out.println("Closing Window");
            window.close();  
        }
    }

}