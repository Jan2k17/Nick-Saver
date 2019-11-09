package de.jan2k17.NickSaver.MySQL.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQL
{
  public static boolean tableExists(String table) {
    if (table == null) {
      return false;
    }
    try {
      Connection connection = MySQL.getConnection();
      if (connection == null) {
        return false;
      }
      DatabaseMetaData metadata = connection.getMetaData();
      if (metadata == null) {
        return false;
      }
      ResultSet rs = metadata.getTables(null, null, table, null);
      if (rs.next()) {
        return true;
      }
    } catch (Exception exception) {}
    
    return false;
  }

  
  public static boolean insertData(String columns, String values, String table) { return MySQL.update("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");"); }

  
  public static boolean deleteData(String column, String logic_gate, String data, String table) {
    if (data != null) {
      data = "'" + data + "'";
    }
    return MySQL.update("DELETE FROM " + table + " WHERE " + column + logic_gate + data + ";");
  }
  
  public static boolean exists(String column, String data, String table) {
    if (data != null) {
      data = "'" + data + "'";
    }
    try {
      ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
      
      if (rs.next()) {
        return true;
      }
    } catch (Exception exception) {}
    
    return false;
  }

  
  public static boolean deleteTable(String table) { return MySQL.update("DROP TABLE " + table + ";"); }


  
  public static boolean truncateTable(String table) { return MySQL.update("TRUNCATE TABLE " + table + ";"); }

  
  public static boolean createTable(String table, String columns) {
    if (!tableExists(table)) {
      return MySQL.update("CREATE TABLE " + table + " (" + columns + ");");
    }
    return false;
  }
  
  public static boolean upsert(String selected, Object object, String column, String data, String table) {
    if (object != null) {
      object = "'" + object + "'";
    }
    if (data != null) {
      data = "'" + data + "'";
    }
    try {
      ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + "=" + data + ";");
      
      if (rs.next()) {
        MySQL.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + column + "=" + data + ";");
      } else {
        insertData(column + ", " + selected, "'" + data + "', '" + object + "'", table);
      } 
    } catch (Exception exception) {}
    
    return false;
  }
  
  public static boolean set(String selected, Object object, String column, String logic_gate, String data, String table) {
    if (object != null) {
      object = "'" + object + "'";
    }
    if (data != null) {
      data = "'" + data + "'";
    }
    return MySQL.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + column + logic_gate + data + ";");
  }
  
  public static boolean set(String selected, Object object, String[] where_arguments, String table) {
    String arguments = "";
    
    for (String argument : where_arguments) {
      arguments = arguments + argument + " AND ";
    }
    if (arguments.length() <= 5) {
      return false;
    }
    if (object != null) {
      object = "'" + object + "'";
    }
    return MySQL.update("UPDATE " + table + " SET " + selected + "=" + object + " WHERE " + arguments + ";");
  }
  
  public static Object get(String selected, String[] where_arguments, String table) {
    String arguments = "";
    
    for (String argument : where_arguments) {
      arguments = arguments + argument + " AND ";
    }
    if (arguments.length() <= 5) {
      return Boolean.valueOf(false);
    }
    arguments = arguments.substring(0, arguments.length() - 5);
    
    try {
      ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + arguments + ";");
      if (rs.next()) {
        return rs.getObject(selected);
      }
    } catch (Exception exception) {}

    
    return null;
  }
  
  public static ArrayList<Object> listGet(String selected, String[] where_arguments, String table) {
    ArrayList<Object> array = new ArrayList<Object>();
    String arguments = "";
    
    for (String argument : where_arguments) {
      arguments = arguments + argument + " AND ";
    }
    if (arguments.length() <= 5) {
      return array;
    }
    arguments = arguments.substring(0, arguments.length() - 5);
    
    try {
      ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + arguments + ";");
      while (rs.next()) {
        array.add(rs.getObject(selected));
      }
    } catch (Exception exception) {}

    
    return array;
  }
  
  public static Object get(String selected, String column, String logic_gate, String data, String table) {
    if (data != null) {
      data = "'" + data + "'";
    }
    try {
      ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";");
      if (rs.next()) {
        return rs.getObject(selected);
      }
    } catch (Exception exception) {}

    
    return null;
  }
  
  public static ArrayList<Object> listGet(String selected, String column, String logic_gate, String data, String table) {
    ArrayList<Object> array = new ArrayList<Object>();
    
    if (data != null) {
      data = "'" + data + "'";
    }
    try {
      ResultSet rs = MySQL.query("SELECT * FROM " + table + " WHERE " + column + logic_gate + data + ";");
      while (rs.next()) {
        array.add(rs.getObject(selected));
      }
    } catch (Exception exception) {}

    
    return array;
  }
  
  public int countRows(String table) {
    int i = 0;
    if (table == null) {
      return i;
    }
    ResultSet rs = MySQL.query("SELECT * FROM " + table + ";");
    try {
      while (rs.next()) {
        i++;
      }
    } catch (Exception exception) {}

    
    return i;
  }
}