package downloadsCategorizer.frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	private GridPane fileListPane;
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane root = new GridPane();
		root.setAlignment(Pos.TOP_CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(20,20,20,20));
		
		Label label = new Label("Filename: ");
		root.add(label, 0,0);
		TextField field = new TextField();
		root.add(field, 1, 0);
		Button button = new Button("Search");
		button.setOnMouseClicked(event -> {
			search(field.getText());
		});
		root.add(button, 2, 0);
		
		fileListPane = new GridPane();
		fileListPane.setAlignment(Pos.TOP_LEFT);
		fileListPane.setHgap(10);
		fileListPane.setVgap(10);
		fileListPane.setPadding(new Insets(20,20,20,20));
		
		Label test = new Label("test");
		fileListPane.add(test, 0, 1);
		
		root.add(fileListPane, 1, 3);
		primaryStage.setScene(new Scene(root,1920,1080));
		primaryStage.show();
		button.requestFocus();
	}
	private void search(String filename) {
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
