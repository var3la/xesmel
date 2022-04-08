package com.jorge.xesmel.service;

import java.util.List;

import com.jorge.xesmel.exception.AlreadyExistsException;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.Colmena;
import com.jorge.xesmel.model.ColmenaCriteria;
import com.jorge.xesmel.model.Results;

public interface ColmenaService {
	
	public Colmena findById (Long id) throws DataException; 
	
	public Results<Colmena> findBy( ColmenaCriteria cc,int startIndex, int pageSize) throws DataException;
	
	public Long create( Colmena co) throws DataException, ServiceException, AlreadyExistsException;
	
	public void update(Colmena co) throws DataException;
	
	public void delete(Long id) throws DataException;

	
}
