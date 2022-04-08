package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.CosechaDAO;
import com.jorge.xesmel.dao.impl.CosechaDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.UserAlreadyExistsException;
import com.jorge.xesmel.model.Cosecha;
import com.jorge.xesmel.service.CosechaService;

public class CosechaServiceImpl implements CosechaService{
	
	private CosechaDAO cosechaDAO = null;
	
	private static Logger logger=LogManager.getLogger(CosechaService.class);
	
	public CosechaServiceImpl() {
		
		cosechaDAO = new CosechaDAOImpl();
		
	}

	@Override
	public Cosecha findById(Long id) throws DataException {
		
		Connection c = null;
		Cosecha cosecha = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			cosecha = cosechaDAO.findById(c, id);
			
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return cosecha;
	}

	@Override
	public List<Cosecha> findBy(Long id, Double cantidad, Date FechaRecogida, Long colmenaId, Long tipoCosechaId)
			throws DataException {
		
		Connection c = null;
		List<Cosecha> cosechas = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			cosechas = cosechaDAO.findBy(c, id, cantidad, FechaRecogida, colmenaId, tipoCosechaId);
					
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return cosechas;
	}

	@Override
	public Long create(Cosecha co) throws DataException {
		
		Connection c = null;
		Long cosechaId = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			if(cosechaDAO.findById(c, co.getId())!=null) {
				throw new UserAlreadyExistsException(co.getId()+"cosecha exists");
			}
			cosechaId = cosechaDAO.create(c, co);
			commitOrRollback = true;
		}catch (DataException de) {
			logger.error(co.getId(), de);
			throw de;
		}catch(Exception e) {
			logger.error(co.getId(), e);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return cosechaId;
	}

	@Override
	public void deleteById(Long id) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			cosechaDAO.deleteById(c, id);
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}

}








