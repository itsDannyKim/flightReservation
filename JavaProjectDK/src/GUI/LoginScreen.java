package GUI;

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
	
	Scene RegistrationScene, LoginScreen;
	
	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		
		primaryStage.setTitle("Best Flight Search");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));
		
		Scene RegistrationScene = new Scene(grid2, 600, 300);
		
		Label pls = new Label("You made it");
		grid2.add(pls, 0, 2);
		
		Scene LoginScene = new Scene(grid, 300, 275);
		
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
		
		Button btnRegister = new Button("Register");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().add(btnRegister);
		grid.add(hbBtn, 0, 4);
		btnRegister.setOnAction(e -> primaryStage.setScene(RegistrationScene));
		
		Button btnLogin = new Button("Sign in");
		HBox hbBtn1 = new HBox(10);
		hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn1.getChildren().add(btnLogin);
		grid.add(hbBtn1, 1, 4);
		
		
		
		primaryStage.setScene(LoginScene);
		primaryStage.show();
	}

}
