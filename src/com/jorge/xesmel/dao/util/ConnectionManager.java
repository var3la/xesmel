package com.jorge.xesmel.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {
	
	private static Logger logger = LogManager.getLogger(ConnectionManager.class);

    public static Connection getConnection() throws SQLException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/xesmel", "root", "jorge");
            return con;
        }catch (SQLException sqle) {
        	logger.fatal(sqle);
        	throw sqle;
        } catch (ClassNotFoundException cnfe) {
            logger.fatal(cnfe);
            throw new UnknownError(cnfe.getMessage());
        }

    }

}