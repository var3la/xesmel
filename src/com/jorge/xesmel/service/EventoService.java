package com.jorge.xesmel.service;

import java.util.List;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.model.EventoCriteria;
import com.jorge.xesmel.model.EventoDTO;

public interface EventoService {
	
	/**
	 * busqueda de eventos por ID
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public EventoDTO findById (Long id)throws ServiceException;
	/**
	 * busqueda de eventos por varios criterios
	 * @param ec campos de busqueda
	 * @return lista de eventos
	 * @throws ServiceException
	 */
	public List<EventoDTO> findBy(EventoCriteria ec)throws ServiceException;
	/**
	 * crea un nuevo evento
	 * @param e evento a crear
	 * @return evento creado
	 */
	public Long create(EventoDTO e) throws ServiceException, DataException;
	/**
	 * actualiza un evento
	 * @param e evento que se actualiza 
	 * @throws ServiceException
	 */
	public void update (EventoDTO e) throws ServiceException;
	/**
	 * borrado de eventos
	 * @param e
	 * 
	 * @throws ServiceException
	 */
	public void delete (Long id)throws ServiceException;
}
