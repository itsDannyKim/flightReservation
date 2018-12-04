package GUI;

// Java FX imports
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// JDBC imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Class imports
import UserTypes.Flight;

public class SearchAFlight extends Application {

	Scene SearchFlightScene;
	ChoiceBox<String> searchByOptions;
	TextField searchCriteria;
	TableView<Flight> searchResults;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@SuppressWarnings("unchecked")
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
		searchByOptions = new ChoiceBox<>();
		searchByOptions.getItems().addAll("From City", "To City", "Date", "Time");
		searchByOptions.setValue("From City");
		GridPane.setConstraints(searchByOptions, 1, 0);
		
		// Create a text field to input criteria
		searchCriteria = new TextField();
		searchCriteria.setPromptText("Enter your criteria");
		GridPane.setConstraints(searchCriteria, 2, 0);
	
		
		// Create a 'Search Now' button
		Button searchNow = new Button();
		searchNow.setText("Go!");
		GridPane.setConstraints(searchNow, 3, 0);
		searchNow.setOnAction(e -> goSearch());
		
		// Create a 'Book" button
		Button bookIt = new Button();
		bookIt.setText("Book This Flight");
		GridPane.setConstraints(bookIt, 3, 10);
		
		
		// Creates 'Log Out' button to go back to 'Login Screen'
		Button btnLogOut = new Button("Log Out");
		GridPane.setConstraints(btnLogOut, 0, 10);
		btnLogOut.setOnAction(e -> {
			try {
				LoginScreen screen = new LoginScreen();
				screen.start(primaryStage);
			} catch (Exception el) {
				el.printStackTrace();
			}
		});
		
		// The following code creates the tables
		TableColumn<Flight, Integer> flightIDColumn = new TableColumn<>("Flight ID");
		flightIDColumn.setMinWidth(100);
		flightIDColumn.setCellValueFactory(new PropertyValueFactory<>("FlightId"));
		
		TableColumn<Flight, String> carrierColumn = new TableColumn<>("Carrier");
		carrierColumn.setMinWidth(200);
		carrierColumn.setCellValueFactory(new PropertyValueFactory<>("carrier"));
		
		TableColumn<Flight, String> departingDateColumn = new TableColumn<>("Departing Date");
		departingDateColumn.setMinWidth(200);
		departingDateColumn.setCellValueFactory(new PropertyValueFactory<>("DepartingDate"));
		
