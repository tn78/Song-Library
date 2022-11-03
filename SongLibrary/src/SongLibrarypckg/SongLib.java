//Tejas Nimkar, ALEXANDER NOCCIOLO
package SongLibrarypckg;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class SongLib extends Application {
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("LibraryUI.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Controller listController = loader.getController();
		listController.start(primaryStage);
		primaryStage.setTitle("Song Library");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	public static void main(String[] args){
		launch(args);
	}
}

