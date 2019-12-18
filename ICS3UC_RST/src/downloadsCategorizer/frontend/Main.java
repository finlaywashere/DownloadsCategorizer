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

	/**
	 * Initializes all the UI stuff
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.indexes = ConfigurationManager.loadIndexes();
		// Initialize the root pane of the window
		GridPane root = new GridPane();
		root.setAlignment(Pos.TOP_CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(20, 20, 20, 20));
		
		// Create the label that tells the user what the input text field is for
		Label label = new Label("Filename: ");
		root.add(label, 0, 0);
		// Create the text field for the user to enter a search term in to
		TextField field = new TextField();
		root.add(field, 1, 0);
		// Create the button that the user uses to search for files
		Button button = new Button("Search");
		button.setOnMouseClicked(event -> {
			search(field.getText());
		});
		root.add(button, 2, 0);
		// Create the list view that is used to list files
		ListView<Label> fileListView = new ListView<Label>(labels);
		root.add(fileListView, 1, 3);
		// Setup the rest of the window stuff
		primaryStage.setScene(new Scene(root, 1920, 1080));
		primaryStage.show();
		button.requestFocus();
	}

	/**
	 * Search through the indexes and display all of the files that match a certain filename
	 * Also does some magic with making the filenames clickable
	 * @param filename
	 */
	private void search(String filename) {
		// Find all the files matching that filename
		List<File> files = indexes.get(filename);
		// Clear the current list of files
		labels.clear();
		// If there are no files, then the list is blank
		if (files == null)
			return;
		for (File f : files) {
			// Put some text on the screen that contains the file path relative to the Downloads folder
			Label l = new Label(makeRelative(f, ConfigurationManager.DOWNLOADS_FOLDER));
			l.setOnMouseClicked(event -> {
				Desktop desktop = Desktop.getDesktop();
				new Thread() {
					public void run() {
						try {
							// The label has been double clicked, so the parent folder for the file that the user selected should be opened
							desktop.open(f.getParentFile());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			});
			// Throw the label on to the screen
			labels.add(l);
		}
	}
	/**
	 * Credit to https://stackoverflow.com/questions/204784/how-to-construct-a-relative-path-in-java-from-two-absolute-paths-or-urls
	 * Makes an absolute path relative
	 * @param filePath the absolute path
	 * @param fromPath the folder to make it relative to
	 * @return the relative path
	 */
	private static String makeRelative(File filePath, File fromPath) {
		String relative = fromPath.toURI().relativize(filePath.toURI()).getPath();
		return relative;
	}

	/**
	 * Starts the UI program
	 * @param args the program arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
