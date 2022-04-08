package com.jorge.xesmel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.VentaDAO;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Venta;

public class VentaDAOImpl implements VentaDAO{
	
	private static Logger logger = LogManager.getLogger(VentaDAOImpl.class);
	
	public VentaDAOImpl() {
		
	}
	
	public Venta findById(Connection c,Long id) throws DataException{
		
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Venta venta = null;
		
		try {
			
			String sql=" SELECT id, cantidad, precio_kg, fecha_venta,cosechaId "
							+" FROM venta "
							+" WHERE id = ? ";
			
			if(logger.isDebugEnabled()) {
				logger.debug("VentaDAO.findById SQL: "+ sql);
			}
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				venta = loadNext(rs);
			}

		} catch (SQLException u) {
			logger.error(id, u);
			throw new DataException(toString());
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		
		}
		
		return venta;	
		
	}
	
	@Override
	public Long create(Connection c, Venta v) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			
			String sql =" INSERT INTO VENTA( CANTIDAD , PRECIO_KG, FECHA_VENTA, COSECHA_ID)"
					+ " VALUES (?,?,?,?) ";
			
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, v.getCantidad());
			JDBCUtils.setParameter(preparedStatement, i++, v.getPrecioKg());
			JDBCUtils.setParameter(preparedStatement, i++, v.getFechaVenta());
			JDBCUtils.setParameter(preparedStatement, i++, v.getCosechaId());
			
			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					v.setId(rs.getLong(1));
				}
		}else {
			
		}
			
		}catch (SQLException sqle) {
			logger.error(v, sqle);
			throw new DataException(v.toString());
		}finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	
		
		return v.getId();
	}

	@Override
	public int update(Connection c, Venta v) throws DataException {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			
			String sql= " UPDATE VENTA " + " SET CANTIDAD = ?," + " SET PRECIO_KG = ?,"
						+ " SET FECHA_VENTA = ?," + " SET COSECHA_ID = ?";
			
			if(logger.isDebugEnabled()) {
				logger.debug("VentaDAO.update: ", sql);
			}
			
			preparedStatement = c.prepareStatement(sql);
			
			int i = 1;
			
			JDBCUtils.setParameter(preparedStatement, i++, v.getCantidad());
			JDBCUtils.setParameter(preparedStatement, i++, v.getPrecioKg());
			JDBCUtils.setParameter(preparedStatement, i++, v.getFechaVenta());
			JDBCUtils.setParameter(preparedStatement, i++, v.getCosechaId());
			
			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows != 1) {

			}
		} catch (SQLException e) {
			logger.error(v.getId(), e);
			throw new DataException(v.toString() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			
		}
		return updatedRows;
		
	}
		
	private Venta loadNext(ResultSet rs)throws SQLException{
		Venta venta = new Venta();
			
		int i = 1;
		venta.setId(rs.getLong(i++));
		venta.setCantidad(rs.getDouble(i++));
		venta.setPrecioKg(rs.getDouble(i++));
		venta.setFechaVenta(rs.getDate(i++));
		venta.setCosechaId(rs.getLong(i++));		
		return venta;
		
		}
			
}
