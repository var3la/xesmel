package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.TipoEventoDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoEvento;

public class TipoEventoDAOImpl implements TipoEventoDAO{
	
	private static Logger logger = LogManager.getLogger(TipoEventoDAOImpl.class);
	
	public TipoEventoDAOImpl() {
		
	}
	
	@Override
	public TipoEvento findById(Connection c,Long id) throws DataException{
		
		
		ResultSet rs = null;
		TipoEvento tipoEvento = null;
		
		PreparedStatement preparedStatement=null;
		try {
			
		
			String sql=" SELECT id, tratamiento_generico "
									+" FROM tipo_evento "
									+" WHERE id = ? ";
			
			if(logger.isDebugEnabled()) {
			logger.debug("TipoEventoDAO.findById:SQL: "+sql);
			}
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				tipoEvento = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		
		return tipoEvento;	
	}
	private TipoEvento loadNext(ResultSet rs) throws SQLException {
		TipoEvento tipoEvento = new TipoEvento();

		int i = 1;
		tipoEvento.setId(rs.getLong(i++));
		tipoEvento.setAlza(rs.getString(i++));
		tipoEvento.setAtaque(rs.getString(i++));
		tipoEvento.setDivisionColmena(rs.getString(i++));
		tipoEvento.setOtroActo(rs.getString(i++));
		tipoEvento.setRevision(rs.getString(i++));
		tipoEvento.setTratamientoGenerico(rs.getString(i++));
		tipoEvento.setVacunacion(rs.getString(i++));
		

		return tipoEvento;
	}

}	
