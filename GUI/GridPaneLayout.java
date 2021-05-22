package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class GridPaneLayout extends Application
{
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("GridPaneLayout");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);        // padding for individual cells
        grid.setHgap(10);       // padding for individual cells

        Label emailLabel = new Label("Email: ");
        GridPane.setConstraints(emailLabel, 0, 0);   // column 0, row 0
        TextField emailInput = new TextField();
        emailInput.setPromptText("Enter Username here...");      // placeholder
        GridPane.setConstraints(emailInput, 1, 0);  

        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 1);
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("Enter password here...");      // placeholder
        GridPane.setConstraints(passwordInput, 1, 1);  

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 2, 3);  
        loginButton.setOnAction(e -> {
            //boolean valid = (validateEmail(emailInput)) && (validatePassword(passwordInput));
        });

        grid.getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton);
        
        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();
    }

}
