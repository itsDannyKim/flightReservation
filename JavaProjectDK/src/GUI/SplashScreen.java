package GUI;

import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashScreen {

	public static class ShowImage extends Application {
		@Override // Override the start method in the Application class
		public void start(Stage primaryStage) {

			// Create a pane to hold the image views
			Pane pane = new HBox(20);
			pane.setPadding(new Insets(0));
			Image image = new Image("file:///Users/danielkim/Documents/School/CIS%203270/JavaProjectDK/JavaProjectDK/Resources/flights.jpg");
			pane.getChildren().add(new ImageView(image));

			// Create a scene and place it in the stage
			Scene scene = new Scene(pane);

			// Set the title
			primaryStage.setTitle("Loading...");
			primaryStage.setScene(scene);
			primaryStage.show();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}

			LoginScreen login = new LoginScreen();

			try {
				login.start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void main(String[] args) {
			Application.launch(args);

		}
	}

}
