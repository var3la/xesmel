package com.jorge.xesmel.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.service.MailService;
import com.jorge.xesmel.service.impl.MailServiceImpl;

public class MailServiceTest {
	
	private static Logger logger = LogManager.getLogger(MailServiceTest.class);
	
	private MailService mailService = null;
	
	private MailServiceTest() {
		
		mailService = new MailServiceImpl();
	}
	
	public void testSendMail() {
		
		try {
			String from = "no-reply@gmail.com";
			String to = "xesmelapp@gmail.com";
			mailService.sendEmail(from, "Test", "Prueba mailService", to);
			
			logger.fatal("Mail a "+to+" enviado con éxito.");
		}catch (Exception e) {
			logger.error( e);
		}
	}
	public static void main (String args[]) {
		MailServiceTest test = new MailServiceTest();
		test.testSendMail();
	}

}
