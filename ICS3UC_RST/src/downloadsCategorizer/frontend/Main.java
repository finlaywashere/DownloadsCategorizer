package downloadsCategorizer.frontend;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import downloadsCategorizer.common.ConfigurationManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Map<String, List<File>> indexes;
	private ObservableList<Label> labels = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.indexes = ConfigurationManager.loadIndexes();
		GridPane root = new GridPane();
		root.setAlignment(Pos.TOP_CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(20, 20, 20, 20));

		Label label = new Label("Filename: ");
		root.add(label, 0, 0);
		TextField field = new TextField();
		root.add(field, 1, 0);
		Button button = new Button("Search");
		button.setOnMouseClicked(event -> {
			search(field.getText());
		});
		root.add(button, 2, 0);
		ListView<Label> fileListView = new ListView<Label>(labels);
		root.add(fileListView, 1, 3);
		primaryStage.setScene(new Scene(root, 1920, 1080));
		primaryStage.show();
		button.requestFocus();
	}

	private void search(String filename) {
		List<File> files = indexes.get(filename);
		labels.clear();
		if (files == null)
			return;
		for (File f : files) {
			Label l = new Label(f.getPath());
			l.setOnMouseClicked(event -> {
				Desktop desktop = Desktop.getDesktop();
				new Thread() {
					public void run() {
						try {
							desktop.open(f.getParentFile());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			});
			labels.add(l);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
