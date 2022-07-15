package com.crud;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
	public Connection getconnection() {
		String url = "jdbc:mysql://localhost:3306/grey_goose";
		String userName = "root";
		String password = "root";
		Connection connect = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connect;
	}
}