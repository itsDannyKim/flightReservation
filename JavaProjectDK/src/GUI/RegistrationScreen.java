package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;

import UserTypes.Customer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class RegistrationScreen extends Application {
	TextField FirstName = new TextField();
	TextField LastName = new TextField();
	TextField StreetAddress = new TextField();
	TextField Zipcode = new TextField();
	TextField State = new TextField();
	TextField Username = new TextField();
	TextField Email = new TextField();
	TextField SocialSecurity = new TextField();
	TextField SecurityQuestionAnswer = new TextField();
	PasswordField Password = new PasswordField();
	PasswordField ConfirmPassword = new PasswordField();

	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Registration");

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.TOP_LEFT);
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));

		Scene RegistrationScene = new Scene(grid2, 400, 550);

		Label firstNameLbl = new Label("First Name: ");
		grid2.add(firstNameLbl, 1, 0);

		Label lastNameLbl = new Label("Last Name: ");
		grid2.add(lastNameLbl, 1, 1);

		Label addressLbl = new Label("Address: ");
		grid2.add(addressLbl, 1, 2);

		Label zipLbl = new Label("Zipcode: ");
		grid2.add(zipLbl, 1, 3);

		Label stateLbl = new Label("State: ");
		grid2.add(stateLbl, 1, 4);

		Label emailLbl = new Label("Email: ");
		grid2.add(emailLbl, 1, 5);

		Label ssnLbl = new Label("SSN: ");
		grid2.add(ssnLbl, 1, 6);

		Label userLbl = new Label("Username: ");
		grid2.add(userLbl, 1, 7);

		Label passLbl = new Label("Password: ");
		grid2.add(passLbl, 1, 8);

		Label pconfirmPassLbl = new Label("Confirm Password");
		grid2.add(pconfirmPassLbl, 1, 9);

		final ComboBox<String> securityQuestionComboBox = new ComboBox<String>();
		securityQuestionComboBox.getItems().addAll("Where was your birthplace?", "Who is your best friend?",
				"What was your first car?", "What is your favorite food?");
		grid2.add(securityQuestionComboBox, 1, 10);

		grid2.add(FirstName, 2, 0);
		grid2.add(LastName, 2, 1);
		grid2.add(StreetAddress, 2, 2);
		grid2.add(Zipcode, 2, 3);
		grid2.add(State, 2, 4);
		grid2.add(Email, 2, 5);
		grid2.add(SocialSecurity, 2, 6);
		grid2.add(Username, 2, 7);
		grid2.add(Password, 2, 8);
		grid2.add(ConfirmPassword, 2, 9);
		grid2.add(SecurityQuestionAnswer, 2, 10);

		FirstName.setPromptText("First Name");
		LastName.setPromptText("Last Name");
		StreetAddress.setPromptText("Street Address");
		Zipcode.setPromptText("#####");
		State.setPromptText("State");
		Email.setPromptText("Example@example.com");
		SocialSecurity.setPromptText("###-##-####");
		Username.setPromptText("Username");
		Password.setPromptText("Password");
		ConfirmPassword.setPromptText("Confirm");
		SecurityQuestionAnswer.setPromptText("Answer");

		Button btnSubmit = new Button("Submit");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().add(btnSubmit);
		grid2.add(hbBtn, 2, 14);
		btnSubmit.setOnAction(e -> {
			Connection dbConnection = null;
			PreparedStatement preparedStatement = null;

			try {
				if (checkUser(Username) == true && checkPass(Password, ConfirmPassword) == true) {

					Customer cust = new Customer();
					cust.setFirstName(FirstName.getText());
					cust.setLastName(LastName.getText());
					cust.setEmail(Email.getText());
					cust.setUsername(Username.getText());
					cust.setStreetAddress(StreetAddress.getText());
					cust.setZipcode(Zipcode.getText());
					cust.setState(State.getText());
					cust.setSecurityQuestion(SecurityQuestionAnswer.getText());
					cust.setPassword(Password.getText());
					cust.setConfirmPassword(ConfirmPassword.getText());
					cust.setSocialSecurity(SocialSecurity.getText());
					// dbConnection = Connect();
					String sql = "Insert into Customer(firstName,lastName, email,userNAME,Address,Zip,State,SecurityQ,  Password, ConfirmPassword,SSN) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
					preparedStatement = dbConnection.prepareStatement(sql);

					preparedStatement.executeUpdate();
					dbConnection.close();
					preparedStatement.close();

					LoginScreen loginPage = new LoginScreen();

					loginPage.start(primaryStage);

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		Button btnHome = new Button("Home");
		HBox hbBtnHome = new HBox(10);
		hbBtnHome.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnHome.getChildren().add(btnHome);
		grid2.add(hbBtnHome, 1, 14);
		btnHome.setOnAction(e -> {
			try {
				LoginScreen screen = new LoginScreen();
				screen.start(primaryStage);
			} catch (Exception el) {
				el.printStackTrace();
			}
		});

		primaryStage.setScene(RegistrationScene);
		primaryStage.show();

	}

	public static boolean checkUser(TextField User) {
		if (User.toString().length() <= 3) {
			AlertBox.display("Error", "Username must be more than 3 characters");
			return false;
		}
		return true;
	}

	public static boolean checkPass(PasswordField pass, PasswordField passw) {
		String str = pass.getText();
		int counter = 0;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c)) {
				counter++;
			}
		}
		if (!pass.getText().equals(passw.getText())) {
			AlertBox.display("Error", "Passwords don't match");
			return false;

		} else if (counter == 0) {
			AlertBox.display("Error", "Password must contain a number");
			return false;

		} else {
			return true;
		}
	}

}
