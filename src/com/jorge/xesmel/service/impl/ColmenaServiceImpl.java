package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.ColmenaDAO;
import com.jorge.xesmel.dao.impl.ColmenaDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.AlreadyExistsException;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Colmena;
import com.jorge.xesmel.model.ColmenaCriteria;
import com.jorge.xesmel.model.Results;
import com.jorge.xesmel.service.ColmenaService;

public class ColmenaServiceImpl implements ColmenaService{
	
	private ColmenaDAO colmenaDAO = null;
	
	private static Logger logger = LogManager.getLogger(ColmenaServiceImpl.class);
	
	public ColmenaServiceImpl() {
		
		colmenaDAO = new ColmenaDAOImpl();
	}
	
	
	@Override
	public Colmena findById(Long id) throws DataException {
	
		Connection c = null;
		Colmena colmena = null;
		
		boolean commitOrRollback = false;
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			colmena = colmenaDAO.findById(c, id);
			commitOrRollback = true;
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return colmena;
	}
	
	@Override
	public Results<Colmena> findBy(ColmenaCriteria cc, int startIndex, int pageSize) throws DataException{
		logger.info("searching colmena criteria: "+cc);
		Connection c = null;
		Results<Colmena> results = null;
		boolean commitOrRollback = false;
		
		try {
			c=ConnectionManager.getConnection();
			
			c.setAutoCommit(false);
			
			results = colmenaDAO.findBy(c, cc, startIndex, pageSize);
			
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(cc.toString(),sqle);
			throw new DataException(cc.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return results;
	}
	

	@Override
	public Long create(Colmena co) throws DataException, ServiceException, AlreadyExistsException{
		
		Connection c = null;
		boolean commitOrRollback = false;
		Long colmenaId = null;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			if (colmenaDAO.findById(c, co.getId())!=null) {
				throw new AlreadyExistsException(co.toString());
			}
			colmenaId = colmenaDAO.create(c, co);
			
			commitOrRollback = true;
		}catch (DataException de) {
			logger.error(colmenaId, de);
			throw de;
		}catch(Exception e) {
			logger.error(colmenaId, e);
			throw new ServiceException(e);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return colmenaId;
	}

	@Override
	public void update(Colmena co) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			colmenaDAO.update(c, co);
			commitOrRollback=true;
			
		}catch(SQLException sqle) {
			logger.error(co.toString(),sqle);
			throw new DataException(co.toString());
		}finally {
		JDBCUtils.closeConnection(c, commitOrRollback);
		}		
	}

	

	@Override
	public void delete(Long id) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			colmenaDAO.delete(c, id);
			commitOrRollback = true;
			
		}catch(SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}


	

	
	

}
