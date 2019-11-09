package de.jan2k17.NickSaver.MySQL.basic;

import java.sql.ResultSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.jan2k17.NickSaver.MySQL.database.MySQL;
import de.jan2k17.NickSaver.main.main;

public class Commands implements CommandExecutor{
	private static final String perm = ChatColor.RED + "Du hast nicht die benötigten rechte.";
	  
	  private void send(CommandSender sender) {
		  if(Config.getLang().equalsIgnoreCase("de")) {
			  sender.sendMessage(main.prefix + ChatColor.GREEN + "Verwendung: /nick-saver <players>");
			  sender.sendMessage(main.prefix + ChatColor.GREEN + "Verwendung: /nick-saver <reload/rl>");
			  sender.sendMessage(main.prefix + ChatColor.GREEN + "Verwendung: /nick-saver <connect/disconnect/reconnect>");
		  }else if(Config.getLang().equalsIgnoreCase("en")) {
			  sender.sendMessage(main.prefix + ChatColor.GREEN + "Usage: /nick-saver <players>");
			  sender.sendMessage(main.prefix + ChatColor.GREEN + "Usage: /nick-saver <reload/rl>");
			  sender.sendMessage(main.prefix + ChatColor.GREEN + "Usage: /nick-saver <connect/disconnect/reconnect>");
		  }
		  
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("nick-saver")) {
	      if (sender instanceof Player) {
	    	  if(!sender.hasPermission("nicksaver.mysql.admin") || !sender.isOp()) {
	  	        sender.sendMessage(perm);
		        return true;
	    	  }
	      } 
	      if (args.length == 0) {
	        send(sender);
	      }
	      else if (args.length == 1) {
	        String s = args[0];
	        if (s.equalsIgnoreCase("Reload") || s.equalsIgnoreCase("Rl")) {
	          Config.reload();
	          if(Config.getLang().equalsIgnoreCase("de")) {
	        	  sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL Konfiguration neugeladen.");
	          }else if(Config.getLang().equalsIgnoreCase("en")) {
	        	  sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL configuration reloaded.");
	          }
	          
	        } else if (s.equalsIgnoreCase("Connect")) {
	        	if(Config.getLang().equalsIgnoreCase("de")) {
	        		sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL versucht sich zu verbinden. Schaue in die Konsole für mehr Infos.");
	        		MySQL.connect();
	        	}else if(Config.getLang().equalsIgnoreCase("en")) {
	        		sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL is trying to connect. View console for more info.");
	        		MySQL.connect();
	        	}
	        	
	        } else if (s.equalsIgnoreCase("Disconnect")) {
	        	if(Config.getLang().equalsIgnoreCase("de")) {
	        		sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL versucht die Verbindung zu trennen. Schaue in die Konsole für mehr Infos.");
	        		MySQL.disconnect();
	        	}else if(Config.getLang().equalsIgnoreCase("en")) {
	        		sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL is trying to disconnect. View console for more info.");
	        		MySQL.disconnect();
	        	}
	        } else if (s.equalsIgnoreCase("Reconnect")) {
	        	if(Config.getLang().equalsIgnoreCase("de")) {
	        		sender.sendMessage(main.prefix + ChatColor.GREEN + "MySQL versucht sich neu zu verbinden. Schaue in die Konsole für mehr Infos.");
	  	          	MySQL.reconnect();
	        	}else if(Config.getLang().equalsIgnoreCase("en")) {
	        		sender.sendMessage(main.prefix + ChatColor.GREEN +"MySQL is trying to reconnect. View console for more info.");
	  	          	MySQL.reconnect();
	        	}
	        } else if (s.equalsIgnoreCase("Players")) {
	        	String table = "users";
	        	
	        	int i = 0;
	        	
	            ResultSet rs = MySQL.query("SELECT * FROM " + table + ";");
	            try {
	              while (rs.next()) {
	                i++;
	              }
	              if(Config.getLang().equalsIgnoreCase("de")) {
	            	  sender.sendMessage(main.prefix + "Es waren bisher " + ChatColor.RED + ChatColor.BOLD + i + ChatColor.RESET + ChatColor.GREEN + " Spieler auf dem Server gewesen!");
	              }else if(Config.getLang().equalsIgnoreCase("en")) {
	            	  sender.sendMessage(main.prefix + "" + ChatColor.RED + ChatColor.BOLD + i + ChatColor.RESET + ChatColor.GREEN + " are registered in the database!");
	              }
	            } catch (Exception exception) {}
	        } else {
	          send(sender);
	        } 
	      } else {
	        send(sender);
	      } 
	    } 
	    return false;
	  }
}
