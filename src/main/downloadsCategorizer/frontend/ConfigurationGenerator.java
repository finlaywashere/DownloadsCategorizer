package downloadsCategorizer.frontend;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import downloadsCategorizer.common.ConfigurationManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import simpleIO.Console;

public class ConfigurationGenerator extends Application {
	private ObservableList<Label> labels = FXCollections.observableArrayList();
	@Override
	public void start(Stage primaryStage) throws Exception {
		ConfigurationManager.init();
		// Initialize the root pane of the window
		GridPane root = new GridPane();
		root.setAlignment(Pos.TOP_CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(20, 20, 20, 20));

		// Create the label that tells the user what the input text field is for
		Label label = new Label("File extension: ");
		root.add(label, 0, 0);
		// Create the text field for the user to enter a file extension in to
		TextField field = new TextField();
		root.add(field, 1, 0);
		// Create a drop down list of presets
		ComboBox<Label> presets = new ComboBox<Label>();
		ObservableList<Label> presetsList = getPresets();
		presets.setItems(presetsList);
		
		// Create the text field for the user to enter a folder path in to
		// NOTE: its up here so that the dropdown menu can use it
		TextField field2 = new TextField();
		root.add(field2, 1, 1);
		
		presets.setOnAction(event ->{
			Label curr = presets.getValue();
			String[] parse = curr.getText().split(" ",2);
			field.setText(parse[0]);
			if(parse.length > 1)
				field2.setText(parse[1]);
			else
				field2.setText("");
		});
		root.add(presets, 2, 0);
		
		// Create the label that tells the user what the input text field is for
		Label label2 = new Label("Folder path: ");
		root.add(label2, 0, 1);
		// Create the button for the user to append to the config file
		Button button = new Button("Add");
		button.setOnMouseClicked(event -> {
			// Get the extension and path and then remove them
			String extension = field.getText();
			String path = field2.getText();
			field.setText("");
			field2.setText("");
			// Add them to the screen and config
			add(extension,path);
		});
		root.add(button, 2, 1);
		
		Label label3 = new Label("Note: Click on any entry to delete it");
		root.add(label3, 1, 2);
		// Create the list view that is used to list the configuration entries
		ListView<Label> listView = new ListView<Label>(labels);
		root.add(listView, 1, 3);
		
		primaryStage.setScene(new Scene(root, 640, 480));
		primaryStage.show();
		
		loadConfig();
	}
	private ObservableList<Label> getPresets(){
		ObservableList<Label> labels = FXCollections.observableArrayList();
		
		labels.add(new Label(""));
		labels.add(new Label(".pdf Documents/"));
		labels.add(new Label(".docx Documents/"));
		labels.add(new Label(".java Programming/"));
		labels.add(new Label(".jar Executables/"));
		labels.add(new Label(".exe Executables/"));
		labels.add(new Label(".msi Executables/"));
		labels.add(new Label(".zip Archives/"));
		labels.add(new Label(".rar Archives/"));
		labels.add(new Label(".tar Archives/"));
		labels.add(new Label(".tar.xz Archives/"));
		labels.add(new Label(".tar.gz Archives/"));
		labels.add(new Label(".doc Documents/"));
		labels.add(new Label(".sh Executables/"));
		labels.add(new Label(".bin Executables/"));
		labels.add(new Label(".bat Executables/"));
		labels.add(new Label(".class Executables/"));
		labels.add(new Label(".py Executables/"));
		labels.add(new Label(".js Executables/"));
		labels.add(new Label(".gradle Executables/"));
		labels.add(new Label(".txt Documents/"));
		labels.add(new Label(".png Images/"));
		labels.add(new Label(".jpg Images/"));
		labels.add(new Label(".raw Images/"));
		labels.add(new Label(".svg Images/"));
		labels.add(new Label(".bmp Images/"));
		labels.add(new Label(".fs7 Executables/"));
		labels.add(new Label(".fasm Programming/"));
		labels.add(new Label(".fil Programming/"));
		labels.add(new Label(".log Logs/"));
		labels.add(new Label(".odt Documents/"));
		labels.add(new Label(".rtf Documents/"));
		labels.add(new Label(".csv Documents/"));
		labels.add(new Label(".xml Documents/"));
		labels.add(new Label(".pps Documents/"));
		labels.add(new Label(".ppt Documents/"));
		labels.add(new Label(".mp3 Music/"));
		labels.add(new Label(".wav Music/"));
		labels.add(new Label(".m4a Music/"));
		labels.add(new Label(".mp4 Videos/"));
		labels.add(new Label(".avi Videos/"));
		labels.add(new Label(".mov Videos/"));
		labels.add(new Label(".stl 3D Models/"));
		labels.add(new Label(".obj 3D Models/"));
		labels.add(new Label(".gif Images/"));
		labels.add(new Label(".tif Images/"));
		labels.add(new Label(".xlsl Documents/"));
		labels.add(new Label(".xlr Documents/"));
		labels.add(new Label(".xls Documents/"));
		labels.add(new Label(".sql Databases/"));
		labels.add(new Label(".db Databases/"));
		labels.add(new Label(".dbf Databases/"));
		labels.add(new Label(".mdb Databases/"));
		labels.add(new Label(".pdb Databases/"));
		labels.add(new Label(".apk Executables/"));
		labels.add(new Label(".app Executables/"));
		labels.add(new Label(".com Executables/"));
		labels.add(new Label(".b Game Files/"));
		labels.add(new Label(".dem Game Files/"));
		labels.add(new Label(".gam Game Files/"));
		labels.add(new Label(".nes Game Files/"));
		labels.add(new Label(".rom Game Files/"));
		labels.add(new Label(".sav Game Files/"));
		labels.add(new Label(".md Documents/"));
		labels.add(new Label(".jsp Executables/"));
		labels.add(new Label(".php Executables/"));
		labels.add(new Label(".css Programming/"));
		labels.add(new Label(".html Programming/"));
		labels.add(new Label(".js Programming/"));
		labels.add(new Label(".php Programming/"));
		labels.add(new Label(".crx Executables/"));
		labels.add(new Label(".plugin Executables"));
		labels.add(new Label(".fnt Fonts/"));
		labels.add(new Label(".otf Fonts/"));
		labels.add(new Label(".ttf Fonts/"));
		labels.add(new Label(".dll Executables/"));
		labels.add(new Label(".lnk Shortcuts/"));
		labels.add(new Label(".cfg Configurations/"));
		labels.add(new Label(".7z Archives/"));
		labels.add(new Label(".deb Archives/"));
		labels.add(new Label(".pkg Archives/"));
		labels.add(new Label(".rpm Archives/"));
		labels.add(new Label(".iso Disk Images/"));
		labels.add(new Label(".vcd Disk Images/"));
		labels.add(new Label(".c Programming/"));
		labels.add(new Label(".cpp Programming/"));
		labels.add(new Label(".cs Programming/"));
		labels.add(new Label(".h Programming/"));
		labels.add(new Label(".pl Programming/"));
		labels.add(new Label(".lua Programming/"));
		labels.add(new Label(".swift Programming/"));
		labels.add(new Label(".vb Programming/"));
		labels.add(new Label(".bak Backups/"));
		labels.add(new Label(".torrent Torrents/"));
		
		return labels;
	}
	private void loadConfig() {
		try {
			// Clear the labels to make sure there are no duplicates
			labels.clear();
			
			// Open up a scanner to read from the config file
			Scanner in = new Scanner(ConfigurationManager.CONFIGURATION_FILE);
			// Make a variable that stores what the line number is
			int line = 0;
			// Read every line of the file
			while(in.hasNextLine()) {
				// Get the line
				String s = in.nextLine();
				// Trim trailing and leading spaces so that we know if it is a blank line or not
				s = s.trim();
				// If it is blank then don't continue processing it
				if(s.equals("")) {
					line++;
					continue;
				}
				// Remove the asterisks because that is behind the scenes stuff
				s = s.replaceAll("\\*", "");
				// Make a label that can be added to the list
				Label l = new Label(s);
				labels.add(l);
				final int currLine = line;
				// When the label is clicked yeet it out of existence
				l.setOnMouseClicked(event -> {
					remove(l,currLine);
				});
				line++;
			}
			in.close();
		}catch(Exception e) {
			try {
				// Make sure the config file exists
				ConfigurationManager.CONFIGURATION_FILE.createNewFile();
				// Recursively try again because what could go wrong?
				// NOTE: this could be a big sad in the future except its fine because there is no way that any other errors could possibly happen /s (don't screw uo the config file permissions btw)
				loadConfig();
			} catch (IOException e1) {
				Console.print("ERR: "+"Failed to read configuration file!");
				e1.printStackTrace();
			}
		}
	}
	private void remove(Label l, int line) {
		try {
			// Read the lines in the file
			Scanner in = new Scanner(ConfigurationManager.CONFIGURATION_FILE);
			// Make a list to store the temporary lines
			List<String> lines = new ArrayList<String>();
			int lineNum = 0;
			// Iterate over all the lines
			while(in.hasNextLine()) {
				String s = in.nextLine();
				// If the line is not the specified line then add it
				if(line != lineNum) {
					lines.add(s);
				}
				lineNum++;
			}
			in.close();
			// Delete and recreate the config file
			ConfigurationManager.CONFIGURATION_FILE.delete();
			ConfigurationManager.CONFIGURATION_FILE.createNewFile();
			// Write the temporary lines to the config file
			PrintWriter out = new PrintWriter(new FileWriter(ConfigurationManager.CONFIGURATION_FILE));
			for(String s : lines) {
				out.println(s);
			}
			// Save the config file
			out.close();
			
			// Remove the label from the list
			labels.remove(l);
		}catch(Exception e) {
			// Somehow there was an error that should not ever happen
			Console.print("ERR: "+"this shouldn't happen");
		}
	}
	private void add(String extension, String path) {
		try {
			// Make a print writer that is setup to append to the configuration file
			PrintWriter out = new PrintWriter(new FileWriter(ConfigurationManager.CONFIGURATION_FILE,true));
			// Write stuff in the configuration file format
			out.println("*"+extension+" "+path);
			// Actually write the changes
			out.close();
			
			// Reload the config
			loadConfig();
		}catch(Exception e) {
			try {
				// If it failed then make it again and retry
				ConfigurationManager.CONFIGURATION_FILE.createNewFile();
				// NOTE: If bad stuff happens then this will be a big sad because recursion except it doesn't really matter because it should never happen
				add(extension, path);
			} catch (IOException e1) {
				Console.print("ERR: "+"Failed to write to configuration file");
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
