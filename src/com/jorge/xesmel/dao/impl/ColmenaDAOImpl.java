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

import com.jorge.xesmel.dao.ColmenaDAO;
import com.jorge.xesmel.dao.util.DAOUtils;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.UserNotFoundException;
import com.jorge.xesmel.model.Colmena;
import com.jorge.xesmel.model.ColmenaCriteria;
import com.jorge.xesmel.model.Results;


public class ColmenaDAOImpl implements ColmenaDAO {
	
	private static Logger logger = LogManager.getLogger(ColmenaDAOImpl.class);
	
	public ColmenaDAOImpl() {
		
	}

	
	@Override	
	public Colmena findById(Connection c, Long id) throws DataException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Colmena colmena = null;
		
		try {
			
			
			String sql = "SELECT id, cod_en_apiario,fecha_alta, fecha_baja,apiario_id,tipo_origen_id"
									+" FROM colmena"
									+" WHERE id =  ?";
			
			if(logger.isDebugEnabled()) {
			logger.debug("ColmenaDAO.findBy:SQL= "+sql);
			}
			
			preparedStatement = c.prepareStatement(sql,
			ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);			
			rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				colmena = loadNext(rs);
			}
			
		} catch (SQLException e) {			
			logger.error(colmena, e);
			throw new DataException(id.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return colmena;
	}
	
		
	@Override
	public Results<Colmena> findBy(Connection c, ColmenaCriteria cc, int startIndex, int pageSize) throws DataException{

	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	Results<Colmena> results = null;
	
	try {

		
		StringBuilder sqlSB = new StringBuilder(" SELECT id, cod_en_apiario,fecha_alta,fecha_baja,apiario_id,tipo_origen_id " +

				" FROM colmena ");

		boolean first = true;
		
		if(cc.getId()!=null) {
			addClause(sqlSB, first, " WHERE id = ? ");
			first = false;
		}

		if (cc.getCodEnApiario()!=null) {
			addClause(sqlSB, first, " and cod_en_apiario= ? ");
			first = false;
		}
		if (cc.getFechaAlta()!=null) {
			addClause(sqlSB, first, " and fecha_alta = ? ");
			first = false;
		}
		if (cc.getFechaBaja()!=null) {
			addClause(sqlSB, first, " and fecha_baja = ? ");
			first = false;
		}
		if (cc.getApiarioId()!=null) {
			addClause(sqlSB, first, " and apiario_id = ? ");
			first = false;
		}
		if (cc.getTipoOrigenId()!=null) {
			addClause(sqlSB, first, " and tipo_origen_id = ? ");
			first = false;
		}
	
		
		if(logger.isDebugEnabled()) {
		logger.error("ColmenaDAO.findBy:SQL= "+sqlSB);
		}
		
		preparedStatement = c.prepareStatement(sqlSB.toString(), 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		int i=1;

		JDBCUtils.setParameter(preparedStatement, i++, cc.getId());
		JDBCUtils.setParameter(preparedStatement, i++, cc.getCodEnApiario());
		JDBCUtils.setParameter(preparedStatement, i++, cc.getFechaAlta());
		JDBCUtils.setParameter(preparedStatement, i++, cc.getFechaBaja());
		JDBCUtils.setParameter(preparedStatement, i++, cc.getApiarioId());
		JDBCUtils.setParameter(preparedStatement, i++, cc.getTipoOrigenId());
		
		rs = preparedStatement.executeQuery();

		List<Colmena> colmenas = new ArrayList<Colmena>();
		Colmena colmena = null;
		
		int resultsLoaded = 0;
		if ((startIndex>=1) && rs.absolute(startIndex)) {
			do {
				colmena = loadNext (rs);
				colmenas.add(colmena);
				resultsLoaded++;
			}while (resultsLoaded<pageSize && rs.next());
		}
		
		results.setData(colmenas);
		results.setTotal(DAOUtils.getTotalRows(rs));
		
	} catch (SQLException e) {			
		logger.error("findByCriteria: "+cc+ e.getMessage());
		throw new DataException("findByCriteria: "+cc+ e.getMessage());
	} finally {
		JDBCUtils.close(rs);
		JDBCUtils.close(preparedStatement);
	
	}
	return results;	
}
	@Override
	public  Long create(Connection c, Colmena colmena) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {

			

			String sql = " INSERT INTO COLMENA(COD_EN_APIARIO, FECHA_ALTA, FECHA_BAJA, APIARIO_ID, TIPO_ORIGEN_ID) "
					+ " VALUES (?,?,?,?,?) ";

			
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getCodEnApiario());				
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getFechaAlta());
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getFechaBaja());
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getApiarioId()); 
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getTipoOrigenId());


			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					colmena.setId(rs.getLong(1));
			}
			
			
			}
		
		} catch (SQLException e) {			
			logger.error(colmena, e);
			throw new DataException(colmena.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return colmena.getId();
	}
	@Override
	public int update(Connection c, Colmena colmena) throws DataException{
	
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {

			

			String sql =" UPDATE COLMENA "
					+ " SET  COD_EN_APIARIO = ?,"
					+ "      FECHA_ALTA = ?,"
					+ "      FECHA_BAJA = ?,"
					+ "      APIARIO_ID = ?,"
					+ "      TIPO_ORIGEN_ID = ? "
					+ "  WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getCodEnApiario());				
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getFechaAlta());
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getFechaBaja());
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getApiarioId()); 
			JDBCUtils.setParameter(preparedStatement, i++, colmena.getTipoOrigenId());
			

			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				
			}
		
		} catch (SQLException e) {			
			logger.error(colmena,e);
			throw new DataException(colmena.toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return updatedRows;

	}
	@Override
	public void delete(Connection c, Long id) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			String sql = " delete "
					+" from colmena "
					+" where id = ? ";
			preparedStatement = c.prepareStatement(sql, 
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			JDBCUtils.setParameter(preparedStatement, 1, id);
			int deleteRows = preparedStatement.executeUpdate();
			if (deleteRows!=1) {
				throw new UserNotFoundException(""+id);
			}
		
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(""+id, sqle);
		}finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		
		
	}
	
	

	private Colmena loadNext(ResultSet rs) 
			throws SQLException { 
			Colmena colmena = new Colmena();

			int i = 1;
			colmena.setId(rs.getLong(i++));
			colmena.setCodEnApiario(rs.getLong(i++));
			colmena.setFechaAlta(rs.getDate(i++));
			colmena.setFechaBaja(rs.getDate(i++));
			colmena.setApiarioId(rs.getLong(i++));
			colmena.setTipoOrigenId(rs.getLong(i++));
			return colmena;
		}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
}
