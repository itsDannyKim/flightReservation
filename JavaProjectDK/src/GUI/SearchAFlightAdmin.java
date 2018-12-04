package GUI;

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
import UserTypes.Flight;

public class SearchAFlightAdmin extends Application {

	private TableView<Flight> table = new TableView<Flight>();
	//private final ObservableList<Flight> data = FXCollections.observableArrayList
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

		/*TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Flight, String>>() {
			@Override
			public void handle(CellEditEvent<Flight, String> t) {
				((Flight) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
			}
		});

		TableColumn emailCol = new TableColumn("Email");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("email"));
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Flight, String>>() {
			@Override
			public void handle(CellEditEvent<Flight, String> t) {
				((Flight) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
			}
		});*/

		//table.setItems(data);
		table.getColumns().addAll(FlightIdCol);

		final TextField addFlightIdCol = new TextField();
		addFlightIdCol.setPromptText("FlightID");
		addFlightIdCol.setMaxWidth(FlightIdCol.getPrefWidth());
		/*final TextField addLastName = new TextField();
		addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		addLastName.setPromptText("Last Name");
		final TextField addEmail = new TextField();
		addEmail.setMaxWidth(emailCol.getPrefWidth());
		addEmail.setPromptText("Email");*/

		/*final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.add(new Flight(addFirstName.getText(), addLastName.getText(), addEmail.getText()));
				addFirstName.clear();
				addLastName.clear();
				addEmail.clear();
			}
		});*/

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
}