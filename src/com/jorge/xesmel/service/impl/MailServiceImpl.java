package com.jorge.xesmel.service.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jorge.xesmel.configuration.ConfigurationManager;
import com.jorge.xesmel.dao.util.ConfigUtilsNames;
import com.jorge.xesmel.exception.MailException;
import com.jorge.xesmel.service.MailService;

public class MailServiceImpl implements MailService{

	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	private static final String CFGM_PFX = "service.mail.";
	private static final String SERVER = CFGM_PFX + "host.name";
	private static final String PORT = CFGM_PFX + "smtp.port";
	private static final String ACCOUNT = CFGM_PFX + "user.name";
	private static final String PASSWORD = CFGM_PFX + "user.password";

	public MailServiceImpl() {

	}

	@Override
	public void sendEmail(String from, String subject, String text, String... to) throws MailException {
		
		logger.traceEntry();
	
		ConfigurationManager cfg = ConfigurationManager.getInstance();
		
		Email email = new SimpleEmail();
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Sending from "+from+" to "+to+"...");
			}
			
			email.setHostName(cfg.getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, SERVER));			
			email.setSmtpPort(Integer.valueOf(cfg.getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, PORT)));			
			email.setAuthenticator(
					new DefaultAuthenticator(cfg.getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, ACCOUNT),
							cfg.getParameter(ConfigUtilsNames.WEB_XESMEL_PROPERTIES, PASSWORD)));			
			email.setSSLOnConnect(true);			
			email.setFrom(from);			
			email.setSubject(subject);			
			email.setMsg(text);			
			email.addTo(to);
			email.send();

			logger.traceExit();

		}catch (EmailException e) {
			logger.error("Sending from "+from+" to "+to+"..."+e, e);
			throw new MailException("Trying to send mail "+from+to, e);
		}		
	}		
}
