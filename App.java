import GUI.*;
import Core.*;

import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

 
public class App extends Application
{
    Stage window;
    static API api;
    static MailAPI mailAPI;
    String loggedInEmail;
    static final int WIDTH = 450; 
    static final int HEIGHT = 700; 

    // HEIGHT, WIDTH
    public static void main(String[] args) 
    {
        api = new API();
        mailAPI = new MailAPI();
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        window = primaryStage;
        window.setOnCloseRequest( e -> { 
            e.consume();        // prevents X button from closing the window
            closeWindow();
        }); // sets method to the x button 

        
        Scene scene = HomeWindow();
        // Scene scene = HostWindow();

        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        window.setTitle("Project - 2018103053");
        window.setScene(scene);
        window.show();
    }


    private void closeWindow()
    {
        Boolean result = ConfirmBox.display("Confirm Exit", "Are you sure you wish to leave?");
        if ( result == true )
        {
            api.logout();
            System.out.println("Closing Window");
            window.close();  
        }
    }

    private void logout()
    {
        Boolean result = ConfirmBox.display("Confirm Logout", "Are you sure you wish to logout?");
        if ( result == true )
        {
            api.logout();
            System.out.println("Logging Out");
            window.setScene(HomeWindow());  
        }
    }
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~COMPONANTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public HBox TopMenu(String message)
    {
        HBox topMenu = new HBox();
        Label welcomeLabel = new Label(message);
    
        welcomeLabel.setStyle("-fx-font-size: 1.5em; -fx-font-family: helvetica, arial, sans-serif");
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(welcomeLabel);
        topMenu.setPadding(new Insets(10));
        return topMenu;
    }

    public HBox BottomMenu(boolean flag, String message, int target)
    {
        HBox bottomMenu = new HBox(20);
        if (flag)
        {
            Button backButton  = new Button();
            backButton.setText(message);
            backButton.setPadding(new Insets(0,20,0,20));
            backButton.setStyle("-fx-font-size: 1.5em");
            backButton.setOnAction(e -> {
                if (target == 0)
                {
                    window.setScene(HomeWindow());
                }
                if (target == 1)
                {
                    window.setScene(HostWindow());
                }
                if (target == 2)
                {
                    window.setScene(ResidentWindow());
                }
                if (target == -1)
                {
                    logout();
                }
            });
            backButton.setId("button-primary");

            bottomMenu.getChildren().add(backButton);
        }
        
        Button exitButton  = new Button();
        exitButton.setText("Exit");
        exitButton.setPadding(new Insets(0,20,0,20));
        exitButton.setStyle("-fx-font-size: 1.5em");
        exitButton.setOnAction(e -> {
            closeWindow();
        });
        exitButton.setId("button-exit");
        
        bottomMenu.getChildren().add(exitButton);
        bottomMenu.setAlignment(Pos.CENTER_RIGHT);
        // bottomMenu.setStyle("-fx-background-color: #15202b");
        // bottomMenu.setSpacing(20);

        return bottomMenu;
    }

