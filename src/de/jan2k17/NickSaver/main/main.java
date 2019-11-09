package de.jan2k17.NickSaver.main;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.jan2k17.NickSaver.MySQL.basic.Commands;
import de.jan2k17.NickSaver.MySQL.basic.Config;
import de.jan2k17.NickSaver.MySQL.database.MySQL;
import de.jan2k17.NickSaver.MySQL.database.SQL;

public class main extends JavaPlugin{
	
	public static String prefix = ChatColor.AQUA + "[Nick-Saver] ";
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		getCommand("nick-saver").setExecutor(new Commands());
	    
	    Config.create();
	    MySQL.connect();
		
		if(MySQL.isConnected()) {
			
			new EventListener(this);
			
			if (!SQL.tableExists("users")) {
				
				SQL.createTable("users", "id int(11) NOT NULL AUTO_INCREMENT,\r\n" +
						"			      		name varchar(255) NOT NULL,\r\n" +
						"			      		uuid varchar(100) NOT NULL,\r\n" +
						"			      		position varchar(255) NOT NULL DEFAULT 'Spieler/in',\r\n" +
						"						lastseen TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\r\n" +
						"			      		PRIMARY KEY (`id`)");
			    }
			
		} else {
			if(Config.getLang().equalsIgnoreCase("de")) {
				System.out.println(prefix + "Es konnte keine MySQL-Verbindung aufgebaut werden!");
				this.getServer().shutdown();
			}else if(Config.getLang().equalsIgnoreCase("en")) {
				System.out.println(prefix + "MySQL can't connect to the database!");
				this.getServer().shutdown();
			}
			
		}
		
		System.out.println(prefix + "is enabled!");
		
	}
	
	@Override
	public void onDisable() {
		
		MySQL.disconnect();
		
		System.out.println(prefix + "is disabled!");
		
	}
	
	public static void update(Player p) {
		Logger logger = plugin.getLogger();
		new UpdateChecker(plugin, 72574).getVersion(version -> {
            if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
            	if(Config.getLang().equalsIgnoreCase("de")) {
            		logger.info("Es ist kein Update verfügbar.");
            	} else if(Config.getLang().equalsIgnoreCase("en")) {
            		logger.info("There is not a new update available.");
            	}
            } else {
            	if(Config.getLang().equalsIgnoreCase("de")) {
            		
            		if(p.hasPermission("nicksaver.mysql.admin") || p.isOp()) {
            			p.sendMessage(prefix + "Es ist eine neue Version verfügbar.");
            		}
            		
            		logger.info("Es ist eine neue Version verfügbar.");
            		
            	} else if(Config.getLang().equalsIgnoreCase("en")) {
            		
            		if(p.hasPermission("nicksaver.mysql.admin") || p.isOp()) {
            			p.sendMessage(prefix + "There is a new update available.");
            		}
            		
            		logger.info("There is a new update available.");
            		
            	}
            }
        });
	}
	
}
