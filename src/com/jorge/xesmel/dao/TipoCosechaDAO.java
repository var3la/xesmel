package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.TipoCosecha;

public interface TipoCosechaDAO {
	
	public TipoCosecha findById(Connection c, Long id) throws DataException;

}
