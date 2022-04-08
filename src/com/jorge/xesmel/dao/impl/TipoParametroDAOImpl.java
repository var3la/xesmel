package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.TipoParametroDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoParametro;

public class TipoParametroDAOImpl implements TipoParametroDAO{

	private static Logger logger = LogManager.getLogger(TipoParametroDAOImpl.class);
	
	public TipoParametroDAOImpl() {
		
	}
	
	public TipoParametro findById (Connection c,Long id)throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		TipoParametro tipoParametro = null;

		try {

			String sql=" SELECT id,nombre "
							+" FROM tipo_parametro "
							+" WHERE id= ? ";
			
			if(logger.isDebugEnabled()) {
			logger.debug("TipoParametro.findById:SQL: "+sql);
			}
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				tipoParametro = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return tipoParametro;
		
	}

		private TipoParametro loadNext(ResultSet rs)throws SQLException{
			TipoParametro tipoParametro = new TipoParametro();
			
			int i = 1;
			tipoParametro.setId(rs.getLong(i++));
			tipoParametro.setNombre(rs.getString(i++));
			
			return tipoParametro;
		}

	}

