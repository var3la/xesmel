package com.jorge.xesmel.dao;

import java.sql.Connection;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.Analisis;

public interface AnalisisDAO {
	
	public Analisis findByCosechaId(Connection c, Long cosechaId) throws DataException;

	public Long create(Connection c, Analisis analisis) throws DataException;
	
	
}