package unusedclasses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import downloadsCategorizer.common.ConfigurationManager;
import simpleIO.Console;

public class Uninstaller {

	public static void main(String[] args) {
		// 🦀🦀 This program is gone 🦀🦀
		// I have the big sad now because this program is being uninstalled :(
		int i = 0;
		String s = "really";
		Console.print("Note: Uninstalling will only undo the sorting, not remove the program");
		while(i < 30) {
			Console.print(":(\nUwU please don't go!");
			String resp = Console.readString("Are you "+s+" sure you want to uninstall? Y/N ");
			s += " really";
			i++;
			if(!resp.equalsIgnoreCase("y")) {
				Console.print("Good.");
				return;
			}
		}
		File folder = ConfigurationManager.DOWNLOADS_FOLDER;
		for(File f : folder.listFiles()) {
			if(f.isDirectory()) {
				for(File f1 : f.listFiles()) {
					try {
						Files.move(f1.toPath(), new File(folder,f1.getName()).toPath(), StandardCopyOption.ATOMIC_MOVE);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
