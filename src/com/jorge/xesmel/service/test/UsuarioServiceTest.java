package com.jorge.xesmel.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.MailException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.exception.UserAlreadyExistsException;
import com.jorge.xesmel.model.Apiario;
import com.jorge.xesmel.model.Usuario;
import com.jorge.xesmel.service.UsuarioService;
import com.jorge.xesmel.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {
	
	private static Logger logger = LogManager.getLogger(UsuarioServiceTest.class);
	private UsuarioService usuarioService = null;
	private Usuario usuario = null;
	public UsuarioServiceTest() {
		
		usuarioService = new UsuarioServiceImpl();
	}
	
	public void testFind(){
		logger.trace("testing find....");
		usuario = new Usuario();
		
		usuario.setEmail("sofia@gmail.com");
		logger.info("buscando usuario..."+usuario.getEmail());
		try {
			usuarioService.findByEmail(usuario.getEmail());
			
		}catch (DataException de) {
			logger.error(de);
		}catch (Exception e) {
			
		}
		logger.info("usuario "+usuario.getEmail()+" encontrado");
	}

	public void testUsuarioLogin() {
        logger.trace("Begin...");

        
        String email = "xesmelapp@gmail.com";
        String password = "manuela";
        
        try {

            usuario = usuarioService.login(email, password);

            if (usuario != null) {
                logger.info("Bienvenido a Xesmel ");
            } else {
                logger.info("Usuario o contraseña incorrecta ");
            }

            logger.info("End!");
        } catch(DataException de) {
            logger.error(usuario, de);
        }  catch(ServiceException se){
            logger.error(usuario, se);
        }
    }
	
	public void testSignUp() {
		logger.trace("Testing signUp...");
		usuario = new Usuario();
		
		//long num = System.currentTimeMillis();
		usuario.setNombre("Martina");
		usuario.setApellido("gonzalez");
		usuario.setSegundoApellido("gonzalez");
		usuario.setNombreComercial("moitamel");
		usuario.setDni("34999959j");
		usuario.setTelefono("777888999");
		usuario.setEmail("jgvarela79@hotmail.com");
		usuario.setPassword("manuela");		
		usuario.setRega("ES274567895");
		logger.trace("Creando usuario "+usuario.getEmail());
		try {
			
			usuarioService.signUp(usuario);
			
		} catch (UserAlreadyExistsException uae) {		
			logger.trace("El usuario ya existe.");
			logger.error(usuario.getEmail()+ uae);
		} catch (MailException me) {
			logger.trace("Ha habido un problema al enviar el e-mail.");
			logger.error(usuario.getEmail()+me);
		} catch (ServiceException se) {
			logger.error(se.getMessage());
		}
	}
	
	public void testUpdate() {
		logger.trace("Testing Update...");
		usuario = new Usuario();
		usuario.setId(1L);
		//long num = System.currentTimeMillis();
		usuario.setNombre("Martina");
		usuario.setApellido("gonzalez");
		usuario.setSegundoApellido("gonzalez");
		usuario.setNombreComercial("moitamel");
		usuario.setDni("34999959j");
		usuario.setTelefono("222222222");
		usuario.setEmail("jgvarela79@hotmail.com");
		usuario.setPassword("manuela");		
		usuario.setRega("ES2745674309");
		logger.trace("Actualizando usuario "+usuario.getEmail());
		try {
			
			usuarioService.update(usuario);
			
		} catch (UserAlreadyExistsException uae) {		
			logger.trace("El usuario ya existe.");
			logger.error(usuario.getEmail()+ uae);
		} catch (MailException me) {
			logger.trace("Ha habido un problema al enviar el e-mail.");
			logger.error(usuario.getEmail()+me);
		} catch (ServiceException se) {
			logger.error(se.getMessage());
		}
	}
	
	public void testDelete() {
		
		logger.trace("testing delete....");
		usuario = new Usuario();
		Long id=null;
		
		usuario.setId(10L);
		try {
		
			usuarioService.delete(id);
		}catch (ServiceException sqle) {
			logger.error(sqle);
			logger.trace("El usuario no se puede borrar.");
		}
		
	}
	
public void testFindbyId() {
		
		logger.trace("testing delete....");
		usuario = new Usuario();
		
		
		
		try {
		
			usuario = usuarioService.findById(5L);
			logger.error(usuario);
		}catch (ServiceException sqle) {
			logger.error(sqle);
			logger.trace("El usuario no se puede borrar.");
		}
		
	}
	

	
	
	public static void main(String args[]) {
		UsuarioServiceTest test = new UsuarioServiceTest();
		test.testFindbyId();
		//test.testSignUp();
		//test.testDelete();
		//test.testFind();
		//test.testUsuarioLogin();
		//test.testUpdate();
	}

}