    public VBox LeftMenu(int accType)
    {
        
        VBox leftMenu = new VBox();
        Button profileButton = new Button("Profile");
        Button viewResButton = new Button("View Reservations");
        // Button settingsButton = new Button("Settings");

        profileButton.setId("button-left-menu");
        viewResButton.setId("button-left-menu");
        // settingsButton.setId("button-left-menu");
        if (accType == 1)
        {
            System.out.println("HOST ACCOUNT");
            Button newRoomButton = new Button("Add New Room");
            Button viewRoomButton = new Button("View Rooms");

            profileButton.setOnAction(e -> window.setScene(HostWindow()));
            newRoomButton.setOnAction(e -> window.setScene(HostAddRoomWindow()));
            viewRoomButton.setOnAction(e -> window.setScene(HostViewRoomWindow()));
            viewResButton.setOnAction(e -> window.setScene(HostViewReservationWindow()));
            // settingsButton.setOnAction(e -> window.setScene(HostSettingsWindow()));

            newRoomButton.setId("button-left-menu");
            viewRoomButton.setId("button-left-menu");
            
            leftMenu.getChildren().addAll(profileButton, newRoomButton, viewRoomButton, viewResButton); // , settingsButton);

        }
        else if (accType == 0)
        {
            System.out.println("RESIDENT ACCOUNT");
            
            Button makeReservationButton = new Button("Make Reservation");
            Button viewRoomButton = new Button("View Rooms");

            profileButton.setOnAction(e -> window.setScene(ResidentWindow()));
            viewRoomButton.setOnAction(e -> window.setScene(ResidentViewRoomWindow()));
            makeReservationButton.setOnAction(e -> window.setScene(ResidentMakeReservationWindow()));
            viewResButton.setOnAction(e -> window.setScene(ResidentViewReservationWindow()));
            // settingsButton.setOnAction(e -> window.setScene(ResidentSettingsWindow()));

            viewRoomButton.setId("button-left-menu");
            makeReservationButton.setId("button-left-menu");

            leftMenu.getChildren().addAll(profileButton, viewRoomButton, makeReservationButton, viewResButton); // , settingsButton);

        }
               

        return leftMenu;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~HOME WINDOW~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    private Scene HomeWindow()
    {
        HBox topMenu = TopMenu("Welcome to Bed n' Breakfast");

        VBox content = new VBox(20); // use any container as center pane e.g. VBox
        Button loginButton  = new Button();
        loginButton.setText("Go to Login");
        loginButton.setPadding(new Insets(0,20,0,20));
        loginButton.setStyle("-fx-font-size: 1.25em");
        loginButton.setOnAction(e -> {
            window.setScene(LoginWindow());
        });
        loginButton.setId("button-main");
        
        Button hostButton  = new Button();
        hostButton.setText("Register as Host");
        hostButton.setPadding(new Insets(0,20,0,20));
        hostButton.setStyle("-fx-font-size: 1.25em");
        hostButton.setOnAction(e -> {
            window.setScene(HostRegistrationWindow());
        });
        hostButton.setId("button-main");


        Button residentButton  = new Button();
        residentButton.setText("Register as Resident");
        residentButton.setPadding(new Insets(0,20,0,20));
        residentButton.setStyle("-fx-font-size: 1.25em");
        residentButton.setOnAction(e -> {
            window.setScene(ResidentRegistrationWindow());
        });
        residentButton.setId("button-main");

        
        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(loginButton, hostButton, residentButton);


        HBox bottomMenu = BottomMenu(false, null, -1);


        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~LOGIN WINDOW~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private Scene LoginWindow()
    {
        HBox topMenu = TopMenu("Login");

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label emailLabel = new Label("Email: ");
        GridPane.setConstraints(emailLabel, 0, 0);   // column 0, row 0
        TextField emailInput = new TextField();
        emailInput.setPromptText("Enter Email here...");      // placeholder
        GridPane.setConstraints(emailInput, 1, 0);  

        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter password here...");      // placeholder
        GridPane.setConstraints(passwordInput, 1, 1);  

        Button loginButton = new Button("Login");
        loginButton.setId("button-success");
        GridPane.setConstraints(loginButton, 2, 3);  
        loginButton.setOnAction(e -> {
            System.out.println(emailInput.getText() + " " + passwordInput.getText());
            int res = api.login(emailInput.getText(), passwordInput.getText());
            if (res == 0)
            {
                loggedInEmail = emailInput.getText();
                AlertBox.display("Login", "You have successfully Logged In.");
                int accType = api.getLoggedInUserType();
                if (accType == 1)
                    window.setScene(HostWindow());
                else if (accType == 0)
                    window.setScene(ResidentWindow());
            }
            else
            {
                AlertBox.display("Login: Error", "Incorrect Credentials. No account found.");
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton);

        HBox bottomMenu = BottomMenu(true, "Back", 0);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~HOST REGISTRATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private Scene HostRegistrationWindow()
    {
        HBox topMenu = TopMenu("Host Registration");

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label emailLabel = new Label("Email: ");
        GridPane.setConstraints(emailLabel, 0, 0);   // column 0, row 0
        TextField emailInput = new TextField();
        emailInput.setPromptText("Enter Email here...");      // placeholder
        GridPane.setConstraints(emailInput, 1, 0);  

        Label firstNameLabel = new Label("First Name: ");
        GridPane.setConstraints(firstNameLabel, 0, 1);   
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("Enter First Name here...");      // placeholder
        GridPane.setConstraints(firstNameInput, 1, 1); 

        Label lastNameLabel = new Label("Last Name: ");
        GridPane.setConstraints(lastNameLabel, 0, 2);   
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Enter Last Name here...");      // placeholder
        GridPane.setConstraints(lastNameInput, 1, 2); 

        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter password here...");      // placeholder
        GridPane.setConstraints(passwordInput, 1, 3); 
        
        Label repasswordLabel = new Label("Re-Password: ");
        GridPane.setConstraints(repasswordLabel, 0, 4);
        PasswordField repasswordInput = new PasswordField();
        repasswordInput.setPromptText("Re-Enter password here...");      // placeholder
        GridPane.setConstraints(repasswordInput, 1, 4); 

        Label ageLabel = new Label("Age: ");
        GridPane.setConstraints(ageLabel, 0, 5);
        TextField ageInput = new TextField();
        ageInput.setPromptText("Enter age here...");      // placeholder
        GridPane.setConstraints(ageInput, 1, 5); 

        Button registerButton = new Button("Register");
        registerButton.setId("button-success");
        GridPane.setConstraints(registerButton, 2, 7);  
        registerButton.setOnAction(e -> {
            try{
                Host host = new Host(emailInput.getText(), firstNameInput.getText(), lastNameInput.getText(), passwordInput.getText(), Integer.parseInt(ageInput.getText()));

                if (!(passwordInput.getText().equals(repasswordInput.getText())))
                {
                    AlertBox.display("Registration Error", "Entered Passwords do not match.");
                    return;
                }
                int res = api.newHost(host);
                if (res == 0)
                {
                    AlertBox.display("Registration", "You have successfully Registered as a Host.");
                    window.setScene(LoginWindow());
                }
                else
                {
                    AlertBox.display("Registration: Error", "Email ID already in use.");
                }
            }
            catch(NumberFormatException ex)
            {
                AlertBox.display("Registration Error", "Please Fill all Fields");
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                AlertBox.display("Registration Error", ex.getMessage());
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(emailLabel, emailInput, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, passwordLabel, passwordInput, repasswordLabel, repasswordInput, ageLabel, ageInput, registerButton);

        HBox bottomMenu = BottomMenu(true, "Back", 0);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RESIDENT REGISTRATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    private Scene ResidentRegistrationWindow()
    {
        HBox topMenu = TopMenu("Resident Registration");

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label emailLabel = new Label("Email: ");
        GridPane.setConstraints(emailLabel, 0, 0);   // column 0, row 0
        TextField emailInput = new TextField();
        emailInput.setPromptText("Enter Email here...");      // placeholder
        GridPane.setConstraints(emailInput, 1, 0);  

        Label firstNameLabel = new Label("First Name: ");
        GridPane.setConstraints(firstNameLabel, 0, 1);   
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("Enter First Name here...");      // placeholder
        GridPane.setConstraints(firstNameInput, 1, 1); 

        Label lastNameLabel = new Label("Last Name: ");
        GridPane.setConstraints(lastNameLabel, 0, 2);   
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Enter Last Name here...");      // placeholder
        GridPane.setConstraints(lastNameInput, 1, 2); 

        Label passwordLabel = new Label("Password: ");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter password here...");      // placeholder
        GridPane.setConstraints(passwordInput, 1, 3); 
        
        Label repasswordLabel = new Label("Re-Password: ");
        GridPane.setConstraints(repasswordLabel, 0, 4);
        PasswordField repasswordInput = new PasswordField();
        repasswordInput.setPromptText("Re-Enter password here...");      // placeholder
        GridPane.setConstraints(repasswordInput, 1, 4); 

        Button registerButton = new Button("Register");
        registerButton.setId("button-success");
        GridPane.setConstraints(registerButton, 2, 7);  
        registerButton.setOnAction(e -> {
            try{
                Resident resident = new Resident(emailInput.getText(), firstNameInput.getText(), lastNameInput.getText(), passwordInput.getText());
                if (!(passwordInput.getText().equals(repasswordInput.getText())))
                {
                    AlertBox.display("Registration Error", "Entered Passwords do not match.");
                    return;
                }
                int res = api.newResident(resident);
                if (res == 0)
                {
                    AlertBox.display("Registration", "You have successfully Registered as a Resident.");
                    window.setScene(LoginWindow());
                }
                else
                {
                    AlertBox.display("Registration: Error", "Email ID already in use.");
                }
            }
            catch(NumberFormatException ex)
            {
                AlertBox.display("Registration Error", "Please Fill all Fields");
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                AlertBox.display("Registration Error", ex.getMessage());
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(emailLabel, emailInput, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, passwordLabel, passwordInput, repasswordLabel, repasswordInput, registerButton);



        // ~~~~~~~~~~~~~~~~~~~~~~ BottomMenu ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        HBox bottomMenu = BottomMenu(true, "Back", 0);


        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ COMMON ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    private Scene viewRoom(Room room)
    {
        int userType = api.getLoggedInUserType();
        HBox topMenu = TopMenu("View Room: " + room.getId());

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells
        // content.setStyle("-fx-background-color: #15202b");

        Label titleLabel = new Label("Room Details");
        titleLabel.setStyle("-fx-font-size: 15pt");
        GridPane.setConstraints(titleLabel, 0, 0);
        
        Label occupancyLabel = new Label("Max Occupancy: " + room.getMaxOccupancy());
        GridPane.setConstraints(occupancyLabel, 0, 2);

        Label addressLabel = new Label("Address: " + room.getAddress());
        GridPane.setConstraints(addressLabel, 0, 3);
        
        Label availabilityLabel = new Label("Availability: " + (room.getAvailability() ? "Available" : "Not Available"));
        GridPane.setConstraints(availabilityLabel, 0, 4);

        Label priceLabel = new Label("Price: " + room.getPrice());
        GridPane.setConstraints(priceLabel, 0, 5);

        Label ownerLabel = new Label("Owner: " + room.getOwnerEmail());
        GridPane.setConstraints(ownerLabel, 0, 6);
      
        try
        {
            InputStream stream = new FileInputStream(room.getPhoto());
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            imageView.setFitWidth(175);
            imageView.setPreserveRatio(true);
            GridPane.setConstraints(imageView, 0, 1);   // column 0, row 0
            content.getChildren().add(imageView);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        HBox contentBottom = new HBox();
        contentBottom.setPadding(new Insets(10,10,10,10));
        contentBottom.setSpacing(10);
        
        Button choiceButton = new Button();
        choiceButton.setId("button-primary");
        Button deleteRoomButton = new Button("Delete Room");
        deleteRoomButton.setOnAction(e -> {
            boolean check = ConfirmBox.display("Delete Room Confirmation","Are you sure you wish to delete room?");
            if (check == true)
            {   
                api.removeRoom(room.getId());
                AlertBox.display("Delete Room", "Room has been deleted.");
                window.setScene(HostViewRoomWindow());
            }
        });

        contentBottom.getChildren().addAll(choiceButton);
        if (userType == 1)
        {
            choiceButton.setText("Edit Room");
            deleteRoomButton.setId("button-danger");
            contentBottom.getChildren().add(deleteRoomButton);
        }
        if (userType == 0)
            choiceButton.setText("Make Reservation");
        choiceButton.setOnAction(e -> {
            if (userType == 1)
            {
                System.out.println("Edit Profile");
                if( api.checkRoomReserved(room.getId()))
                {
                    AlertBox.display("Edit Error", "Cannot Edit Reserved Room since it violates agreed contract");
                }
                else
                    window.setScene(HostEditRoomWindow(room.getId()));
            }   
            if (userType == 0)
                window.setScene(ResidentMakeReservationWindow());
        });
        

        GridPane.setConstraints(contentBottom, 3, 8);   // column 0, row 0

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(titleLabel, occupancyLabel, ownerLabel, addressLabel, priceLabel, availabilityLabel, contentBottom);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }
    
    private Scene viewReservation(int resID)
    {
        Reservation reservation = api.getReservationById(resID);
        int userType = api.getLoggedInUserType();
        HBox topMenu = TopMenu("View Reservation: " + reservation.getId());

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells
        // content.setStyle("-fx-background-color: #15202b");

        Label titleLabel = new Label("Reservation Details");
        titleLabel.setStyle("-fx-font-size: 15pt");
        GridPane.setConstraints(titleLabel, 0, 0);
        
        Label idLabel = new Label("Room ID: " + reservation.getRoomID());
        GridPane.setConstraints(idLabel, 0, 1);
        Button viewRoomButton = new Button("View Room");
        // viewRoomButton.setId("button-primary");
        GridPane.setConstraints(viewRoomButton, 1, 1);
        viewRoomButton.setOnAction(e -> {
            Room room = api.getRoomById(reservation.getRoomID());
            window.setScene(viewRoom(room));
        });

        Label ownerLabel = new Label("Owner: " + reservation.getOwner());
        GridPane.setConstraints(ownerLabel, 0, 2);
        
        Label residentLabel = new Label("Resident: " + reservation.getResident());
        GridPane.setConstraints(residentLabel, 0, 3);

        Label startDateLabel = new Label("Start Date: " + reservation.getStartDate());
        GridPane.setConstraints(startDateLabel, 0, 4);

        Label endDateLabel = new Label("End Date: " + reservation.getEndDate());
        GridPane.setConstraints(endDateLabel, 0, 5);

        Label costLabel = new Label("Cost: " + reservation.getCost());
        GridPane.setConstraints(costLabel, 0, 6);

                
        HBox contentBottom = new HBox();
        contentBottom.setPadding(new Insets(10,10,10,10));
        contentBottom.setSpacing(10);
        
        Button editButton = new Button();
        Button deleteReservationButton = new Button("Delete Reservation");
        deleteReservationButton.setId("button-danger");
        deleteReservationButton.setOnAction(e -> {
            boolean check = ConfirmBox.display("Delete Reservation Confirmation","Are you sure you wish to delete Reservation?");
            if (check == true)
            {   
                api.deleteReservation(reservation.getId());
                String subject = "Re: Reservation Deleted for Res : #"+reservation.getId();
                String body = "This mail is to confirm and inform you about the successful deletion of a reservation(RES #"+ reservation.getId() + "). \nRoom #"+reservation.getRoomID() + "is now made available";
                if (userType == 0)
                {
                    mailAPI.mail(reservation.getOwner(), subject, body);
                }
                else
                {
                    mailAPI.mail(reservation.getResident(), subject, body);
                }
                AlertBox.display("Delete Reservation", "Reservation has been deleted.");
                if (userType == 0)
                    window.setScene(ResidentWindow());
                else
                    window.setScene(HostWindow());

            }
        });

        if (userType == 0)
        {
            editButton.setText("Edit Reservation");
            editButton.setId("button-primary");
            contentBottom.getChildren().add(editButton);
        }
        contentBottom.getChildren().addAll(deleteReservationButton);
        
        editButton.setOnAction(e -> window.setScene(ResidentEditReservationWindow(resID)));
        

        GridPane.setConstraints(contentBottom, 3, 8);   // column 0, row 0

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(titleLabel, idLabel, viewRoomButton, ownerLabel, residentLabel, startDateLabel, endDateLabel, costLabel, contentBottom);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~HOST PAGE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private Scene HostWindow()
    {
        Host host = api.getHostByEmail(loggedInEmail);
        HBox topMenu = TopMenu("Welcome Host: " + host.getName());

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells
        // content.setStyle("-fx-background-color: #15202b");

        Label titleLabel = new Label("Profile Details");
        titleLabel.setStyle("-fx-font-size: 15pt");
        GridPane.setConstraints(titleLabel, 0, 0);
        
        Label nameLabel = new Label("Name: " + host.getName());
        GridPane.setConstraints(nameLabel, 0, 2);

        Label emailLabel = new Label("Email: " + host.getEmail());
        GridPane.setConstraints(emailLabel, 0, 3);   // column 0, row 0
      
        
        Label ageLabel = new Label("Name: " + host.getAge());
        GridPane.setConstraints(ageLabel, 0, 4);

        try
        {
            InputStream stream = new FileInputStream(host.getPhoto());
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            imageView.setFitWidth(175);
            imageView.setPreserveRatio(true);
            GridPane.setConstraints(imageView, 0, 1);   // column 0, row 0
            content.getChildren().add(imageView);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        HBox contentBottom = new HBox();
        contentBottom.setPadding(new Insets(10,10,10,10));
        contentBottom.setSpacing(10);
        
        Button editButton = new Button();
        editButton.setId("button-primary");
        editButton.setText("Edit Account");
        editButton.setOnAction(e -> window.setScene(HostEditWindow()));

        Button deleteHostButton = new Button("Delete Account");
        deleteHostButton.setId("button-danger");
        deleteHostButton.setOnAction(e -> {
            boolean check = ConfirmBox.display("Delete Host Confirmation","Are you sure you wish to delete account?");
            if (check == true)
            {   
                api.removeHost();
                api.logout();
                AlertBox.display("Delete Host", "User has been deleted. You will be returned to home window");
                window.setScene(HomeWindow());
            }
        });
        contentBottom.getChildren().addAll(editButton, deleteHostButton);

        
        GridPane.setConstraints(contentBottom, 3, 6);   // column 0, row 0

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(titleLabel, nameLabel, emailLabel, ageLabel, contentBottom);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene HostEditWindow()
    {
        Host hostOld = api.getHostByEmail(loggedInEmail);
        String passwordTemp = hostOld.getPassword();
        // Room roomOld = api.getRoomById(roomID);
        HBox topMenu = TopMenu("Edit Host: " + hostOld.getName());

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label firstNameLabel = new Label("First Name: ");
        GridPane.setConstraints(firstNameLabel, 0, 1);   
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("Enter First Name here...");      // placeholder
        firstNameInput.setText(hostOld.getFirstName());
        GridPane.setConstraints(firstNameInput, 1, 1); 

        Label lastNameLabel = new Label("Last Name: ");
        GridPane.setConstraints(lastNameLabel, 0, 2);   
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Enter Last Name here...");      // placeholder
        lastNameInput.setText(hostOld.getLastName());
        GridPane.setConstraints(lastNameInput, 1, 2); 

        Label passwordLabel = new Label("New Password: ");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter New Password here...");      // placeholder
        GridPane.setConstraints(passwordInput, 1, 3); 
        
        Label repasswordLabel = new Label("Re-Password: ");
        GridPane.setConstraints(repasswordLabel, 0, 4);
        PasswordField repasswordInput = new PasswordField();
        repasswordInput.setPromptText("Re-Enter password here...");      // placeholder
        GridPane.setConstraints(repasswordInput, 1, 4); 

        Label ageLabel = new Label("Age: ");
        GridPane.setConstraints(ageLabel, 0, 5);
        TextField ageInput = new TextField();
        ageInput.setPromptText("Enter age here...");      // placeholder
        ageInput.setText(String.valueOf(hostOld.getAge()));
        GridPane.setConstraints(ageInput, 1, 5); 
        
        Button photoButton = new Button("Change Host Picture");
        photoButton.setId("button-primary");
        // photoButton.setOnAction(e -> ImageInputMethod(roomOld));
        photoButton.setOnAction(e -> {
            File selectedFile;
            FileChooser photoInput = new FileChooser();
            selectedFile = photoInput.showOpenDialog(null);
            try
            {
                if (selectedFile != null && (ImageIO.read(selectedFile) != null))
                {                
                    System.out.println(selectedFile.getAbsolutePath());
                    hostOld.setPhoto(selectedFile.getAbsolutePath());
                }
                else
                {
                    AlertBox.display("Image Input Error", "Invalid File type");
                }
            }
            catch (IOException ex)
            {
                AlertBox.display("Image Input Error", "Invalid File type");
            }
        });
        GridPane.setConstraints(photoButton, 0, 6); 

        Button resetPhotoButton = new Button("Reset Account Picture to Default");
        resetPhotoButton.setId("button-primary");
        resetPhotoButton.setOnAction(e -> hostOld.resetPhoto());
        GridPane.setConstraints(resetPhotoButton, 1, 6); 

        Button editButton = new Button("Finalize Edit");
        editButton.setId("button-success");
        GridPane.setConstraints(editButton, 2, 7);  
        editButton.setOnAction(e -> {
            if (!(passwordInput.getText().equals("")))
            {
                hostOld.setPassword(passwordInput.getText());
            }
            if (passwordInput.getText().equals(repasswordInput.getText()))
            {
                try{
                    //Room room = new Room(roomOld.getId(), loggedInEmail, addressInput.getText(), Integer.parseInt(occupancyInput.getText()), (availabilityChoice.getValue() == "Available") ? true : false, Double.parseDouble(priceInput.getText()), roomOld.getPhoto());
                    Host host = new Host(hostOld.getEmail(), firstNameInput.getText(), lastNameInput.getText(), hostOld.getPassword(), Integer.parseInt(ageInput.getText()), hostOld.getPhoto());
    
                    int res = api.updateHost(host);
                    if (res == 0)
                    {
                        AlertBox.display("Edit User", "You have successfully updated User: " + hostOld.getName());
                        window.setScene((HostWindow()));
                    }
                    else
                    {
                        AlertBox.display("Edit Host Error", "There was an unexpected Error.");
                    }
                }
                catch(NumberFormatException ex)
                {
                    AlertBox.display("Edit Host Error", "There was an unexpected Error.");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                    AlertBox.display("Edit Host Error", ex.getMessage());
                }
            }
            else
            {
                hostOld.setPassword(passwordTemp);
                AlertBox.display("Edit Error", "Entered Passwords do not match. \nClear Password Field if you wish to not change password.");
            }            
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, passwordLabel, passwordInput, repasswordLabel, repasswordInput, ageLabel, ageInput, photoButton, resetPhotoButton, editButton);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }


    private Scene HostAddRoomWindow()
    {
        //Host host = api.getHostByEmail(loggedInEmail);
        HBox topMenu = TopMenu("New Room");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label addressLabel = new Label("Address: ");
        GridPane.setConstraints(addressLabel, 0, 0);   // column 0, row 0
        TextField addressInput = new TextField();
        addressInput.setPromptText("Enter Address here...");      // placeholder
        GridPane.setConstraints(addressInput, 1, 0);  

        Label occupancyLabel = new Label("Max Occupancy: ");
        GridPane.setConstraints(occupancyLabel, 0, 1);   
        TextField occupancyInput = new TextField();
        occupancyInput.setPromptText("Enter Max Occupancy here...");      // placeholder
        GridPane.setConstraints(occupancyInput, 1, 1); 

        Label availableLabel = new Label("Availability: ");
        GridPane.setConstraints(availableLabel, 0, 2);   
        ChoiceBox<String> availabilityChoice = new ChoiceBox<String>();
        availabilityChoice.getItems().addAll("Available", "Not Available");
        availabilityChoice.setValue("Available");
        GridPane.setConstraints(availabilityChoice, 1, 2); 

        Label priceLabel = new Label("Price per day: ");
        GridPane.setConstraints(priceLabel, 0, 3);
        TextField priceInput = new TextField();
        priceInput.setPromptText("Enter Price here...");      // placeholder
        GridPane.setConstraints(priceInput, 1, 3); 
        
        Button addButton = new Button("Add Room");
        addButton.setId("button-success");
        GridPane.setConstraints(addButton, 2, 7);  
        addButton.setOnAction(e -> {
            try{
                Room room = new Room(loggedInEmail, addressInput.getText(), Integer.parseInt(occupancyInput.getText()), (availabilityChoice.getValue() == "Available") ? true : false, Double.parseDouble(priceInput.getText()));

                int res = api.newRoom(room);
                if (res == 0)
                {
                    AlertBox.display("New Room", "You have successfully added a new Room.");
                    window.setScene(HostWindow());
                }
                else
                {
                    AlertBox.display("New Room Error", "There was an unexpected Error.");
                }
            }
            catch(NumberFormatException ex)
            {
                AlertBox.display("New Room Error", "Please Fill all Fields");
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                AlertBox.display("New Room Error", ex.getMessage());
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(addressLabel, addressInput, occupancyLabel, occupancyInput, availableLabel, availabilityChoice, priceLabel, priceInput, addButton);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene HostEditRoomWindow(int roomID)
    {
        //Host host = api.getHostByEmail(loggedInEmail);
        Room roomOld = api.getRoomById(roomID);
        HBox topMenu = TopMenu("Edit Room: " + roomID);

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label addressLabel = new Label("Address: ");
        GridPane.setConstraints(addressLabel, 0, 0);   // column 0, row 0
        TextField addressInput = new TextField();
        addressInput.setPromptText("Enter Address here...");      // placeholder
        addressInput.setText(roomOld.getAddress());
        GridPane.setConstraints(addressInput, 1, 0);  

        Label occupancyLabel = new Label("Max Occupancy: ");
        GridPane.setConstraints(occupancyLabel, 0, 1);   
        TextField occupancyInput = new TextField();
        occupancyInput.setPromptText("Enter Max Occupancy here...");      // placeholder
        occupancyInput.setText(String.valueOf(roomOld.getMaxOccupancy()));
        GridPane.setConstraints(occupancyInput, 1, 1); 

        Label availableLabel = new Label("Availability: ");
        GridPane.setConstraints(availableLabel, 0, 2);   
        ChoiceBox<String> availabilityChoice = new ChoiceBox<String>();
        availabilityChoice.getItems().addAll("Available", "Not Available");
        availabilityChoice.setValue(roomOld.getAvailability() ? "Available" : "Not Available");
        GridPane.setConstraints(availabilityChoice, 1, 2); 

        Label priceLabel = new Label("Price per day: ");
        GridPane.setConstraints(priceLabel, 0, 3);
        TextField priceInput = new TextField();
        priceInput.setPromptText("Enter Price here...");      // placeholder
        priceInput.setText(String.valueOf(roomOld.getPrice()));
        GridPane.setConstraints(priceInput, 1, 3); 
        
        Button photoButton = new Button("Change Room Picture");
        photoButton.setId("button-primary");
        // photoButton.setOnAction(e -> ImageInputMethod(roomOld));
        photoButton.setOnAction(e -> {
            File selectedFile;
            FileChooser photoInput = new FileChooser();
            selectedFile = photoInput.showOpenDialog(null);
            try
            {
                if (selectedFile != null && (ImageIO.read(selectedFile) != null))
                {                
                    System.out.println(selectedFile.getAbsolutePath());
                    roomOld.setPhoto(selectedFile.getAbsolutePath());
                }
                else
                {
                    AlertBox.display("Image Input Error", "Invalid File type");
                }
            }
            catch (IOException ex)
            {
                AlertBox.display("Image Input Error", "Invalid File type");
            }
        });
        GridPane.setConstraints(photoButton, 0, 5); 

        Button resetPhotoButton = new Button("Reset Room Picture to Default");
        resetPhotoButton.setId("button-primary");
        resetPhotoButton.setOnAction(e -> roomOld.resetPhoto());
        GridPane.setConstraints(resetPhotoButton, 1, 5); 

        Button editButton = new Button("Finalize Edit");
        editButton.setId("button-success");
        GridPane.setConstraints(editButton, 2, 7);  
        editButton.setOnAction(e -> {
            // System.out.println(roomOld.getPhoto());
            try{
                Room room = new Room(roomOld.getId(), loggedInEmail, addressInput.getText(), Integer.parseInt(occupancyInput.getText()), (availabilityChoice.getValue() == "Available") ? true : false, Double.parseDouble(priceInput.getText()), roomOld.getPhoto());

                int res = api.updateRoom(room);
                if (res == 0)
                {
                    AlertBox.display("Edit Room", "You have successfully updated Room: " + roomID);
                    window.setScene((viewRoom(room)));
                }
                else
                {
                    AlertBox.display("Edit Room Error", "There was an unexpected Error.");
                }
            }
            catch(NumberFormatException ex)
            {
                AlertBox.display("Edit Room Error", "There was an unexpected Error.");
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                AlertBox.display("Edit Room Error", ex.getMessage());
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(addressLabel, addressInput, occupancyLabel, occupancyInput, availableLabel, availabilityChoice, priceLabel, priceInput, photoButton, resetPhotoButton, editButton);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene HostViewRoomWindow()
    {
        HBox topMenu = TopMenu("View Rooms");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        VBox content = new VBox(20);
        
        TableView<Room> table = new TableView<>();

        TableColumn<Room, Integer> idColumn = new TableColumn<>("Room ID");
        idColumn.setMinWidth(70);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Room, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(70);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Room, Integer> occupancyColumn = new TableColumn<>("Max Occupancy");
        occupancyColumn.setMinWidth(120);
        occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));

        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price per Night");
        priceColumn.setMinWidth(90);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Room, Boolean> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setMinWidth(90);
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        table.setItems(api.viewRoomsByHostEmail(loggedInEmail));
        table.getColumns().addAll(Arrays.asList(idColumn, addressColumn, occupancyColumn, priceColumn, availabilityColumn));


        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setSpacing(10);
        Button viewRoomButton = new Button("View Room");
        viewRoomButton.setOnAction(e -> {
            ObservableList<Room> selected; // all;
            // all = table.getItems();
            selected = table.getSelectionModel().getSelectedItems();
            // selected.forEach(all::remove);
            window.setScene(viewRoom(selected.get(0)));
        });
        hbox.getChildren().addAll(viewRoomButton);

        content.getChildren().addAll(table, hbox);


        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene HostViewReservationWindow()
    {
        HBox topMenu = TopMenu("View Reservations");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        VBox content = new VBox(20);
        
        TableView<Reservation> table = new TableView<>();

        TableColumn<Reservation, Integer> idColumn = new TableColumn<>("Reservation ID");
        idColumn.setMinWidth(90);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Reservation, Integer> roomColumn = new TableColumn<>("Room ID");
        roomColumn.setMinWidth(90);
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<Reservation, String> residentColumn = new TableColumn<>("Resident");
        residentColumn.setMinWidth(160);
        residentColumn.setCellValueFactory(new PropertyValueFactory<>("resident"));

        TableColumn<Reservation, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setMinWidth(110);
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<Reservation, String> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setMinWidth(110);
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        table.setItems(api.viewReservationsByOwnerEmail(loggedInEmail));
        table.getColumns().addAll(Arrays.asList(idColumn, roomColumn, residentColumn, startDateColumn, endDateColumn));


        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setSpacing(10);
        Button viewReservationButton = new Button("View Reservation");
        viewReservationButton.setOnAction(e -> {
            ObservableList<Reservation> selected; // all;
            // all = table.getItems();
            selected = table.getSelectionModel().getSelectedItems();

            // selected.forEach(all::remove);
            // System.out.println(selected.get(0));
            window.setScene(viewReservation(selected.get(0).getId()));
        });
        hbox.getChildren().addAll(viewReservationButton);

        content.getChildren().addAll(table, hbox);


        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

/*    private Scene HostSettingsWindow()
    {
        // Host host = api.getHostByEmail(loggedInEmail);
        HBox topMenu = TopMenu("Settings");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells
        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }
*/
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RESIDENT PAGE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    private Scene ResidentWindow()
    {
        Resident resident = api.getResidentByEmail(loggedInEmail);
        HBox topMenu = TopMenu("Welcome Resident: " + resident.getName());

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells
        // content.setStyle("-fx-background-color: #15202b");

        Label titleLabel = new Label("Profile Details");
        titleLabel.setStyle("-fx-font-size: 15pt");
        GridPane.setConstraints(titleLabel, 0, 0);
        
        Label nameLabel = new Label("Name: " + resident.getName());
        GridPane.setConstraints(nameLabel, 0, 2);

        Label emailLabel = new Label("Email: " + resident.getEmail());
        GridPane.setConstraints(emailLabel, 0, 3);   // column 0, row 0
      
        try
        {
            InputStream stream = new FileInputStream(resident.getPhoto());
            Image image = new Image(stream);
            //Creating the image view
            ImageView imageView = new ImageView();
            //Setting image to the image view
            imageView.setImage(image);
            imageView.setFitWidth(175);
            imageView.setPreserveRatio(true);
            GridPane.setConstraints(imageView, 0, 1);   // column 0, row 0
            content.getChildren().add(imageView);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        HBox contentBottom = new HBox();
        contentBottom.setPadding(new Insets(10,10,10,10));
        contentBottom.setSpacing(10);
        
        Button editButton = new Button();
        editButton.setId("button-primary");
        editButton.setText("Edit Account");
        editButton.setOnAction(e -> window.setScene(ResidentEditWindow()));

        Button deleteResidentButton = new Button("Delete Account");
        deleteResidentButton.setId("button-danger");
        contentBottom.getChildren().addAll(editButton, deleteResidentButton);
        deleteResidentButton.setOnAction(e -> {
            boolean check = ConfirmBox.display("Delete Resident Confirmation","Are you sure you wish to delete account?");
            if (check == true)
            {   
                api.removeResident();
                api.logout();
                AlertBox.display("Delete Resident", "User has been deleted. You will be returned to home window");
                window.setScene(HomeWindow());
            }
        });
        
        GridPane.setConstraints(contentBottom, 3, 6);   // column 0, row 0

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(titleLabel, nameLabel, emailLabel, contentBottom);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene ResidentEditWindow()
    {
        Resident residentOld = api.getResidentByEmail(loggedInEmail);
        String passwordTemp = residentOld.getPassword();
        // Room roomOld = api.getRoomById(roomID);
        HBox topMenu = TopMenu("Edit Resident: " + residentOld.getName());

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label firstNameLabel = new Label("First Name: ");
        GridPane.setConstraints(firstNameLabel, 0, 1);   
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("Enter First Name here...");      // placeholder
        firstNameInput.setText(residentOld.getFirstName());
        GridPane.setConstraints(firstNameInput, 1, 1); 

        Label lastNameLabel = new Label("Last Name: ");
        GridPane.setConstraints(lastNameLabel, 0, 2);   
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Enter Last Name here...");      // placeholder
        lastNameInput.setText(residentOld.getLastName());
        GridPane.setConstraints(lastNameInput, 1, 2); 

        Label passwordLabel = new Label("New Password: ");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter New Password here...");      // placeholder
        GridPane.setConstraints(passwordInput, 1, 3); 
        
        Label repasswordLabel = new Label("Re-Password: ");
        GridPane.setConstraints(repasswordLabel, 0, 4);
        PasswordField repasswordInput = new PasswordField();
        repasswordInput.setPromptText("Re-Enter password here...");      // placeholder
        GridPane.setConstraints(repasswordInput, 1, 4); 
        
        Button photoButton = new Button("Change Resident Picture");
        photoButton.setId("button-primary");
        // photoButton.setOnAction(e -> ImageInputMethod(roomOld));
        photoButton.setOnAction(e -> {
            File selectedFile;
            FileChooser photoInput = new FileChooser();
            selectedFile = photoInput.showOpenDialog(null);
            try
            {
                if (selectedFile != null && (ImageIO.read(selectedFile) != null))
                {                
                    System.out.println(selectedFile.getAbsolutePath());
                    residentOld.setPhoto(selectedFile.getAbsolutePath());
                }
                else
                {
                    AlertBox.display("Image Input Error", "Invalid File type");
                }
            }
            catch (IOException ex)
            {
                AlertBox.display("Image Input Error", "Invalid File type");
            }
        });
        GridPane.setConstraints(photoButton, 0, 6); 

        Button resetPhotoButton = new Button("Reset Account Picture to Default");
        resetPhotoButton.setId("button-primary");
        resetPhotoButton.setOnAction(e -> residentOld.resetPhoto());
        GridPane.setConstraints(resetPhotoButton, 1, 6); 

        Button editButton = new Button("Finalize Edit");
        editButton.setId("button-success");
        GridPane.setConstraints(editButton, 2, 7);  
        editButton.setOnAction(e -> {
            if (!(passwordInput.getText().equals("")))
            {
                residentOld.setPassword(passwordInput.getText());
            }
            if (passwordInput.getText().equals(repasswordInput.getText()))
            {
                try{
                    //Room room = new Room(roomOld.getId(), loggedInEmail, addressInput.getText(), Integer.parseInt(occupancyInput.getText()), (availabilityChoice.getValue() == "Available") ? true : false, Double.parseDouble(priceInput.getText()), roomOld.getPhoto());
                    Resident resident = new Resident(residentOld.getEmail(), firstNameInput.getText(), lastNameInput.getText(), residentOld.getPassword(), residentOld.getPhoto());
    
                    int res = api.updateResident(resident);
                    if (res == 0)
                    {
                        AlertBox.display("Edit Resident", "You have successfully updated User: " + residentOld.getName());
                        window.setScene((HostWindow()));
                    }
                    else
                    {
                        AlertBox.display("Edit Resident Error", "There was an unexpected Error.");
                    }
                }
                catch(NumberFormatException ex)
                {
                    AlertBox.display("Edit Resident Error", "There was an unexpected Error.");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                    AlertBox.display("Edit Resident Error", ex.getMessage());
                }
            }
            else
            {
                residentOld.setPassword(passwordTemp);
                AlertBox.display("Edit Error", "Entered Passwords do not match. \nClear Password Field if you wish to not change password.");
            }            
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, passwordLabel, passwordInput, repasswordLabel, repasswordInput, photoButton, resetPhotoButton, editButton);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }


    private Scene ResidentMakeReservationWindow()
    {
        //Host host = api.getHostByEmail(loggedInEmail);
        HBox topMenu = TopMenu("Make Reservation");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label roomIDLabel = new Label("Room ID: ");
        GridPane.setConstraints(roomIDLabel, 0, 0);   // column 0, row 0
        TextField roomIDInput = new TextField();
        roomIDInput.setPromptText("Enter Room ID here...");      // placeholder
        GridPane.setConstraints(roomIDInput, 1, 0);  

        Label startDateLabel = new Label("Start Date: ");
        GridPane.setConstraints(startDateLabel, 0, 1);   
        TextField startDateInput = new TextField();
        startDateInput.setPromptText("YYYY-MM-DD");      // placeholder
        GridPane.setConstraints(startDateInput, 1, 1); 

        Label endDateLabel = new Label("End Date: ");
        GridPane.setConstraints(endDateLabel, 0, 2);   
        TextField endDateInput = new TextField();
        endDateInput.setPromptText("YYYY-MM-DD");      // placeholder
        GridPane.setConstraints(endDateInput, 1, 2); 

        
        Button proceedButton = new Button("Proceed");
        proceedButton.setId("button-success");
        GridPane.setConstraints(proceedButton, 2, 4);  
        proceedButton.setOnAction(e -> {
            try
            {
                double price = api.getRoomById(Integer.parseInt(roomIDInput.getText())).getPrice();
                double cost = Reservation.findCost(price, startDateInput.getText(), endDateInput.getText());
                Boolean result = ConfirmBox.display("Total Cost", String.format("Total Cost: %.2f\nDo you wish to proceed?", cost));
                if ( result == true )
                {
                    try{
                        int res = api.makeReservation(Integer.parseInt(roomIDInput.getText()), new Date(startDateInput.getText()), new Date(endDateInput.getText()));
                        if (res == 0)
                        {
                            Reservation reservation = api.getReservationByRoomId(Integer.parseInt(roomIDInput.getText()));
                            String subject = "Re: Reservation Success of Room : #"+reservation.getRoomID();
                            String body = "This mail is to confirm and inform you about the successful booking of your room. Details are attached below:\n" + reservation.toString();
                            mailAPI.mail(reservation.getOwner(), subject, body);

                            AlertBox.display("Reservation Success", "You have made the reservation.");
                            window.setScene(ResidentWindow());
                        }
                        else
                        {
                            AlertBox.display("Reservation Error", "The Room requested is not available");
                        }
                    }
                    catch(NumberFormatException ex)
                    {
                        AlertBox.display("Reservation Error", "Please Fill all Fields");
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                        AlertBox.display("Reservation Error", ex.getMessage());
                    }
                }
            }
            catch (Exception ex)
            {
                AlertBox.display("Reservation Error", "No room with entered ID");
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(roomIDLabel, roomIDInput, startDateLabel, startDateInput, endDateLabel, endDateInput, proceedButton);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene ResidentEditReservationWindow(int resID)
    {
        //Host host = api.getHostByEmail(loggedInEmail);
        Reservation reservation = api.getReservationById(resID);
        HBox topMenu = TopMenu("Edit Reservation: " + resID);

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells

        Label startDateLabel = new Label("Start Date: ");
        GridPane.setConstraints(startDateLabel, 0, 1);   
        TextField startDateInput = new TextField();
        startDateInput.setPromptText("YYYY-MM-DD");      // placeholder
        startDateInput.setText(reservation.getStartDate());
        GridPane.setConstraints(startDateInput, 1, 1); 

        Label endDateLabel = new Label("End Date: ");
        GridPane.setConstraints(endDateLabel, 0, 2);   
        TextField endDateInput = new TextField();
        endDateInput.setPromptText("YYYY-MM-DD");      // placeholder
        endDateInput.setText(reservation.getEndDate());
        GridPane.setConstraints(endDateInput, 1, 2);

        Button editButton = new Button("Finalize Edit");
        editButton.setId("button-success");
        GridPane.setConstraints(editButton, 2, 7);  
        editButton.setOnAction(e -> {
            // System.out.println(roomOld.getPhoto());
            try{
                double cost = Reservation.findCost(reservation.getRoomPrice(), startDateInput.getText(), endDateInput.getText());
                boolean check = ConfirmBox.display("Confirm Edit", "The cost for reservation is now: " + cost +".\nDo you wish to continue?");
                if (check == true)
                {
                    int res = api.updateReservationByResident(resID, startDateInput.getText(), endDateInput.getText(), cost);
                    if (res == 0)
                    {
                        Reservation updatedRes = api.getReservationById(resID);
                        String subject = "Re: Reservation Updated for Res : #"+updatedRes.getId();
                        String body = "This mail is to confirm and inform you about the successful updation of a reservation(RES #"+ updatedRes.getId() + "). Details are attached below:\n" +updatedRes.toString();
                        mailAPI.mail(updatedRes.getOwner(), subject, body);
                        AlertBox.display("Edit Reservation", "You have successfully updated Reservation: " + resID);
                        window.setScene((viewReservation(resID)));
                    }
                    else
                    {
                        AlertBox.display("Edit Reservation Error", "There was an unexpected Error.");
                    }
                }
                
            }
            catch(NumberFormatException ex)
            {
                AlertBox.display("Edit Reservation Error", "There was an unexpected Error.");
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                AlertBox.display("Edit Reservation Error", ex.getMessage());
            }
        });

        content.setAlignment(Pos.CENTER);
        content.getChildren().addAll(startDateLabel, startDateInput, endDateLabel, endDateInput, editButton);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private Scene ResidentViewRoomWindow()
    {
        HBox topMenu = TopMenu("View All Rooms");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        VBox content = new VBox(20);

        
        TableView<Room> table = new TableView<>();

        TableColumn<Room, Integer> idColumn = new TableColumn<>("Room ID");
        idColumn.setMinWidth(70);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Room, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(70);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Room, Integer> occupancyColumn = new TableColumn<>("Max Occupancy");
        occupancyColumn.setMinWidth(120);
        occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));

        TableColumn<Room, Double> priceColumn = new TableColumn<>("Price per Night");
        priceColumn.setMinWidth(90);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Room, Boolean> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setMinWidth(90);
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        table.setItems(api.viewAllRooms());
        table.getColumns().addAll(Arrays.asList(idColumn, addressColumn, occupancyColumn, priceColumn, availabilityColumn));


        HBox contentTop = new HBox();
        contentTop.setPadding(new Insets(10,10,10,10));
        contentTop.setSpacing(10);
        
        TextField keyInput = new TextField();
        keyInput.setPromptText("Enter Address Key to search...");
        keyInput.setMinWidth(200);

        ComboBox<String> filterChoice = new ComboBox<>();
        filterChoice.getItems().addAll( "Any", "Free", "Booked");
        filterChoice.setPromptText("Room Status");
        filterChoice.getSelectionModel().selectFirst();
        filterChoice.setOnAction(e -> {
            if ( keyInput.getText().equals("") )
            {
                if (filterChoice.getValue().equals("Any"))
                {
                    table.setItems(api.viewAllRooms());
                }
                else if (filterChoice.getValue().equals("Free"))
                {
                    table.setItems(api.viewFreeRooms());
                }
                else
                {
                    table.setItems(api.viewBookedRooms());
                }
            }
            else
            {
                searchByAddress(table, filterChoice.getValue(), keyInput.getText());
            }
            
        });

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            searchByAddress(table, filterChoice.getValue(), keyInput.getText());
        });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            filterChoice.getSelectionModel().selectFirst();
            keyInput.clear();
            table.setItems(api.viewAllRooms());
        });
        
        contentTop.getChildren().addAll(keyInput, searchButton, filterChoice, resetButton);

        HBox contentBottom = new HBox();
        contentBottom.setPadding(new Insets(10,10,10,10));
        contentBottom.setSpacing(10);
        Button viewRoomButton = new Button("View Room");
        viewRoomButton.setOnAction(e -> {
            ObservableList<Room> selected; // all;
            // all = table.getItems();
            selected = table.getSelectionModel().getSelectedItems();

            // selected.forEach(all::remove);
            window.setScene(viewRoom(selected.get(0)));
        });
        contentBottom.getChildren().addAll(viewRoomButton);

        content.getChildren().addAll(contentTop, table, contentBottom);


        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    private void searchByAddress(TableView<Room> table, String choice, String input)
    {
        if (choice.equals("Any"))
            table.setItems(api.viewRoomsByAddress(input, 0));
        if (choice.equals("Free"))
            table.setItems(api.viewRoomsByAddress(input, 1));
        if (choice.equals("Booked"))
            table.setItems(api.viewRoomsByAddress(input, 2));
    }

    private Scene ResidentViewReservationWindow()
    {
        // Host host = api.getHostByEmail(loggedInEmail);
        HBox topMenu = TopMenu("View Reservations");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        VBox content = new VBox(20);
        
        TableView<Reservation> table = new TableView<>();

        TableColumn<Reservation, Integer> idColumn = new TableColumn<>("Reservation ID");
        idColumn.setMinWidth(90);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Reservation, Integer> roomColumn = new TableColumn<>("Room ID");
        roomColumn.setMinWidth(90);
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<Reservation, String> residentColumn = new TableColumn<>("Owner");
        residentColumn.setMinWidth(160);
        residentColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));

        TableColumn<Reservation, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setMinWidth(110);
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<Reservation, String> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setMinWidth(110);
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        table.setItems(api.viewReservationsByResidentEmail(loggedInEmail));
        table.getColumns().addAll(Arrays.asList(idColumn, roomColumn, residentColumn, startDateColumn, endDateColumn));


        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setSpacing(10);
        Button viewReservationButton = new Button("View Reservation");
        viewReservationButton.setOnAction(e -> {
            ObservableList<Reservation> selected; // all;
            // all = table.getItems();
            selected = table.getSelectionModel().getSelectedItems();

            // selected.forEach(all::remove);
            // System.out.println(selected.get(0));
            
            window.setScene(viewReservation(selected.get(0).getId()));

        });
        hbox.getChildren().addAll(viewReservationButton);

        content.getChildren().addAll(table, hbox);

        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }

    /*
    private Scene ResidentSettingsWindow()
    {
        // Host host = api.getHostByEmail(loggedInEmail);
        HBox topMenu = TopMenu("Settings");

        VBox leftMenu = LeftMenu(api.getLoggedInUserType());

        GridPane content = new GridPane();
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setVgap(8);        // padding for individual cells
        content.setHgap(10);       // padding for individual cells
        HBox bottomMenu = BottomMenu(true, "Logout", -1);

        // root
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(0, 20, 20, 0)); // space between elements and window border
        root.setTop(topMenu);
        root.setCenter(content);
        root.setBottom(bottomMenu);
        root.setLeft(leftMenu);

        Scene scene = new Scene(root, HEIGHT, WIDTH);
        scene.getStylesheets().add("CSS/base.css");
        return scene;
    }
    */
    

}