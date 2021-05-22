package GUI;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {
    
    static boolean answer;

    public static boolean display(String title, String message)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); // this window appears on top 
                            // and has to be dealt with before going back to original window
        window.setTitle(title);
        window.setMinWidth(250);

        Label messageLabel = new Label();
        messageLabel.setText(message);
        messageLabel.setPadding(new Insets(10,20,10,20));
        
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> 
        {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> 
        {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll( new Label("\n"), messageLabel, yesButton, noButton, new Label("\n"));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("CSS/base.css");
        window.setScene(scene);
        window.showAndWait();               // blocks any user interaction until the alert box is closesd

        return answer;
    }
}
