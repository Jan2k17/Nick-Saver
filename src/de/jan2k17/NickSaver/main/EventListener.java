package de.jan2k17.NickSaver.main;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.jan2k17.NickSaver.MySQL.basic.Config;
import de.jan2k17.NickSaver.MySQL.database.SQL;

public class EventListener implements Listener{
	
	public EventListener(main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		main.update(p);
		
		boolean uuid = SQL.exists("uuid", p.getUniqueId().toString(), "users");
		
		if(uuid == false) {
			SQL.insertData("name, uuid", "'" + p.getName() + "', '" + p.getUniqueId().toString() + "'", "users");
			if(Config.getLang().equalsIgnoreCase("de")) {
				System.out.println(main.prefix + "Der Username " + p.getName() + " wurde in der Datenbank gespeichert!");
			}else if(Config.getLang().equalsIgnoreCase("en")) {
				System.out.println(main.prefix + "The user " + p.getName() + " has been saved into the database!");
			}
		} else if(uuid == true) {
			boolean uuidname = SQL.exists("name", p.getName(), "users");
			SQL.upsert("lastseen", "CURRENT_TIMESTAMP", "uuid", p.getUniqueId().toString(), "users");
			if(uuidname == false) {
				SQL.upsert("name", p.getName(), "uuid", p.getUniqueId().toString(), "users");
			}
		}
		
	}
	
}
