package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Venta;

public interface VentaDAO {
	
	public Venta findById(Connection c, Long id)throws DataException;

	public Long create(Connection c, Venta v)throws DataException;
	
	public int update(Connection c, Venta v)throws DataException;
	
	}

