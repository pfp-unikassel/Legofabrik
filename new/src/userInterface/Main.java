package userInterface;


	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	/**
	 * Startet den Ui controller welcher dann die Steuerung startet
	 */
	private Controller controller;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
	

	    Parent root = FXMLLoader.load(getClass().getResource("/myScene.fxml"));
		primaryStage.setTitle("LegoUI");
		primaryStage.setScene(new Scene(root,950,950));
		primaryStage.show();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/myScene.fxml"));
		controller = loader.<Controller>getController();
	}
	
	@Override
	public void stop(){
		/**
		 * Disconnected bricks wenn das Fenster geschlossen wird
		 */
	    controller.getS().disconnectBricks();
	    System.out.println("Stage is closing");
	}
}
