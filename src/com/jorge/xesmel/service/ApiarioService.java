package com.jorge.xesmel.service;

import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Apiario;
import com.jorge.xesmel.model.ApiarioCriteria;

public interface ApiarioService {
	
	public List<Apiario> findBy (ApiarioCriteria ac) throws DataException;
	
	public Apiario findById(Long id) throws DataException;
	
	public Apiario findByUsuarioId(Long id) throws DataException; 

	public Long create(Apiario a) throws DataException, ServiceException;

	public void update(Apiario a) throws DataException;

	public void deleteById(Long id) throws DataException;

}
