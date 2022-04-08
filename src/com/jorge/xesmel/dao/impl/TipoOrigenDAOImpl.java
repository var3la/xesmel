package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.TipoOrigenDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoOrigen;

public class TipoOrigenDAOImpl implements TipoOrigenDAO{
	
	private static Logger logger = LogManager.getLogger(TipoOrigenDAOImpl.class);
	
	public TipoOrigenDAOImpl() {
		
	}
	
	public TipoOrigen findById (Connection c,Long id) throws DataException{

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		TipoOrigen tipoOrigen = null;

		try {

			String sql= " SELECT id,nombre "
							+" FROM tipo_origen "
							+" WHERE id= ? ";
			
			if(logger.isDebugEnabled()) {
			logger.debug("TipoOrigenDAO.findById:SQL "+sql);
			}
			preparedStatement =c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				tipoOrigen = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return tipoOrigen;
	}
		
		private TipoOrigen loadNext(ResultSet rs)throws SQLException{
			
			TipoOrigen tipoOrigen = new TipoOrigen();
			int i = 1;		
			tipoOrigen.setId(rs.getLong(i++));
			tipoOrigen.setNombre(rs.getString(i++));

			return tipoOrigen;	




	}


}
