package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.CosechaDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.UserNotFoundException;
import com.jorge.xesmel.model.Cosecha;

public class CosechaDAOImpl implements CosechaDAO{
	
	private static Logger logger = LogManager.getLogger(CosechaDAOImpl.class);

	
	public CosechaDAOImpl() {
		
	}
	
	
	@Override
	public Cosecha findById(Connection c,Long id) throws DataException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Cosecha cosecha = null;
		
		try {
			
			
			String sql = " SELECT id, cantidad, fecha_recogida, colmena_id,tipo_cosecha_id"
									+" FROM cosecha"
									+" WHERE id = ?";
			
			if(logger.isDebugEnabled()) {
			logger.debug("CosechaDAO.findBy:SQL= "+sql);
			}
			preparedStatement = c.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs= preparedStatement.executeQuery();
			
			if (rs.next()) {
				cosecha = loadNext(rs);
			}
		}catch (SQLException sqle) {
			logger.error(id, sqle);
		}finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
			
			return cosecha;	
	}
	@Override
	public List<Cosecha> findBy(Connection c, Long id,Double cantidad,Date fechaRecogida,Long colmenaId,Long tipoCosechaId) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Cosecha> cosechas = null;
		
		try {
			
			StringBuilder sqlSB = new StringBuilder(" SELECT id, cantidad, fecha_recogida, colmena_id,tipo_cosecha_id ")
			
				.append(" FROM cosecha");
			boolean first = true;
			
			if(id!=null) {
				addClause(sqlSB,first," WHERE id = ?");
				first = false;
			}

			if (cantidad!=null) {
				addClause(sqlSB,first," and cantidad = ?");
				first=false;
			}
			if (fechaRecogida!=null) {
				addClause(sqlSB,first," and fecha_recogida = ?");
				first=false;
			}
			if (colmenaId!=null) {
				addClause(sqlSB,first," and colmena_id= ?");
				first=false;
			}
			if (tipoCosechaId!=null) {
				addClause(sqlSB,first," and tipo_cosecha_id = ?");
				first=false;
			}
			
			sqlSB.append(" ORDER BY FECHA_RECOGIDA DESC ");
			
			if(logger.isDebugEnabled()) {
			logger.debug("CosechaDAO.findBy:SQL= "+sqlSB);
			}
			preparedStatement = c.prepareStatement(sqlSB.toString(), 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		int i;

		i = 1;

		JDBCUtils.setParameter(preparedStatement, i++, id);
		JDBCUtils.setParameter(preparedStatement, i++, cantidad);
		JDBCUtils.setParameter(preparedStatement, i++, fechaRecogida);
		JDBCUtils.setParameter(preparedStatement, i++, colmenaId);
		JDBCUtils.setParameter(preparedStatement, i++, tipoCosechaId);

		rs = preparedStatement.executeQuery();

		cosechas = new ArrayList<Cosecha>();

		Cosecha cosecha = null;
		while (rs.next()) {
			cosecha = loadNext(rs);
			cosechas.add(cosecha);
		}

	} catch (SQLException sqle) {			
		logger.error(id,sqle);
	} finally {
		JDBCUtils.close(rs);
		JDBCUtils.close(preparedStatement);
		
	}
	return cosechas;	
	}
	@Override
	public Long create(Connection c, Cosecha co) throws DataException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			
			
			String sql = "insert into cosecha(cantidad, fecha_recogida, colmena_id, tipo_cosecha_id) "
					+ "values (?,?,?,?)";
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;
			
			JDBCUtils.setParameter(preparedStatement, i++, co.getCantidad());
			JDBCUtils.setParameter(preparedStatement, i++, co.getFechaRecogida());
			JDBCUtils.setParameter(preparedStatement, i++, co.getColmenaId());
			JDBCUtils.setParameter(preparedStatement, i++, co.getTipoCosechaId());
			
			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					co.setId(rs.getLong(1));
				}
			}else {
				
		}
	}catch (SQLException sqle) {
		logger.error(co.toString(),sqle);
	}finally {
		JDBCUtils.close(rs);
		JDBCUtils.close(preparedStatement);
		
	}
	return co.getId();
	
}
	
	@Override
	public void deleteById(Connection c, Long id) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {			
			String sql = " delete "
					+ " from cosecha "
					+ " where id = ? ";
			
			if (logger.isDebugEnabled()) {
				logger.debug("CosechaDAO.delete SQL: "+ sql);
			}
			
			preparedStatement = c.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, id); 
			int deletedRows = preparedStatement.executeUpdate();
			if (deletedRows!=1) {
				throw new UserNotFoundException(""+id);
			}
		} catch (SQLException sqle) {
			logger.error(id.toString()+sqle);
			throw new DataException(""+id, sqle);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		
	}
	
	private Cosecha loadNext(ResultSet rs)
		throws SQLException{
		Cosecha cosecha = new Cosecha();
		
		int i = 1;
		cosecha.setId(rs.getLong(i++));
		cosecha.setCantidad(rs.getDouble(i++));
		cosecha.setFechaRecogida(rs.getDate(i++));
		cosecha.setColmenaId(rs.getLong(i++));
		cosecha.setTipoCosechaId(rs.getLong(i++));
		
		return cosecha;
	}
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	

	
}
