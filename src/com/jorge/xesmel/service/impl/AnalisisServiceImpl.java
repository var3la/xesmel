package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.AnalisisDAO;
import com.jorge.xesmel.dao.impl.AnalisisDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Analisis;
import com.jorge.xesmel.service.AnalisisService;

public class AnalisisServiceImpl implements AnalisisService{
	
	private AnalisisDAO analisisDAO = null;
	
	private static Logger logger = LogManager.getLogger(AnalisisServiceImpl.class);
	
	public AnalisisServiceImpl() {
		analisisDAO = new AnalisisDAOImpl();
		
	}

	@Override
	public Long create(Analisis a) throws DataException, ServiceException {
		
		Connection c = null;
		Long analisisId = null;
		boolean commitOrRollback = false;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			analisisId = analisisDAO.create(c, a);
			commitOrRollback = true;
			
		}catch(DataException de) {
			logger.error(a.getId(),de);
			throw de;
		}catch(Exception e) {
			logger.error(analisisId, e);
			throw new ServiceException(e);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return analisisId;
	}

	@Override
	public Analisis findByCosechaId( Long cosechaId) throws DataException {
		
		Connection c = null;
		Analisis analisis = null;
		boolean commitOrRollback = false;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			analisis = analisisDAO.findByCosechaId(c, cosechaId);
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(cosechaId, sqle);
			throw new DataException(cosechaId.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
			
		return analisis;
	}
	
	

}
