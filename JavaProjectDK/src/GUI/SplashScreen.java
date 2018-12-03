package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Example of displaying a splash page for a standalone JavaFX application
 */
public class SplashScreen extends Application {
	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane pane = new HBox(10);
		pane.setPadding(new Insets(0));
		Image image = new Image("assets/flightfinder.png");
		pane.getChildren().add(new ImageView(image));

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane);

		// Set the title
		primaryStage.setTitle("Welcome to BestFlights4U");
		primaryStage.setScene(scene);
		primaryStage.show();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		LoginScreen login = new LoginScreen();

		login.start(primaryStage);

	}
}
