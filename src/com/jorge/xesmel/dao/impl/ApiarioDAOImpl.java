package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.ApiarioDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.UserNotFoundException;
import com.jorge.xesmel.model.Apiario;
import com.jorge.xesmel.model.ApiarioCriteria;




public class ApiarioDAOImpl implements ApiarioDAO{
	
	private static Logger logger = LogManager.getLogger(ApiarioDAOImpl.class);
	
	public ApiarioDAOImpl() {
		
	}
	
	
	
	@Override
	public Apiario findById(Connection c, Long id) throws DataException{
		
	
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Apiario apiario = null;
	
		try {					
			String sql = " SELECT id, nombre, ubicacion, latitud, longitud, usuario_id "
									+" FROM apiario "
 									+" WHERE id = ? ";

			
			preparedStatement = c.prepareStatement(sql);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				apiario = loadNext(rs);
			}

		} catch (SQLException sqle) {
			logger.error("findById: "+id+": "+sqle.getMessage(), sqle);
			throw new DataException("findById: "+id+": "+sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return apiario;
	}
	
	
	
	
	
	
	
	@Override
	public List<Apiario> findBy(Connection c, ApiarioCriteria ac) throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Apiario> apiarios = null;
		try {
	
			
			StringBuilder sqlSB = new StringBuilder(" SELECT id, nombre, ubicacion, latitud, longitud, usuario_id")
					.append(" FROM apiario ");
			
			boolean first = false;
			
			if(ac.getId()!=null) {
				addClause(sqlSB, first," WHERE id = ? ");
				first = false;
			}

			if (ac.getNombre()!=null) {
				addClause(sqlSB, first," and nombre = ? ");
				first = false;
			}
			if (ac.getUbicacion()!=null) {
				addClause(sqlSB, first," and ubicacion = ? ");
				first = false;
			}
			if (ac.getLatitud()!=null) {
				addClause(sqlSB, first," and latitud = ? ");
				first = false;
			}
			if (ac.getLongitud()!=null) {
				addClause(sqlSB, first," and longitud = ? ");
				first = false;
			}
			if (ac.getUsuarioId()!=null) {
				addClause(sqlSB, first," and usuario_id = ? ");
				first = false;
			}
			
			if(logger.isDebugEnabled()) {
				logger.debug("ApiarioDAO.findBy:SQL= "+sqlSB);
			}
			
			
			
			preparedStatement = c.prepareStatement(sqlSB.toString(), 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		int i;

		i = 1;

		JDBCUtils.setParameter(preparedStatement, i++, ac.getId());
		JDBCUtils.setParameter(preparedStatement, i++, ac.getNombre());
		JDBCUtils.setParameter(preparedStatement, i++, ac.getUbicacion());
		JDBCUtils.setParameter(preparedStatement, i++, ac.getLatitud());
		JDBCUtils.setParameter(preparedStatement, i++, ac.getLongitud());
		JDBCUtils.setParameter(preparedStatement, i++, ac.getUsuarioId());

		rs = preparedStatement.executeQuery();

		apiarios = new ArrayList<Apiario>();

		Apiario apiario = null;
		while (rs.next()) {
			apiario = loadNext(rs);
			apiarios.add(apiario);
		}

	} catch (SQLException e) {			
		logger.error(apiarios, e);
	} finally {
		JDBCUtils.close(rs);
		JDBCUtils.close(preparedStatement);
	
	}
	return apiarios;	
}
	
	public List<Apiario> findByUsuario(Connection c, Long id) throws DataException {
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Apiario> apiarios = null;
		Apiario apiario = null;
	
		try {
	
			String sql = " select u.nombre, u.nombre_comercial, a.id, a.nombre, a.ubicacion " + " from usuario u "
					+ " inner join apiario a on u.id = a.usuario_id  " + " where u.id = ? ";
	
			if (logger.isDebugEnabled()) {
	
				logger.debug("ApiarioDAO.findByIdUsuario:" + sql);
			}
	
			preparedStatement = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
	
			int i=1;
	
			JDBCUtils.setParameter(preparedStatement, i++, id);
	
			rs = preparedStatement.executeQuery();
	
			apiarios = new ArrayList<Apiario>();
	
			
			while (rs.next()) {
				apiario = loadNext(rs);
				apiarios.add(apiario);
			}
	
		} catch (SQLException e) {
			logger.error(apiarios, e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
	
		}
		return apiarios;
	}
	
	@Override
	public Long create(Connection c, Apiario apiario) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
		
			
			String sql =" insert into apiario( nombre, ubicacion, latitud, longitud, usuario_id )"
					+ " values ( ? , ? , ? , ? , ?) ";
			
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getUbicacion());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getUsuarioId());
			
			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					apiario.setId(rs.getLong(1));
				}
				
				}else {
					throw new DataException(apiario.getNombre());
				}
			
		} catch (SQLException e) {			
			logger.error(apiario, e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return apiario.getId();
	}
	@Override
	public int update(Connection c, Apiario apiario) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {

			
			
			String sql ="Update apiario "
					+ " set nombre = ?, "
					+ " ubucacion = ?, "
					+ " latitud = ?,"
					+ " longitud = ?,"
					+ " usuario_id = ? "
					+ " where id = ? ";
			
			preparedStatement = c.prepareStatement(sql);
			
			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getUbicacion());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getUsuarioId());
			JDBCUtils.setParameter(preparedStatement, i++, apiario.getId());
			
			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					apiario.setId(rs.getLong(1));
				}
				
				}else {
					
				}
			
		} catch (SQLException e) {			
			logger.error(apiario,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		return updatedRows;
	}
	public void deleteById(Connection c, Long apiarioId) throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			
			String sql =" delete "
					+" from apiario "
					+" where id = ? ";
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, apiarioId);
			int deleteRows = preparedStatement.executeUpdate();
			if (deleteRows!=1) {
				throw new UserNotFoundException("apiario: "+apiarioId);
			}
		}catch(SQLException sqle) {
			logger.error(apiarioId, sqle);
			throw new DataException("apiario: "+apiarioId,sqle);
		}finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		
	}

	
	private static Apiario loadNext(ResultSet rs) 
		throws SQLException {
			Apiario apiario = new Apiario();
			
			int i = 1;
			apiario.setId(rs.getLong(i++));
			apiario.setNombre(rs.getString(i++));
			apiario.setUbicacion(rs.getString(i++));
			apiario.setLatitud(rs.getDouble(i++));
			apiario.setLongitud(rs.getDouble(i++));
			apiario.setUsuarioId(rs.getLong(i++));
			return apiario;
	}

	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	


	

}