		TableColumn<Flight, String> arrivalDateColumn = new TableColumn<>("Arrival Date");
		arrivalDateColumn.setMinWidth(200);
		arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("ArrivalDate"));
		
		TableColumn<Flight, String> departingCityColumn = new TableColumn<>("Departing City");
		departingCityColumn.setMinWidth(200);
		departingCityColumn.setCellValueFactory(new PropertyValueFactory<>("DepartingCity"));
		
		TableColumn<Flight, String> arrivingCityColumn = new TableColumn<>("Arriving City");
		arrivingCityColumn.setMinWidth(200);
		arrivingCityColumn.setCellValueFactory(new PropertyValueFactory<>("ArrivingCity"));
		
		TableColumn<Flight, String> arrivalTimeColumn = new TableColumn<>("Arrival Time");
		arrivalTimeColumn.setMinWidth(200);
		arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("ArrivalTime"));
		
		TableColumn<Flight, String> departingTimeColumn = new TableColumn<>("Departing Time");
		departingTimeColumn.setMinWidth(200);
		departingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("DepartingTime"));
		
		TableColumn<Flight, Integer> currentPassengersColumn = new TableColumn<>("Current Passengers");
		currentPassengersColumn.setMinWidth(100);
		currentPassengersColumn.setCellValueFactory(new PropertyValueFactory<>("currentPassengers"));
		
		TableColumn<Flight, Integer> passengerLimitColumn = new TableColumn<>("Passenger Limit");
		passengerLimitColumn.setMinWidth(100);
		passengerLimitColumn.setCellValueFactory(new PropertyValueFactory<>("PassengerLimit"));
		
		TableColumn<Flight, Integer> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
		
		searchResults = new TableView<>();
		searchResults.setItems(getFlights());
		searchResults.getColumns().addAll(flightIDColumn, carrierColumn, departingDateColumn, arrivalDateColumn,
				departingCityColumn, arrivingCityColumn, arrivalTimeColumn, departingTimeColumn, currentPassengersColumn,
				passengerLimitColumn, priceColumn);
		
		// set table location on grid???
		GridPane.setConstraints(searchResults, 1, 1);
		
		
		// Add things to the grid
		gridLayout.getChildren().addAll(searchByLabel, searchByOptions, searchCriteria, searchNow, btnLogOut, bookIt, searchResults);
		
		// This creates the scene
		Scene customerSearch = new Scene(gridLayout, 600, 400);
		
		primaryStage.setScene(customerSearch);
		primaryStage.show();
	}
	
	// Method to search flights
	private void goSearch() {
		
		// ArrayList to store flights
		ObservableList<Flight> flights = FXCollections.observableArrayList();
		
		// Create connection once
		Connection dbConn = null;
		dbConn = connect();
		
		// Write out query
		String query;
		
		// Create a query Statement
		PreparedStatement myStmt = null;
		
		// Execute the query
		ResultSet myResult;
		
		switch (searchByOptions.getSelectionModel().getSelectedItem()) {
			
			case "From City": try {
								query = "SELECT * FROM FLIGHT WHERE DepartingCity = '" + searchCriteria.getText() + "'";
								myStmt = dbConn.prepareStatement(query);
								myResult = myStmt.executeQuery();
								
								// Add Flight object to ObservableList flights
								while (myResult.next()) {
									flights.add( 
											new Flight( myResult.getInt("FlightID"), myResult.getString("Carrier"),
													myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
													myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
													myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
													0, myResult.getInt("PassengerLimit"), myResult.getInt("Price")
									));
								}
								
								myStmt.close();
								myResult.close();
								
							 } catch (Exception ex) {
								  	ex.printStackTrace();
							 	}
							  break;
							  
			case "To City": try {
								query = "SELECT * FROM FLIGHT WHERE ArrivingCity = '" + searchCriteria.getText() + "'";
								myStmt = dbConn.prepareStatement(query);
								myResult = myStmt.executeQuery();
			
								// Add Flight object to ObservableList flights
								while (myResult.next()) {
									flights.add( 
											new Flight( myResult.getInt("FlightID"), myResult.getString("Carrier"),
													myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
													myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
													myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
													0, myResult.getInt("PassengerLimit"), myResult.getInt("Price")
									));
								}
								
								myStmt.close();
								myResult.close();
								
							} catch (Exception ex) {
									ex.printStackTrace();
								}
							break;
							
			case "Date": try {
							query = "SELECT * FROM FLIGHT WHERE DepartingDate LIKE '" + searchCriteria.getText().charAt(0) + "/%'";
							myStmt = dbConn.prepareStatement(query);
							myResult = myStmt.executeQuery();
			
							// Add Flight object to ObservableList flights
							while (myResult.next()) {
								flights.add( 
										new Flight( myResult.getInt("FlightID"), myResult.getString("Carrier"),
												myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
												myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
												myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
												0, myResult.getInt("PassengerLimit"), myResult.getInt("Price")
								));
							}
							
							myStmt.close();
							myResult.close();
							
						} catch (Exception ex) {
							ex.printStackTrace();
							}
						break;
						
			case "Time": try {
							query = "SELECT * FROM FLIGHT WHERE DepartingTime LIKE'" + searchCriteria.getText().charAt(0) + "%'";
							myStmt = dbConn.prepareStatement(query);
							myResult = myStmt.executeQuery();
			
							// Add Flight object to ObservableList flights
							while (myResult.next()) {
								flights.add( 
										new Flight( myResult.getInt("FlightID"), myResult.getString("Carrier"),
												myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
												myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
												myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
												0, myResult.getInt("PassengerLimit"), myResult.getInt("Price")
								));
							}
							
							myStmt.close();
							myResult.close();
							
						} catch (Exception ex) {
							ex.printStackTrace();
							}
						break;
		}
	}
	
	public ObservableList<Flight> getFlights() {
		ObservableList<Flight> flights = FXCollections.observableArrayList();
		
		Connection tempConn = null;
		String query = "SELECT * FROM FLIGHT";
		
		try {
			tempConn = connect();
			PreparedStatement myStmt = tempConn.prepareStatement(query);
			ResultSet myResult = myStmt.executeQuery();
			
			while (myResult.next()) {
				flights.add(
						new Flight( myResult.getInt("FlightID"), myResult.getString("Carrier"),
								myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
								myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
								myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
								0, myResult.getInt("PassengerLimit"), myResult.getInt("Price")
						));
				searchResults.setItems(flights);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return flights;
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




















