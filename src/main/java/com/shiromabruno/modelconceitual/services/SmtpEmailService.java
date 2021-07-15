package com.shiromabruno.modelconceitual.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

//Classe simulando PROD de envio email
//Deu erro falando q precisa implementar sendEmail (Interface tem, mas nao foi implementado no Abstract. Abstract so implementou sendOrderConfirmationEmail)
public class SmtpEmailService extends AbstractEmailService {

	//MailSender ira enviar email conforme a config no applicationProperties DEV e PROD
	//O framework vai instanciar com todos os dados do Application Properties
	@Autowired
	private MailSender mailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		//LOG.info(msg.toString()); ---> simulando em TEST o envio. Na verdade era PRINT
		mailSender.send(msg); 
		LOG.info("Email enviado...");	
	}

}
