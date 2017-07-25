package pl.translator.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.translator.controller.MainController;




public class Main extends Application {

	private final String APPNAME = "Translator";

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/translator/view/MainPane.fxml"));
			Parent parent = (Parent)loader.load();
			Scene scene = new Scene(parent);
			MainController controller = loader.getController();
			primaryStage.setScene(scene);
			primaryStage.setHeight(500);
			primaryStage.setTitle(APPNAME);
			primaryStage.show();
			controller.setStage(primaryStage);
					
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
