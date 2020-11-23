package def;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main
	extends Application {

	public static void main(String[] args) {
		System.out.println("JAPIERDOLE");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
		primaryStage.setTitle("Oblodzisz?");
		System.out.println("Chuj kuwra");
		primaryStage.setScene(new Scene(root,350,300));
		primaryStage.show();

	}
}
