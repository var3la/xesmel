package com.jorge.xesmel.service.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.exception.MailException;
import com.jorge.xesmel.service.MailService;

public class MailServiceImpl implements MailService{
	
	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	public MailServiceImpl() {
		
	}

	@Override
	public void sendEmail(String from, String subject, String text, String... to) throws MailException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Sending from "+from+" to "+to+"...");
			}
			
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("xesmelapp@gmail.com", "chantada"));
		email.setSSLOnConnect(true);
		email.setFrom(from);
		email.setSubject(subject);
		email.setMsg(text);
		email.addTo(to);
		email.send();
		
		logger.debug("Email sent");
		
	}catch (Exception e) {
		logger.error("Sending from"+from+" to "+to+"..."+e);
		throw new MailException(e.getMessage(), e);
	}
	
		
	}

	

		
}
