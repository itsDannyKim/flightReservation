package GUI;

// Java FX imports
import UserTypes.Flight;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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

public class SearchAFlightAdmin extends Application {
	int counter;

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
		searchNow.setPrefSize(100, 20);
		searchNow.setText("Go!");
		searchNow.setOnAction(e -> goSearch());

		// Create a 'Book" button
		Button bookIt = new Button();
		bookIt.setPrefSize(200, 20);
		bookIt.setText("Book This Flight");
		bookIt.setOnAction(e -> {
			bookFlight();
		});

		// Create a 'Book" button
		Button unbookIt = new Button();
		unbookIt.setPrefSize(200, 20);
		unbookIt.setText("Unbook This Flight");
		unbookIt.setOnAction(e -> unbookFlight());

		Button viewBookedFlights = new Button();
		viewBookedFlights.setPrefSize(200, 20);
		viewBookedFlights.setText("View all booked flights");
		viewBookedFlights.setOnAction(e -> viewBookedFlights());

		Button btnAdminAdd = new Button();
		btnAdminAdd.setPrefSize(200, 20);
		btnAdminAdd.setText("Add Flight");
		btnAdminAdd.setOnAction(e -> {

			try {
				AddFlightScreen screen = new AddFlightScreen();
				screen.start(primaryStage);
				AlertBox.display("Created!", "Flight successfully added");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {

			}

		});

		Button deleteFlight = new Button();
		deleteFlight.setPrefSize(200, 20);
		deleteFlight.setText("Delete Flight");
		deleteFlight.setOnAction(e -> deleteButtonClicked());

		// Creates 'Log Out' button to go back to 'Login Screen'
		Button btnLogOut = new Button("Log Out");
		btnLogOut.setPrefSize(200, 20);
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
		bottomMenu.setSpacing(50);
		bottomMenu.getChildren().addAll(btnLogOut, bookIt, unbookIt, viewBookedFlights, btnAdminAdd, deleteFlight);

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
				query = "SELECT * FROM FLIGHT WHERE Booked IS NULL " + "AND DepartingCity = '"
						+ searchCriteria.getText() + "'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
							myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
							myResult.getInt("Price")));
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
				query = "SELECT * FROM FLIGHT WHERE Booked IS NULL AND ArrivingCity = '" + searchCriteria.getText()
						+ "'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
							myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
							myResult.getInt("Price")));
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
				query = "SELECT * FROM FLIGHT WHERE Booked IS NULL AND DepartingDate LIKE '"
						+ searchCriteria.getText().charAt(0) + "/%'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
							myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
							myResult.getInt("Price")));
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
				query = "SELECT * FROM FLIGHT WHERE Booked IS NULL AND DepartingTime LIKE'"
						+ searchCriteria.getText().charAt(0) + "%'";
				myStmt = dbConn.prepareStatement(query);
				myResult = myStmt.executeQuery();

				// Add Flight object to ObservableList flights
				while (myResult.next()) {
					flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
							myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
							myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
							myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
							myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
							myResult.getInt("Price")));
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
		String query = "SELECT * FROM FLIGHT WHERE Booked IS NULL";

		try {
			tempConn = connect();
			PreparedStatement myStmt = tempConn.prepareStatement(query);
			ResultSet myResult = myStmt.executeQuery();

			while (myResult.next()) {
				flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
						myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
						myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
						myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
						myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
						myResult.getInt("Price")));
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

		try {

			dbConnection = connect();

			// casts flight id to an int and increases counter for booked flight

			int intFlightid = selectedFlight.getFlightId();
			counter++;
			String increaseCurrent = "UPDATE Flight SET currentPassenger = " + counter + " WHERE FlightID = "
					+ intFlightid;
			PreparedStatement increaseCurrentstmt = dbConnection.prepareStatement(increaseCurrent);
			increaseCurrentstmt.executeUpdate();

			// select all the departing date values from booked flights
			String getDepartingDate = "Select DepartingDate FROM Flight WHERE Booked LIKE 'Booked'";
			PreparedStatement getDepartingDateStmt = dbConnection.prepareStatement(getDepartingDate);
			ResultSet departingDateValue = getDepartingDateStmt.executeQuery();

			// changes result set to a string parameters and checks if the selected record
			// has a conflicting date
			String[] arr = null;
			while (departingDateValue.next()) {
				String em = departingDateValue.getString("DepartingDate");
				arr = em.split("\n");
				for (int i = 0; i < arr.length; i++) {
					System.out.println(arr[i]);
					if (selectedFlight.getDepartingDate() == arr[i]) {
						System.out.println("There is a conflicting flight");

					}
				}
			}

			if (selectedFlight.getCurrentPassengers() < selectedFlight.getPassengerLimit()) {
				String bookFlight = "UPDATE Flight SET Booked = 'Booked' WHERE FlightID = " + intFlightid;
				PreparedStatement bookFlightStmt = dbConnection.prepareStatement(bookFlight);
				bookFlightStmt.executeUpdate();
				AlertBox.display("Booked!", "Flight successfully booked");
			} else {
				System.out.println("The flight is full");
			}

			String query = "SELECT * FROM FLIGHT WHERE Booked IS NULL";
			PreparedStatement myStmt = dbConnection.prepareStatement(query);
			ResultSet myResult = myStmt.executeQuery();

			while (myResult.next()) {

				flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
						myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
						myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
						myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
						myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
						myResult.getInt("Price")));
				searchResults.setItems(flights);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void unbookFlight() {
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

			int intFlightid = selectedFlight.getFlightId();
			String unbookFlight = "UPDATE Flight SET Booked = NULL WHERE FlightID = " + intFlightid;
			PreparedStatement unbookFlightStmt = dbConnection.prepareStatement(unbookFlight);
			unbookFlightStmt.executeUpdate();
			AlertBox.display("Booked!", "Flight successfully unbooked");
			counter--;
			String increaseCurrent = "UPDATE Flight SET currentPassenger = " + counter + " WHERE FlightID = "
					+ intFlightid;
			PreparedStatement increaseCurrentstmt = dbConnection.prepareStatement(increaseCurrent);
			increaseCurrentstmt.executeUpdate();

			String query = "SELECT * FROM FLIGHT WHERE Booked IS NULL";

			PreparedStatement myStmt = dbConnection.prepareStatement(query);
			ResultSet myResult = myStmt.executeQuery();

			while (myResult.next()) {

				flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
						myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
						myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
						myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
						myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
						myResult.getInt("Price")));
				searchResults.setItems(flights);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ObservableList<Flight> viewBookedFlights() {
		ObservableList<Flight> flights = FXCollections.observableArrayList();

		Connection tempConn = null;
		String query = "SELECT * FROM FLIGHT WHERE Booked IS NOT NULL";

		try {
			tempConn = connect();
			PreparedStatement myStmt = tempConn.prepareStatement(query);
			ResultSet myResult = myStmt.executeQuery();

			while (myResult.next()) {
				flights.add(new Flight(myResult.getInt("FlightID"), myResult.getString("Carrier"),
						myResult.getString("DepartingCity"), myResult.getString("ArrivingCity"),
						myResult.getString("DepartingTime"), myResult.getString("ArrivalTime"),
						myResult.getString("DepartingDate"), myResult.getString("ArrivalDate"),
						myResult.getInt("currentPassenger"), myResult.getInt("PassengerLimit"),
						myResult.getInt("Price")));
				searchResults.setItems(flights);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flights;
	}

	// This is to shorten the code, instead of declaring a connection in a
	// try-catch block everytime
	public Connection connect() {

		Connection methodConnection = null;

		try {
			methodConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project_database_master",
					"root", "082486dk");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return methodConnection;
	}

	private void deleteButtonClicked() {

		ObservableList<Flight> flights = FXCollections.observableArrayList();
		Flight selectedFlight = searchResults.getSelectionModel().getSelectedItem();

		Connection myConn;
		try {
			myConn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/java_project_database_master\", \"root\", \"082486dk");

			String query = "DELETE * FROM Flight WHERE FlightId=" + selectedFlight.getFlightId();

			// create a statement

			PreparedStatement myStat = myConn.prepareStatement(query);

			// execute a query

			myStat.executeUpdate();

			String query1 = "SELECT * FROM FLIGHT WHERE Booked IS NULL";
			PreparedStatement myStat1 = myConn.prepareStatement(query1);
			ResultSet rs1;
			rs1 = myStat1.executeQuery();

			while (rs1.next()) {

				flights.add(new Flight(rs1.getInt("FlightId"), rs1.getString("Carrier"), rs1.getString("DepartingCity"),
						rs1.getString("ArrivingCity"), rs1.getString("DepartingTime"), rs1.getString("ArrivalTime"),
						rs1.getString("DepartingDate"), rs1.getString("ArrivalDate"), rs1.getInt("currentPassengers"),
						rs1.getInt("PassengerLimit"), rs1.getInt("Price")));

				searchResults.setItems(flights);

			}

			myStat.close();

			rs1.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
