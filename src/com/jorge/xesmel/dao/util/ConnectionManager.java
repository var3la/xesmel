package com.jorge.xesmel.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.configuration.ConfigurationManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager {

	private static Logger logger = LogManager.getLogger(ConnectionManager.class);

	private static final String DB = "db.";
	private static final String DRIVER = DB + "driver";
	private static final String URL = DB + "url";
	private static final String USER = DB + "user";
	private static final String PASSWORD = DB + "password";

	private static String DRIVER_CLASS = null;
	private static String DB_URL = null;
	private static String DB_USER = null;
	private static String DB_PASSWORD = null;

	private static ComboPooledDataSource dataSource = null;


	static {
		DRIVER_CLASS = ConfigurationManager.getInstance().getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, DRIVER);
		DB_URL = ConfigurationManager.getInstance().getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, URL);
		DB_USER = ConfigurationManager.getInstance().getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, USER);
		DB_PASSWORD = ConfigurationManager.getInstance().getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, PASSWORD);
		try {

			Class.forName(DRIVER_CLASS);

			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass(DRIVER_CLASS);
			dataSource.setJdbcUrl(DB_URL);
			dataSource.setUser(DB_USER);
			dataSource.setPassword(DB_PASSWORD);

		}catch (Throwable t) {
			logger.fatal("Unable to load db driver class: "+DRIVER_CLASS);
			System.exit(0);
		}
	}
	public static Connection getConnection() throws SQLException {
		try {
			// Sin pool ...
			// Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Connection con = dataSource.getConnection(); 
			return con;
		}  catch (SQLException sqle) {
			logger.fatal(sqle);
			throw sqle;
		}
	}

}