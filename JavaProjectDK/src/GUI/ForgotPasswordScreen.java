package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ForgotPasswordScreen extends Application{
	public static String user = "";
	
	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Password Recovery");

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.TOP_LEFT);
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Forgot Password?");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid2.add(scenetitle, 1, 1);

		Scene passwordRecoveryScene = new Scene(grid2, 350, 200);
		
		Label userNamelbl = new Label("Enter your Username: ");
		grid2.add(userNamelbl, 1, 2);
		
		TextField userName = new TextField();
		userName.setPromptText("Username");
		grid2.add(userName, 2, 2);
		
		//Checks if the username is in the database 
		Button btnRecover = new Button("Recover");
		HBox hbbtnRecover = new HBox(10);
		hbbtnRecover.setAlignment(Pos.BOTTOM_LEFT);
		hbbtnRecover.getChildren().add(btnRecover);
		grid2.add(hbbtnRecover, 2, 5);
		btnRecover.setOnAction(e -> {
			
			try {
				// get a connection to the database
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/java_project_database_master", "root", "Adeftday0302!?");
				// create a statement
				Statement myStat = myConn.createStatement();
				// execute a query
				ResultSet myRs;
				user = userName.getText().trim();
				String sqlUserCheck = "SELECT UserName FROM users where UserName = '" + user + "'";
				myRs = myStat.executeQuery(sqlUserCheck);
				if(myRs == null) {
					AlertBox.display("Incorrect Username", "There is no user with the username: " + user);
				}else {
					RecoveryQuestionScreen recoveryQScreen = new RecoveryQuestionScreen();
					recoveryQScreen.start(primaryStage);
				}
			} catch (Exception el) {
				el.printStackTrace();
			}
		});
		Button btnHome = new Button("Home");
		HBox hbBtnHome = new HBox(10);
		hbBtnHome.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnHome.getChildren().add(btnHome);
		grid2.add(hbBtnHome, 1, 5);
		btnHome.setOnAction(e -> {
			try {
				LoginScreen screen = new LoginScreen();
				screen.start(primaryStage);
			} catch (Exception el) {
				el.printStackTrace();
			}
		});
		

		primaryStage.setScene(passwordRecoveryScene);
		primaryStage.show();
	}

}
