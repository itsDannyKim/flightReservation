package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	TextField userTextField;
	static String usr = "";

	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// sets the title for the stage
		primaryStage.setTitle("Best Flights For YOU");

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

		userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		// create register on action button to change the scene to the
		// registration
		// scene
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
		// creates login button once user enters username and password
		Button btnLogin = new Button("Sign in");
		HBox hbBtn1 = new HBox(10);
		hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn1.getChildren().add(btnLogin);
		grid.add(hbBtn1, 1, 4);
		btnLogin.setOnAction(e -> {
			SearchAFlight searchFlight = new SearchAFlight();
			SearchAFlightAdmin searchFlightAdmin = new SearchAFlightAdmin();
			try {
				if (checkUsername(userTextField, primaryStage) == true && checkPassword(pwBox, primaryStage) == true) {
					searchFlight.start(primaryStage);
					this.setUsr(userTextField);

				} else if (userTextField.getText().equals("Admin") && pwBox.getText().equals("Admin")) {
					searchFlightAdmin.start(primaryStage);
				} else
					AlertBox.display("Error",
							"Incorrect Login Credentials.  Please try again or click Forgot Password");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		// creates forgot button which takes the user to the forgot password
		// screen
		Button btnForgot = new Button("Forgot Password?");
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(btnForgot);
		grid.add(hbBtn2, 1, 6);
		btnForgot.setOnAction(e -> {
			try {
				ForgotPasswordScreen screen = new ForgotPasswordScreen();
				screen.start(primaryStage);
			} catch (Exception el) {
				el.printStackTrace();
			}
		});

		primaryStage.setScene(LoginScene);
		primaryStage.show();
	}

	public static Connection Connect() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project_database_master", "root", "Adeftday0302!?");
		} catch (Exception e) {
			System.out.println("Can not connect");
		}
		if (con != null) {
			System.out.println("Connected Successfully");
		}
		return con;
	}

	public static boolean checkUsername(TextField user, Stage primaryStage) {
		SearchAFlight main = new SearchAFlight();

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			// starts the connection and runs sql query to select the usernames
			// that are notadmin
			dbConnection = Connect();
			String sql = "SELECT UserName FROM Customer WHERE UserName NOT LIKE 'Admin';";

			preparedStatement = dbConnection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				// if the user field equals the field in the database, return
				// true
				if (user.getText().equals(rs.getString("UserName"))) {
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	public static boolean checkPassword(PasswordField pass, Stage primaryStage) {
		SearchAFlight main = new SearchAFlight();

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		try {
			dbConnection = Connect();
			String sql = "SELECT PasswordAsHash FROM Customer WHERE PasswordAsHash NOT LIKE 'Admin'";
			preparedStatement = dbConnection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (pass.getText().equals(rs.getString("PasswordAsHash"))) {
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setUsr(TextField input) {
		usr = input.getText();
	}
	
	public static String getUsr() {
		return usr;
	}
}

	