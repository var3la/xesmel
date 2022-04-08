package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.EventoDAO;
import com.jorge.xesmel.dao.impl.EventoDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.AlreadyExistsException;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.EventoCriteria;
import com.jorge.xesmel.model.EventoDTO;
import com.jorge.xesmel.service.EventoService;

public class EventoServiceImpl implements EventoService{
	
	private EventoDAO eventoDAO = null;
	
	private static Logger logger = LogManager.getLogger(EventoServiceImpl.class);
	
	public EventoServiceImpl() {
		
		eventoDAO = new EventoDAOImpl();
	}
	
	
	public EventoDTO findById(Long id)throws ServiceException{
		
		Connection c = null;
		EventoDTO evento = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			
			c.setAutoCommit(false);
			
			evento = eventoDAO.findById(c, id);
			
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return evento;
	}
	
	public List<EventoDTO> findBy(EventoCriteria ec) throws ServiceException, DataException{
		
		Connection c = null;
		List<EventoDTO> eventos = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			
			c.setAutoCommit(false);
			
			eventos = eventoDAO.findBy(c, ec);
			
		}catch (SQLException sqle) {
			logger.error(ec.comentario, sqle);
			throw new DataException(ec.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return eventos;
	}

	@Override
	public Long create(EventoDTO e) throws ServiceException, DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		Long eventoId = null;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			if(eventoDAO.findById(c, e.getId())!=null) {
				throw new AlreadyExistsException(e.toString());
			}
			eventoId = eventoDAO.create(c, e);
			
			commitOrRollback = true;
		}catch (DataException de) {
			logger.error(e.comentario, de);
			throw de;
		}catch(Exception ex) {
			logger.error(e.getComentario(),ex);
			throw new ServiceException(ex);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return eventoId;
	}

	@Override
	public void update(EventoDTO e) throws ServiceException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			eventoDAO.update(c, e);
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(e.comentario, sqle);
			throw new DataException(e.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}

	@Override
	public void delete(Long id) throws ServiceException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			eventoDAO.deleteById(c, id);
			commitOrRollback = true;
			
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}
	
	

}
