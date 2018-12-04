package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

import UserTypes.Flight;

public class SearchAFlightAdmin extends Application {

	private TableView<Flight> table = new TableView<Flight>();
	// private final ObservableList<Flight> data = FXCollections.observableArrayList
	final HBox hb = new HBox();

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(800);
		stage.setHeight(550);

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);

		TableColumn FlightIdCol = new TableColumn("FlightId");
		FlightIdCol.setMinWidth(100);
		FlightIdCol.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("FlightId"));
		FlightIdCol.setCellFactory(TextFieldTableCell.forTableColumn());
		FlightIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Flight, Integer>>() {
			@Override
			public void handle(CellEditEvent<Flight, Integer> t) {
				((Flight) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFlightId(t.getNewValue());
			}
		});

		/*
		 * TableColumn lastNameCol = new TableColumn("Last Name");
		 * lastNameCol.setMinWidth(100); lastNameCol.setCellValueFactory(new
		 * PropertyValueFactory<Flight, String>("lastName"));
		 * lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 * lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Flight, String>>()
		 * {
		 * 
		 * @Override public void handle(CellEditEvent<Flight, String> t) { ((Flight)
		 * t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t
		 * .getNewValue()); } });
		 * 
		 * TableColumn emailCol = new TableColumn("Email"); emailCol.setMinWidth(200);
		 * emailCol.setCellValueFactory(new PropertyValueFactory<Flight,
		 * String>("email"));
		 * emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 * emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Flight, String>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Flight, String> t) { ((Flight)
		 * t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.
		 * getNewValue()); } });
		 */

		// table.setItems(data);
		table.getColumns().addAll(FlightIdCol);

		final TextField addFlightIdCol = new TextField();
		addFlightIdCol.setPromptText("FlightID");
		addFlightIdCol.setMaxWidth(FlightIdCol.getPrefWidth());
		/*
		 * final TextField addLastName = new TextField();
		 * addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		 * addLastName.setPromptText("Last Name"); final TextField addEmail = new
		 * TextField(); addEmail.setMaxWidth(emailCol.getPrefWidth());
		 * addEmail.setPromptText("Email");
		 */

		/*
		 * final Button addButton = new Button("Add"); addButton.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent e) { data.add(new
		 * Flight(addFirstName.getText(), addLastName.getText(), addEmail.getText()));
		 * addFirstName.clear(); addLastName.clear(); addEmail.clear(); } });
		 */

		hb.getChildren().addAll(addFlightIdCol);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

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

	private void removeButtonClicked() {

		table.getItems().removeAll(table.getSelectionModel().getSelectedItem());

	}

	private void deleteButtonClicked() {

		ObservableList<Flight> flights = FXCollections.observableArrayList();
		Flight selectedFlight = table.getSelectionModel().getSelectedItem();

		Connection myConn;
		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/java_project_database_master\", \"root\", \"Adeftday0302!?");

			String query = "DELETE * FROM Flight WHERE FlightId=" + selectedFlight.getFlightId();

			// create a statement

			PreparedStatement myStat = myConn.prepareStatement(query);

			// execute a query

			myStat.executeUpdate();

			String query1 = "SELECT * FROM Flight";
			PreparedStatement myStat1 = myConn.prepareStatement(query1);
			ResultSet rs1;
			rs1 = myStat1.executeQuery();

			while (rs1.next()) {

				flights.add(new Flight(rs1.getInt("FlightId"), rs1.getString("Carrier"), rs1.getString("DepartingCity"),
						rs1.getString("ArrivingCity"), rs1.getString("DepartingTime"), rs1.getString("ArrivalTime"),
						rs1.getString("DepartingDate"), rs1.getString("ArrivalDate"), rs1.getInt("currentPassengers"),
						rs1.getInt("PassengerLimit"), rs1.getInt("Price")));

				table.setItems(flights);

			}

			myStat.close();

			rs1.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void addButtonClicked() {

		ObservableList<Flight> flights = FXCollections.observableArrayList();
		Flight selectedFlight = table.getSelectionModel().getSelectedItem();

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
			flightadd.setPassengerLimit(PassengerLimit.getText());
			flightadd.setPrice(Price.getText());

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
			preparedStatement.close();

			String query1 = "SELECT * FROM Flight";
			PreparedStatement myStat1 = myConn.prepareStatement(query1);
			ResultSet rs1;
			rs1 = myStat1.executeQuery();

			while (rs1.next()) {

				flights.add(new Flight(rs1.getInt("FlightId"), rs1.getString("Carrier"), rs1.getString("DepartingCity"),
						rs1.getString("ArrivingCity"), rs1.getString("DepartingTime"), rs1.getString("ArrivalTime"),
						rs1.getString("DepartingDate"), rs1.getString("ArrivalDate"), rs1.getInt("currentPassengers"),
						rs1.getInt("PassengerLimit"), rs1.getInt("Price")));

				table.setItems(flights);

			}
			
			myConn.close();
			myStat1.close();
			rs1.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}