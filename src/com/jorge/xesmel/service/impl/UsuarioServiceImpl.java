package com.jorge.xesmel.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.dao.UsuarioDAO;
import com.jorge.xesmel.dao.impl.UsuarioDAOImpl;
import com.jorge.xesmel.dao.util.ConnectionManager;
import com.jorge.xesmel.dao.util.JDBCUtils;
import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.InvalidUserOrPasswordException;
import com.jorge.xesmel.exception.MailException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.exception.UserAlreadyExistsException;
import com.jorge.xesmel.model.Usuario;
import com.jorge.xesmel.service.MailService;
import com.jorge.xesmel.service.UsuarioService;
import com.jorge.xesmel.service.util.PasswordEncryptionUtil;

public class UsuarioServiceImpl implements UsuarioService{
	
	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	private UsuarioDAO usuarioDAO = null;
	private MailService mailService = null;
	
	public UsuarioServiceImpl() {
		usuarioDAO = new UsuarioDAOImpl();
		mailService = new MailServiceImpl();
		
	}

	@Override
	public Usuario login(String email, String password) throws InvalidUserOrPasswordException, DataException, ServiceException {
		Connection c = null;
		Usuario usuario = null;
		boolean passwordOK = false;
		boolean commitOrRollback = false;

		try {
			c=ConnectionManager.getConnection();

			c.setAutoCommit(false);

			usuario = usuarioDAO.findByEmail(c, email);
			logger.info("Email: "+usuario);

			passwordOK = PasswordEncryptionUtil.checkPassword(password,(usuario==null ? null : usuario.getPassword()));

			if(!passwordOK) {
				throw new InvalidUserOrPasswordException(email);
			}

			commitOrRollback = true;
			logger.info("Comit: "+commitOrRollback);
			
		}catch (SQLException sqle) {
			logger.error(email, sqle);
			throw new ServiceException(email, sqle);
			
		}catch (Exception e) {
			logger.error(email, e);
			throw new ServiceException(email, e);
			
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return usuario;
	}
	
	@Override
	public Long signUp(Usuario u) throws UserAlreadyExistsException, MailException, ServiceException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		Long userId = null;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);

			if (usuarioDAO.findByEmail(c, u.getEmail())!=null) {
				throw new UserAlreadyExistsException(u.getEmail());				
			}
			u.setPassword(PasswordEncryptionUtil.encryptPassword(u.getPassword()));
			userId = usuarioDAO.create(c, u);	


			StringBuilder wellcomeMsgSb = new StringBuilder("Hola! ")			
					.append(u.getNombre())
					.append(" Bienvenid@ ");


			String wellcomeMsg = wellcomeMsgSb.toString(); 

			mailService.sendEmail("xesmelapp@gmail.com","registro",wellcomeMsg,u.getEmail());

			commitOrRollback = true;
			
		} catch (DataException de) { 
			logger.error(u.getNombre(), de);	
			throw de;			
		} catch (MailException me) {
			logger.error(u.getEmail(), me);
			throw me;
		} catch (Exception e) {
			logger.error(u.getNombre(), e);
			throw new ServiceException(e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return userId;
		
	}

	@Override
	public void update(Usuario u) throws ServiceException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		
		try {
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			
			
			usuarioDAO.update(c, u);
			commitOrRollback=true;
			
			} catch (SQLException sqle) {
			logger.error(u.getEmail(), sqle);
			throw new DataException(u.toString());
			} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
			}	
		}

	
	

	@Override
	public void delete(Long id) throws DataException {
		
		Connection c = null;
		boolean commitOrRollback = false;
		
		try {
			c = ConnectionManager.getConnection();
			c.setAutoCommit(false);
			
			usuarioDAO.deleteById(c, id);
			commitOrRollback=true;
		}catch (SQLException sqle) {
			logger.error(id.toString(), sqle);
			throw new DataException(sqle);
		}finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	@Override
	public Usuario findByEmail (String  email) throws DataException ,ServiceException{
		Connection c=null;
		Usuario usuario = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();					
			
			c.setAutoCommit(false);
			
			usuario = usuarioDAO.findByEmail(c, email);
								
			commitOrRollback = true;
			

		} catch (SQLException sqle) {
			logger.error(email, sqle);
			throw new ServiceException(email+"", sqle);
			
		} catch (DataException de) { 
			logger.error(email, de);
			throw de;
			
		} catch (Exception e) {
			logger.error(email, e);
			throw new ServiceException(e);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;	
	}
	
	@Override
	public Usuario findById (Long id) throws DataException, ServiceException {
		Connection c=null;
		Usuario usuario = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();					
			
			c.setAutoCommit(false);
			
			usuario = usuarioDAO.findById(c, id);
								
			commitOrRollback = true;
			

		} catch (SQLException sqle) {
			logger.error(id,sqle);
			throw new ServiceException(id+"", sqle);
			
		} catch (DataException de) { 
			logger.error(id, de);	
			throw de;
			
		} catch (Exception e) {
			logger.error(id,e);
			throw new ServiceException(e);
			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;	
	}
}

