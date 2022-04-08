package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.AnalisisDAO;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Analisis;

public class AnalisisDAOImpl implements AnalisisDAO {
	
	private static Logger logger = LogManager.getLogger(AnalisisDAOImpl.class);
	
	public AnalisisDAOImpl() {
		
	}
	
	@Override
	public Analisis findByCosechaId(Connection c, Long cosechaId) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Analisis analisis = null;

		try {

			c = ConnectionManager.getConnection();

			String sql = "SELECT id, fecha, cosecha_id " + " FROM analisis" + " WHERE cosecha_id = ?";
			
			if(logger.isDebugEnabled()) {
				
				logger.debug("AnalisisDAO.findByCosecha:SQL =" + sql);
			}
			

			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, cosechaId);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				analisis = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error(cosechaId.toString(), sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return analisis;
	}

	@Override
	public Long create(Connection c, Analisis analisis) {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			c = ConnectionManager.getConnection();

			String sql = " insert into analisis(fecha, cosecha_id)" + "values (?,?)";
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, analisis.getFecha());
			JDBCUtils.setParameter(preparedStatement, i++, analisis.getCosechaId());

			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows == 1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					analisis.setId(rs.getLong(1));
				}

			} else {
				

			}
		} catch (SQLException sqle) {
			logger.error(analisis.toString(),sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return analisis.getId();
	}
		
	
	private Analisis loadNext(ResultSet rs) throws SQLException {

		Analisis analisis = new Analisis();

		int i = 1;
		analisis.setId(rs.getLong(i++));
		analisis.setFecha(rs.getDate(i++));
		analisis.setCosechaId(rs.getLong(i++));
		return analisis;

	}

	
	}


