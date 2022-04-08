package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.VentaDAO;
import com.jorge.xesmel.dao.impl.VentaDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.AlreadyExistsException;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Venta;
import com.jorge.xesmel.service.VentaService;

public class VentaServiceImpl implements VentaService{
	
	private VentaDAO ventaDAO = null;
	
	private static Logger logger = LogManager.getLogger(VentaServiceImpl.class);
	
	public VentaServiceImpl() {
		
		ventaDAO = new VentaDAOImpl();
	}
	
	@Override
	public Venta findById(Long id) throws DataException{
		
		Connection c = null;
		Venta venta = null;
		
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			venta = ventaDAO.findById(c, id);
			commitOrRollback = true;
		}catch (SQLException sqle) {
			logger.error(id, sqle);
			throw new DataException(id.toString());
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return venta;
	}

	@Override
	public Long create(Venta v) throws DataException, ServiceException {
		
		Connection c = null;
		Long ventaId = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			if(ventaDAO.findById(c, v.getId())!=null) {
				throw new AlreadyExistsException(v.toString());
			}
			ventaId = ventaDAO.create(c, v);
			
			commitOrRollback = true;
			
		}catch (DataException de) {
			logger.error(v.getId(),de);
			throw de;
		}catch (Exception e) {
			logger.error(v.toString(),e);
			throw new ServiceException(e);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
				
		return ventaId;
	}

	@Override
	public void update(Venta v) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			
			c=ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			ventaDAO.update(c, v);
			commitOrRollback=true;
			
		}catch(SQLException sqle) {
			logger.error(v.toString(),sqle);
			throw new DataException(v.toString());
		}finally {
		JDBCUtils.closeConnection(c, commitOrRollback);
		}		
		
	}
}
