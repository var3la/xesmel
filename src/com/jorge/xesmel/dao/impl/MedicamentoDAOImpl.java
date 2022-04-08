package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.MedicamentoDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Medicamento;


public class MedicamentoDAOImpl implements MedicamentoDAO {
	
	private static Logger logger = LogManager.getLogger(MedicamentoDAOImpl.class);
	
	public MedicamentoDAOImpl() {
		
	}
	
	@Override
	public Medicamento findById(Connection c, Long id) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Medicamento medicamento = null;
		
		try {
			
		    String sql = " SELECT id,dosis "
						+" FROM medicamento "
						+" WHERE id = ? " ;
		    
		    if(logger.isDebugEnabled()) {
			logger.debug("MedicamentoDAO.findById= "+sql);
		    }
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();
 
			
			if (rs.next()) {
				medicamento = loadNext(rs);
				
				
				}
			
		} catch (SQLException e) {			
			logger.error(id, e);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
			
		return medicamento;	
	}
	
	public Long create(Connection c, Medicamento m)throws DataException{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			
			String sql = " INSERT INTO MEDICAMENTO (DOSIS) "
					+" VALUES (?) ";
			if(logger.isDebugEnabled()) {
				logger.debug("MedicamentoDAO.create SQL: "+sql);
			}
			
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, m.getDosis());
			
			int insertedRows = preparedStatement.executeUpdate();
			if(insertedRows == 1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					m.setId(rs.getLong(1));
				}
			}else {
				throw new DataException(m.getDosis());
			}
		}catch (SQLException sqle) {
			logger.error(m, sqle);
			
		}finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return m.getId();
	}


	
	
	private Medicamento loadNext(ResultSet rs) throws SQLException{
		Medicamento medicamento= new Medicamento();

		int i = 1;
		medicamento.setId(rs.getLong(i++));
		medicamento.setDosis(rs.getString(i++));
		return medicamento;
	}
	}
