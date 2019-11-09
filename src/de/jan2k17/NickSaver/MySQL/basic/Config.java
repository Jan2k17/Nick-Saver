package de.jan2k17.NickSaver.MySQL.basic;

import de.jan2k17.NickSaver.main.main;

public class Config {
	@SuppressWarnings("unused")
	private static final String host = "host";
	@SuppressWarnings("unused")
	private static final String user = "user";
	@SuppressWarnings("unused")
	private static final String password = "password";
	@SuppressWarnings("unused")
	private static final String database = "database";
	@SuppressWarnings("unused")
	private static final String port = "port";
	@SuppressWarnings("unused")
	private static final String lang = "lang";
	
	public static void clear() {
	    set("host", "", false);
	    set("user", "", false);
	    set("password", "", false);
	    set("database", "", false);
	    set("port", "3306", false);
	    set("lang", "de", false);
	}
	  
	public static void create() {
	    set("host", "", true);
	    set("user", "", true);
	    set("password", "", true);
	    set("database", "", true);
	    set("port", "3306", true);
	    set("lang", "de", true);
	}
	  
	public static void reload() {
	    main.plugin.reloadConfig();
	    create();
	}
	  
	public static void setHost(String s) {
	    if (!getHost().equalsIgnoreCase(s)) {
	      set("host", s, false);
	    }
	  }
	  
	  public static void setUser(String s) {
	    if (!getUser().equalsIgnoreCase(s)) {
	      set("user", s, false);
	    }
	  }
	  
	  public static void setPassword(String s) {
	    if (!getPassword().equalsIgnoreCase(s)) {
	      set("password", s, false);
	    }
	  }
	  
	  public static void setDatabase(String s) {
	    if (!getDatabase().equalsIgnoreCase(s)) {
	      set("database", s, false);
	    }
	  }
	  
	  public static void setPort(String s) {
	    if (!getPort().equalsIgnoreCase(s)) {
	      set("port", s, false);
	    }
	  }
	  
	  public static void setLang(String s) {
	    if (!getLang().equalsIgnoreCase(s)) {
	      set("lang", s, false);
	    }
	  }

	  
	  public static String getHost() { return get("host"); }


	  
	  public static String getUser() { return get("user"); }


	  
	  public static String getPassword() { return get("password"); }


	  
	  public static String getDatabase() { return get("database"); }


	  
	  public static String getPort() { return get("port"); }
	  
	  public static String getLang() { return get("lang"); }

	  
	  private static void set(String name, Object value, boolean checkIfExists) {
	    if (name == null || value == null || (checkIfExists && main.plugin.getConfig().contains(name))) {
	      return;
	    }
	    main.plugin.getConfig().set(name, value);
	    main.plugin.saveConfig();
	  }

	  
	  private static String get(String name) { return (name == null || !main.plugin.getConfig().contains(name)) ? "" : main.plugin.getConfig().getString(name); }
}
