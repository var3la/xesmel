package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.TipoCosechaDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoCosecha;

public class TipoCosechaDAOImpl implements TipoCosechaDAO{
	
	private static Logger logger = LogManager.getLogger(TipoCosechaDAOImpl.class);
	
	public TipoCosechaDAOImpl() {
		
	}
	
public TipoCosecha findById(Connection c, Long id) throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		TipoCosecha tipoCosecha = null;
		
		try {
			
			String sql=" SELECT id, nombre "
						+" FROM tipo_cosecha "
						+" WHERE id = ? ";
			if(logger.isDebugEnabled()) {
			logger.debug("TipoCosechaDAO.findById:SQL= " + sql);
			}
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				tipoCosecha = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}	
		
			
		return tipoCosecha;	
	}
	private TipoCosecha loadNext(ResultSet rs) throws SQLException {
	TipoCosecha tipoCosecha = new TipoCosecha();

	int i = 1;
	tipoCosecha.setId(rs.getLong(i++));
	tipoCosecha.setNombre(rs.getString(i++));
	

	return tipoCosecha;
}
}
