package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.ApiarioDAO;
import com.jorge.xesmel.dao.impl.ApiarioDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Apiario;
import com.jorge.xesmel.model.ApiarioCriteria;
import com.jorge.xesmel.service.ApiarioService;

public class ApiarioServiceImpl implements ApiarioService{
	
	private ApiarioDAO apiarioDAO = null;
	
	private static Logger logger = LogManager.getLogger(ApiarioServiceImpl.class);
	
	public ApiarioServiceImpl() {
		
		apiarioDAO = new ApiarioDAOImpl();
		
	}
	
	public List<Apiario> findBy( ApiarioCriteria ac) throws DataException {
		
		Connection c = null;
		List <Apiario> apiarios = null;
		boolean commitOrRollback = false;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			apiarios = apiarioDAO.findBy(c, ac);
			
		}catch (SQLException sqle) {
			logger.error(ac.getNombre(), sqle);
			throw new DataException(ac.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return apiarios;
	}
	
	
	
	@Override
	public Apiario findById(Long id)throws DataException{
		
		Connection c = null;
		Apiario apiario = null;
		
		boolean commitOrRollback = false;
		
		try {
			
			c = ConnectionManager.getConnection();
			
			c.setAutoCommit(false);
			
			apiario = apiarioDAO.findById(c, id);
			
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return apiario;
	}
	@Override
	public List<Apiario> findByUsuarioId(Long id) throws DataException {
		
		Connection c = null;
		List <Apiario> apiarios = null;
		boolean commitOrRollback = false;
		
		try {
			
			c = ConnectionManager.getConnection();
			
			c.setAutoCommit(false);
			
			apiarios = apiarioDAO.findByUsuario(c, id);
			
			commitOrRollback = false;
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return apiarios;
	}
	
	

	@Override
	public Long create(Apiario a) throws DataException, ServiceException {
		
		Connection c = null;
		Long apiarioId = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			
			c.setAutoCommit(false);
			
			/*if(apiarioDAO.findById(c, a.getId())!=null) {
				throw new UserAlreadyExistsException(a.toString());
			}*/
			apiarioId = apiarioDAO.create(c, a);
			commitOrRollback = true;
		}catch (DataException de) {
			logger.error(a.getNombre(),de);
			throw de;
		}catch(Exception e) {
			logger.error(a.getNombre(), e);
			throw new ServiceException(e);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return apiarioId;
	}

	@Override
	public void update(Apiario a) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			apiarioDAO.update(c, a);
			commitOrRollback = true;
			
		}catch(SQLException sqle) {
			logger.error(a.getNombre(),sqle);
			throw new DataException(a.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}

	@Override
	public void deleteById(Long id) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			apiarioDAO.deleteById(c, id);
			commitOrRollback = true;
		}catch(SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}
	
	

}
