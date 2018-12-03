package GUI;

import GUI.RegistrationScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends Application {
	// creates scene
	Scene RegistrationScene, LoginScreen;
	
	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		//sets the title for the stage
		primaryStage.setTitle("Best Flight Search");
		
		// set the layout for the login screen
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Scene LoginScene = new Scene(grid, 300, 300);
		
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		
		//create register on action button to change the scene to the registration scene
		Button btnRegister = new Button("Register");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().add(btnRegister);
		grid.add(hbBtn, 0, 4);
		btnRegister.setOnAction(e -> {
			RegistrationScreen rscreen = new RegistrationScreen();
			try {
				rscreen.start(primaryStage);
			} catch (Exception e1) {
			
				e1.printStackTrace();
			}
		});
		
		Button btnLogin = new Button("Sign in");
		HBox hbBtn1 = new HBox(10);
		hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn1.getChildren().add(btnLogin);
		grid.add(hbBtn1, 1, 4);
		
		Button btnForgot = new Button("Forgot Password?");
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(btnForgot);
		grid.add(hbBtn2, 1, 6);
		
		
		
		primaryStage.setScene(LoginScene);
		primaryStage.show();
	}

}
