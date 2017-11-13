package com.ming.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqliteUtil {

	public static String driver;

	public static String url;

	static {
		// 读取配置信息
		try {
			Properties properties = new Properties();
			InputStream isStream = new FileInputStream("src/db.properties");
			properties.load(isStream);
			isStream.close();
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			// 注册驱动
			Class.forName(driver);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public static Connection getConnection(){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public static void close(Connection connection,Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e2) {
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e3) {
				}
			}
		}
	}

}
