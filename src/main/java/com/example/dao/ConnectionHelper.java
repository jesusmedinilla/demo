package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHelper
{
	private String url;
	private static ConnectionHelper instance;

	// De momento voy a usar la BBDD de cellar que tengo en MySQL -> Migrar a MongoDB
	private ConnectionHelper()
	{
    	String driver = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//url = "jdbc:mysql://localhost/cellar?user=root";
            //ResourceBundle bundle = ResourceBundle.getBundle("config");
            driver = "com.mysql.jdbc.Driver"; //bundle.getString("jdbc.driver");
            Class.forName(driver);
            url="jdbc:mysql://localhost/cellar?user=root"; //bundle.getString("jdbc.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		try {
			return DriverManager.getConnection(instance.url);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}