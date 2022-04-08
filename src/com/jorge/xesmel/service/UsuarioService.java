package com.jorge.xesmel.service;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.InvalidUserOrPasswordException;
import com.jorge.xesmel.exception.MailException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.exception.UserAlreadyExistsException;
import com.jorge.xesmel.model.*;

public interface UsuarioService {

	/**
	 * Registra un nuevo usuario.
	 * @param u Usuario a registrar, con todos sus datos obligatorios rellenos.
	 * @return Identificador en el sistema del nuevo usuario.
	 * @throws UserAlreadyExistsException Cuando el usuario ya existe en el sistema.
	 * @throws MailException cuando no se envia el mail de confirmacion
	 * @throws ServiceException En otro caso, por ejemplo, cuando alguno(s) de los datos
	 * obligatorios no está cumplimentado o su valor es incorrecto.
	 * @throws DataException 
	 */
	public Long signUp(Usuario u) 
			throws UserAlreadyExistsException, MailException, ServiceException, DataException;
	

	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws InvalidUserOrPasswordException
	 * @throws ServiceException
	 * @throws DataException 
	 */
	public Usuario login(String email, String password)
			throws InvalidUserOrPasswordException, ServiceException, DataException;
	/**
	 * 	
	 * @param u
	 * @throws ServiceException
	 */
	
	public void update(Usuario u) 
			throws ServiceException;
	/**
	 * 
	 * @param id
	 * @throws DataException
	 */
	
	
	public void delete(Long id) throws DataException;
	
	
	// ..
	public Usuario findByEmail(String email)throws DataException, ServiceException;
	
	
	
	public Usuario findById(Long id)throws DataException, ServiceException;
}
