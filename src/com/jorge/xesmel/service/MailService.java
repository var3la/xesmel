package com.jorge.xesmel.service;

import com.jorge.xesmel.exception.MailException;

public interface MailService {

	public void sendEmail(String from, String subject, String text, String...to)
			throws MailException;

	
}
