package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDB
{
   public static Connection getConnection() throws SQLException, IOException
   {	   
	   String drivers="com.mysql.jdbc.Driver";
	   if(drivers!=null)System.setProperty("jdbc.drivers",drivers );
	   String url="jdbc:mysql://localhost:3306/jumpgame";
	   String username="root";
	   String password="password";	   
	   return DriverManager.getConnection(url,username,password);	  
   }
}