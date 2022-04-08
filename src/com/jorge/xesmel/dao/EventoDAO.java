package com.jorge.xesmel.dao;

import java.sql.Connection;
import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.model.EventoCriteria;
import com.jorge.xesmel.model.EventoDTO;

public interface EventoDAO {
	
	public EventoDTO findById(Connection c,Long id) throws DataException;
	
	public List<EventoDTO> findBy(Connection c,EventoCriteria ec) throws DataException;
	
	public Long create(Connection c, EventoDTO e) throws DataException;
	
	public int update(Connection c, EventoDTO e) throws DataException;
	
	public void deleteById(Connection c, Long id) throws DataException;
	
}
