package com.jorge.xesmel.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.exception.DataException;
import com.jorge.xesmel.exception.MailException;
import com.jorge.xesmel.exception.ServiceException;
import com.jorge.xesmel.exception.UserAlreadyExistsException;
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
		logger.info("usuario encontrado");
	}

	public void testUsuarioLogin() {
        logger.trace("Begin...");

        
        String email = "manuela1644439835708@gmail.comv";
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
		
		long num = System.currentTimeMillis();
		usuario.setNombre("manuela");
		usuario.setApellido("gonzalez");
		usuario.setSegundoApellido("gonzalez");
		usuario.setNombreComercial("boamel");
		usuario.setDni("99999959j");
		usuario.setTelefono("777888999");
		usuario.setEmail("xesmelapp@gmail.com");
		usuario.setPassword("manuela");		
		usuario.setRega("testprueba");
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
	
	public void testDelete() {
		
		logger.trace("testing delete....");
		usuario = new Usuario();
		Long id=null;
		
		usuario.setId(37L);
		try {
		
			usuarioService.delete(id);
		}catch (ServiceException sqle) {
			logger.error(sqle);
			logger.trace("El usuario no se puede borrar.");
		}
		
	}
	
	
	public static void main(String args[]) {
		UsuarioServiceTest test = new UsuarioServiceTest();
		test.testSignUp();
		//test.testDelete();
		//test.testFind();
		//test.testUsuarioLogin();
	}

}
