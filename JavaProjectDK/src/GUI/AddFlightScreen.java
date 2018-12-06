package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UserTypes.Flight;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import GUI.SearchAFlightAdmin;

public class AddFlightScreen extends Application {

	TextField Carrier = new TextField();
	TextField DepartingCity = new TextField();
	TextField ArrivingCity = new TextField();
	TextField DepartingTime = new TextField();
	TextField ArrivalTime = new TextField();
	TextField DepartingDate = new TextField();
	TextField ArrivalDate = new TextField();
	TextField currentPassengers = new TextField();
	TextField PassengerLimit = new TextField();
	TextField Price = new TextField();

	public static void main(String[] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Adding a Flight");

		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.TOP_LEFT);
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(25, 25, 25, 25));

		Label Carrierlbl = new Label("Carrier: ");
		grid2.add(Carrierlbl, 1, 0);
		Label DepartingCitylbl = new Label("DepartingCity: ");
		grid2.add(DepartingCitylbl, 1, 1);
		Label ArrivingCitylbl = new Label("ArrivingCity: ");
		grid2.add(ArrivingCitylbl, 1, 2);
		Label DepartingTimelbl = new Label("DepartingTime: ");
		grid2.add(DepartingTimelbl, 1, 3);
		Label ArrivalTimelbl = new Label("ArrivalTime: ");
		grid2.add(ArrivalTimelbl, 1, 4);
		Label DepartingDatelbl = new Label("DepartingDate: ");
		grid2.add(DepartingDatelbl, 1, 5);
		Label ArrivalDatelbl = new Label("ArrivalDate: ");
		grid2.add(ArrivalDatelbl, 1, 6);
		Label currentPassengerslbl = new Label("CurrentPassengers: ");
		grid2.add(currentPassengerslbl, 1, 7);
		Label PassengerLimitlbl = new Label("PassengerLimit: ");
		grid2.add(PassengerLimitlbl, 1, 8);
		Label Pricelbl = new Label("Price: ");
		grid2.add(Pricelbl, 1, 9);

		grid2.add(Carrier, 2, 0);
		grid2.add(DepartingCity, 2, 1);
		grid2.add(ArrivingCity, 2, 2);
		grid2.add(DepartingTime, 2, 3);
		grid2.add(ArrivalTime, 2, 4);
		grid2.add(DepartingDate, 2, 5);
		grid2.add(ArrivalDate, 2, 6);
		grid2.add(currentPassengers, 2, 7);
		grid2.add(PassengerLimit, 2, 8);
		grid2.add(Price, 2, 9);
		
		Button btnHome = new Button("Back");
		HBox hbBtnHome = new HBox(10);
		hbBtnHome.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnHome.getChildren().add(btnHome);
		grid2.add(hbBtnHome, 1, 11);
		btnHome.setOnAction(e -> {
			try {
				SearchAFlightAdmin screen = new SearchAFlightAdmin();
				screen.start(primaryStage);
			} catch (Exception el) {
				el.printStackTrace();
			}
		});

		Button btnAdd = new Button("Add Flight");
		HBox hbbtnAdd = new HBox(10);
		hbbtnAdd.setAlignment(Pos.BOTTOM_LEFT);
		hbbtnAdd.getChildren().add(btnAdd);
		grid2.add(hbbtnAdd, 2, 11);
		btnAdd.setOnAction(e -> {
			try {
				addButtonClicked();
				SearchAFlightAdmin screen = new SearchAFlightAdmin();
				screen.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Scene AddFlightScene = new Scene(grid2, 400, 500);
		primaryStage.setScene(AddFlightScene);
		primaryStage.show();
	}

	private void addButtonClicked() {

		Connection myConn = null;
		PreparedStatement preparedStatement = null;
		try {

			Flight flightadd = new Flight();

			flightadd.setCarrier(Carrier.getText());
			flightadd.setDepartingCity(DepartingCity.getText());
			flightadd.setDepartingDate(DepartingDate.getText());
			flightadd.setDepartingTime(DepartingTime.getText());
			flightadd.setArrivingCity(ArrivingCity.getText());
			flightadd.setArrivalDate(ArrivalDate.getText());
			flightadd.setArrivalTime(ArrivalTime.getText());

			// changes the text field value to an integer for passenger limit and price
			int plimitint = Integer.parseInt(PassengerLimit.getText());
			int priceint = Integer.parseInt(Price.getText());

			flightadd.setPassengerLimit(plimitint);
			flightadd.setPrice(priceint);

			myConn = connect();

			String sql = "INSERT INTO Flight(Carrier, DepartingCity, DepartingDate, DepartingTime, ArrivingCity, ArrivalDate, ArrivalTime, PassengerLimit, "
					+ "Price) VALUES (?,?,?,?,?,?,?,?,?)";

			preparedStatement = myConn.prepareStatement(sql);

			preparedStatement.setString(1, flightadd.getCarrier());
			preparedStatement.setString(2, flightadd.getDepartingCity());
			preparedStatement.setString(3, flightadd.getDepartingDate());
			preparedStatement.setString(4, flightadd.getDepartingTime());
			preparedStatement.setString(5, flightadd.getArrivingCity());
			preparedStatement.setString(6, flightadd.getArrivalDate());
			preparedStatement.setString(7, flightadd.getArrivalTime());
			preparedStatement.setLong(8, flightadd.getPassengerLimit());
			preparedStatement.setLong(9, flightadd.getPrice());

			preparedStatement.executeUpdate();
			myConn.close();
			preparedStatement.close();
			AlertBox.display("Created!", "Flight successfully added");

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

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

}
