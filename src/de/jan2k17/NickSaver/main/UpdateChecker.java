package de.jan2k17.NickSaver.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import de.jan2k17.NickSaver.MySQL.basic.Config;

public class UpdateChecker {
	private Plugin plugin;
    private int resourceId;

    public UpdateChecker(Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
            	if(Config.getLang().equalsIgnoreCase("de")) {
            		this.plugin.getLogger().info("Updateprüfung fehlgeschlagen: " + exception.getMessage());
            	} else if(Config.getLang().equalsIgnoreCase("en")) {
            		this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            	}
                
            }
        });
    }
}
