package GUI;

// Java FX imports
import UserTypes.Flight;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// JDBC imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UserTypes.Customer;
// Class imports
import UserTypes.Flight;

public class SearchAFlight extends Application {

	Scene SearchFlightScene;
	ChoiceBox<String> searchByOptions;
	TextField searchCriteria;
	TableView<Flight> searchResults;
	ObservableList<Flight> bookedFlights;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {

		// This sets the name of the window title
		primaryStage.setTitle("Search a Flight");

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
				departingCityColumn, arrivingCityColumn, arrivalTimeColumn, departingTimeColumn,
				currentPassengersColumn, passengerLimitColumn, priceColumn);

		// Create the 'Search by' label
		Label searchByLabel = new Label("Search by: ");

		// Create the options ChoiceBox
		searchByOptions = new ChoiceBox<>();
		searchByOptions.getItems().addAll("From City", "To City", "Date", "Time");
		searchByOptions.setValue("From City");

		// Create a text field to input criteria
		searchCriteria = new TextField();
		searchCriteria.setPromptText("Enter your criteria");

		// Create a 'Search Now' button
		Button searchNow = new Button();
		searchNow.setText("Go!");
		searchNow.setOnAction(e -> goSearch());

		// Create a 'Book" button
		Button bookIt = new Button();
		bookIt.setText("Book This Flight");
		bookIt.setOnAction(e -> bookFlight());

		// Create a 'Book" button
		Button unbookIt = new Button();
		unbookIt.setText("Unbook This Flight");
		unbookIt.setOnAction(e -> unbookFlight());

		// Creates 'Log Out' button to go back to 'Login Screen'
		Button btnLogOut = new Button("Log Out");
		btnLogOut.setOnAction(e -> {
			try {
				LoginScreen screen = new LoginScreen();
				screen.start(primaryStage);
			} catch (Exception el) {
				el.printStackTrace();
			}
		});

		// Add top menu
		HBox topMenu = new HBox();
		topMenu.setPadding(new Insets(10, 10, 10, 10));
		topMenu.setSpacing(10);
		topMenu.getChildren().addAll(searchByLabel, searchByOptions, searchCriteria, searchNow);

		// Add bottom menu
		HBox bottomMenu = new HBox();
		bottomMenu.setPadding(new Insets(10, 10, 10, 10));
		bottomMenu.setSpacing(10);
		bottomMenu.getChildren().addAll(btnLogOut, bookIt, unbookIt);

		// Create the VBox, stack them!
		VBox box = new VBox();
		box.getChildren().addAll(topMenu, searchResults, bottomMenu);

		// This creates the scene
		SearchFlightScene = new Scene(box);

		primaryStage.setScene(SearchFlightScene);
		primaryStage.show();
	}

	// Method to search flights
	public void goSearch() {

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

		case "From City":
			try {
				query = "SELECT * FROM FLIGHT WHERE DepartingCity = '" + searchCriteria.getText() + "'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"), 0,
							myResult.getInt("PassengerLimit"), myResult.getInt("Price")));
					searchResults.setItems(flights);
				}

				myStmt.close();
				myResult.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;

		case "To City":
			try {
				query = "SELECT * FROM FLIGHT WHERE ArrivingCity = '" + searchCriteria.getText() + "'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"), 0,
							myResult.getInt("PassengerLimit"), myResult.getInt("Price")));
					searchResults.setItems(flights);
				}

				myStmt.close();
				myResult.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;

		case "Date":
			try {
				query = "SELECT * FROM FLIGHT WHERE DepartingDate LIKE '" + searchCriteria.getText().charAt(0) + "/%'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"), 0,
							myResult.getInt("PassengerLimit"), myResult.getInt("Price")));
					searchResults.setItems(flights);
				}

				myStmt.close();
				myResult.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;

		case "Time":
			try {
				query = "SELECT * FROM FLIGHT WHERE DepartingTime LIKE'" + searchCriteria.getText().charAt(0) + "%'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"), 0,
							myResult.getInt("PassengerLimit"), myResult.getInt("Price")));
					searchResults.setItems(flights);
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
				flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
						myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
						myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
						myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"), 0,
						myResult.getInt("PassengerLimit"), myResult.getInt("Price")));
				searchResults.setItems(flights);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flights;
	}

	public void bookFlight() {
		ObservableList<Flight> flights = FXCollections.observableArrayList();
		Flight selectedFlight = searchResults.getSelectionModel().getSelectedItem();

		/*
		 * if (bookedFlights != null) { for (int i = 0; i < bookedFlights.size() + 1;
		 * i++) {
		 * 
		 * if (selectedFlight.get(0).getDepartingDate().equals(bookedFlights.get(i).
		 * getDepartingDate())) { new AlertBox(); AlertBox.display("Add a Flight",
		 * "Booking Unsuccessful!"); break; } }
		 * 
		 * }
		 */

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		try {

			dbConnection = connect();

			String checkbooking = "Select FlightID FROM Customer WHERE FlightID NOT NULL";
			PreparedStatement myStat1 = dbConnection.prepareStatement(checkbooking);
			ResultSet rs2;
			rs2 = myStat1.executeQuery();

			String fid2 = selectedFlight.getFlightId() + "";

			String fid = rs2.getInt("FlightID") + "";
			while (rs2.next()) {
				if (fid2 != fid) {
					String sql = "INSERT INTO Customer(FlightID) VALUES(selectedFlight.getFlightId())";

					PreparedStatement myStat = dbConnection.prepareStatement(sql);
					ResultSet rs1;
					rs1 = myStat.executeQuery();

					myStat.executeUpdate();
					dbConnection.close();
					myStat.close();

					AlertBox.display("Booked!", "Flight successfully booked");
				} else if (fid2 == fid) {
					AlertBox.display("Not Booked", "Flight already booked");

				} else {
					AlertBox.display("Unsuccessful", "Flight not booked");

				}
			}

			// preparedStatement.setString(10, cust.getConfirmPassword());

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void unbookFlight() {

		new AlertBox();
		AlertBox.display("Add a flight", "Booking Successfully Removed!");

	}

	// This is to shorten the code, instead of declaring a connection in a
	// try-catch block everytime
	public Connection connect() {

		Connection methodConnection = null;

		try {
			methodConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project_database_master",
					"root", "Adeftday0302!?");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return methodConnection;
	}

}
