package srpattif.encantamentos.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {
	
	private static File dataFolder;
	
	private static File tempFile;
	private static FileConfiguration tempFileConfig;
	
	private static FileManager manager = new FileManager();
	
	public static FileManager getManager() {
		return manager;
	}
	
	public static void createFile(Plugin plugin, String file) {
		dataFolder = plugin.getDataFolder();
		
		tempFile = new File(dataFolder, file);
		
		if(!tempFile.exists()) plugin.saveResource(file, false);
		
		tempFileConfig = YamlConfiguration.loadConfiguration(tempFile);
	}
	
	public static FileConfiguration getFile(String file) {
		saveFile(file);
		
		tempFile = new File(dataFolder, file);
		tempFileConfig = YamlConfiguration.loadConfiguration(tempFile);
		return tempFileConfig;
	}
	
	public static void saveFile(String file) {
		try {
			tempFileConfig.save(tempFile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe("An error ocurred while saving " + dataFolder + "/" + file);
		}
		tempFileConfig = YamlConfiguration.loadConfiguration(tempFile);
	}
	
}
