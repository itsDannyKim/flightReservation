package GUI;

//Java FX imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//JDBC imports
import java.sql.Connection;
import java.sql.DriverManager;

public class SearchAFlight extends Application {

	Scene SearchFlightScene;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// This sets the name of the window title
		primaryStage.setTitle("Search a Flight");
		
		// This creates a grid lay out.
		GridPane gridLayout = new GridPane();
		gridLayout.setPadding(new Insets(10, 10, 10, 10));
		gridLayout.setVgap(8);
		gridLayout.setHgap(10);
		
		// Create the 'Search by' label
		Label searchByLabel = new Label("Search by: ");
		GridPane.setConstraints(searchByLabel, 0, 0);
		
		// Create the options ChoiceBox 
		ChoiceBox<String> searchByOptions = new ChoiceBox<>();
		searchByOptions.getItems().addAll("From City", "To City", "Date", "Time");
		searchByOptions.setValue("From City");
		GridPane.setConstraints(searchByOptions, 1, 0);
		
		// Create a text field to input criteria
		TextField searchCriteria = new TextField();
		searchCriteria.setPromptText("Enter your criteria");
		GridPane.setConstraints(searchCriteria, 2, 0);
		
		// Create a 'Search Now' button
		Button searchNow = new Button();
		searchNow.setText("Go!");
		GridPane.setConstraints(searchNow, 3, 0);
		
		//TO DO INSERT DATA FROM MYSQL
		
		
		// Create a 'Go Back' button
		Button goBack = new Button();
		goBack.setText("Go Back");
		GridPane.setConstraints(goBack, 0, 10);
		
		//Create a 'Book" button
		Button bookIt = new Button();
		bookIt.setText("Book This Flight");
		GridPane.setConstraints(bookIt, 3, 10);
		
		// Add things to the grid
		gridLayout.getChildren().addAll(searchByLabel, searchByOptions, searchCriteria, searchNow, goBack, bookIt);
		
		// This creates the scene
		Scene customerSearch = new Scene(gridLayout, 600, 400);
		
		primaryStage.setScene(customerSearch);
		primaryStage.show();
	}
	
	//This is to shorten the code, instead of declaring a connection in a try-catch block everytime
	public Connection connect() {
		
		Connection methodConnection = null;
		
		try {
			methodConnection = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/java_project_database_master", "root", "Adeftday0302!?");
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return methodConnection;
		
	}

	

}




















