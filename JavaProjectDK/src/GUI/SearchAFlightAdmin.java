package GUI;

import UserTypes.Flight;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SearchAFlightAdmin extends Application {
	private TableView<Flight> table;
	TextField flightsBooked, searchField,TflightId,TdepartureDate,TarrivalDate,
    TdepartureTime,TarrivalTime,TdepartureCity, TarrivalCity,TflightNumber,
    TmaxSeats,TcurrentSeats;
	
	int booknum = 0;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(900);
		stage.setHeight(500);

		final Label label = new Label("Flight Reservation System");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);

		TableColumn <Flight, String>FlightID = new TableColumn<>("FlightId");
		TableColumn <Flight, String>Carrier = new TableColumn<>("Carrier");
		TableColumn <Flight, String>DepartingCity = new TableColumn<>("DepartingCity");
		TableColumn <Flight, String>ArrivingCity = new TableColumn<>("ArrivingCity");
		TableColumn <Flight, String>DepartingTime = new TableColumn<>("DepartingTime");
		TableColumn <Flight, String>ArrivalTime = new TableColumn<>("ArrivalTime");
		TableColumn <Flight, String>ArrivalDate = new TableColumn<>("ArrivalDate");
		TableColumn <Flight, String>currentPassengers = new TableColumn<>("currentPassengers");
		TableColumn <Flight, String>PassengerLimit = new TableColumn<>("PassengerLimit");


		table.getColumns().addAll(FlightId, Carrier, DepartingCity, ArrivingCity, DepartingTime, 
				ArrivalTime, DepartingDate, ArrivalDate, currentPassengers, PassengerLimit);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}
}