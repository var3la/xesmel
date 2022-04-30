package com.jorge.xesmel.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import com.jorge.xesmel.exception.DataException;

public class JDBCUtils {
	
	
		

	public static final void setParameter(PreparedStatement ps, int parameterIndex, Long value) 
			throws SQLException {		
		setParameter(ps, parameterIndex, value, false);
	}

	
	
	public static final void setParameter(PreparedStatement ps, int parameterIndex, Long value, boolean nullable) 
			throws SQLException {
		if (value!=null) {
			ps.setLong(parameterIndex, value);		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.INTEGER);
			} 			
		}
	}
	
	
	public static final void setParameter(PreparedStatement ps, int parameterIndex, Integer value) 
			throws SQLException {		
		if (value!=null) {
			ps.setInt(parameterIndex, value);
		}
	}

	public static void setParameter(PreparedStatement ps, int parameterIndex, String value) 
			throws SQLException {
		if (value!=null) {
			ps.setString(parameterIndex, value);
		}
	}

	public static void setParameter(PreparedStatement ps, int parameterIndex, Date value) 
		throws SQLException {
		if (value!=null) {
			//ps.setDate(parameterIndex, new java.sql.Date(value.getTime()));
			ps.setTimestamp(parameterIndex, new Timestamp(value.getTime()));
		}
		
	}
	
	public static void setParameter(PreparedStatement ps, int parameterIndex, Double value) 
		throws SQLException {
		if(value!=null) {
				ps.setDouble(parameterIndex, value);
			}
		}
	
	
	
	public static final void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	public static final void close(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {					
				e.printStackTrace();
			}
		}
	}
	
	public static final void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {					
				e.printStackTrace();
			}
		}
	}
	/**
	 * Metodo de finalizacion de transaccion <b>para el caso de autocommit = false</b>
	 * y de cierre de conexion.
	 *  
	 * Ejecuta commit() o rollback() y a continuacion cierra la conexi�n.
	 *  
	 * @param connection Conexion a cerrar.
	 * @param commitOrRollback Si es true ejecuta commit() y en caso contrario ejecuta rollback().
	 */
	public static void closeConnection(Connection connection, boolean commitOrRollback)
		throws DataException {
        try {
            if (connection != null) {
                if (commitOrRollback) {
                	connection.commit();
                } else {
                	connection.rollback();                        
                }
                connection.close();
            }
        } catch (SQLException e) {
            throw new DataException(e);
        }
	}


	

	
		
	}



	
