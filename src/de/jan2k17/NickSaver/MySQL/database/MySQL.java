package de.jan2k17.NickSaver.MySQL.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.jan2k17.NickSaver.MySQL.basic.Config;

public class MySQL {
	private static Connection con;
	  
	  public static Connection getConnection() { return con; }

	  
	  public static void setConnection(String host, String user, String password, String database, String port) {
	    if (host == null || user == null || password == null || database == null) {
	      return;
	    }
	    disconnect(false);
	    try {
	      con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
	      Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySQL connected.");
	    } catch (Exception e) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Connect Error: " + e.getMessage());
	    } 
	  }

	  
	  public static void connect() { connect(true); }

	  
	  private static void connect(boolean message) {
	    String host = Config.getHost();
	    String user = Config.getUser();
	    String password = Config.getPassword();
	    String database = Config.getDatabase();
	    String port = Config.getPort();
	    
	    if (isConnected()) {
	      if (message) {
	        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Connect Error: Already connected");
	      }
	    } else if (host.equalsIgnoreCase("")) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Host is blank");
	    } else if (user.equalsIgnoreCase("")) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: User is blank");
	    } else if (password.equalsIgnoreCase("")) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Password is blank");
	    } else if (database.equalsIgnoreCase("")) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Database is blank");
	    } else if (port.equalsIgnoreCase("")) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Config Error: Port is blank");
	    } else {
	      setConnection(host, user, password, database, port);
	    } 
	  }

	  
	  public static void disconnect() { disconnect(true); }

	  
	  private static void disconnect(boolean message) {
	    try {
	      if (isConnected()) {
	        con.close();
	        
	        if (message) {
	          Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySQL disconnected.");
	        }
	      } else if (message) {
	        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Disconnect Error: No existing connection");
	      } 
	    } catch (Exception e) {
	      if (message) {
	        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Disconnect Error: " + e.getMessage());
	      }
	    } 
	    con = null;
	  }
	  
	  public static void reconnect() {
	    disconnect();
	    connect();
	  }
	  
	  public static boolean isConnected() {
	    if (con != null) {
	      try {
	        return !con.isClosed();
	      } catch (Exception e) {
	        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Connection:");
	        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: " + e.getMessage());
	      } 
	    }
	    return false;
	  }
	  
	  public static boolean update(String command) {
	    if (command == null) {
	      return false;
	    }
	    boolean result = false;
	    
	    connect(false);
	    try {
	      Statement st = getConnection().createStatement();
	      st.executeUpdate(command);
	      st.close();
	      result = true;
	    } catch (Exception e) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Update:");
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Command: " + command);
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: " + e.getMessage());
	    } 
	    disconnect(false);
	    return result;
	  }
	  
	  public static ResultSet query(String command) {
	    if (command == null) {
	      return null;
	    }
	    connect(false);
	    ResultSet rs = null;
	    try {
	      Statement st = getConnection().createStatement();
	      rs = st.executeQuery(command);
	    } catch (Exception e) {
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Query:");
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Command: " + command);
	      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: " + e.getMessage());
	    } 
	    return rs;
	  }
}
