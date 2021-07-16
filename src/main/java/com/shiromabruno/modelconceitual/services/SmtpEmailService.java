package com.shiromabruno.modelconceitual.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

//Erro PKIX LOCAL = desativar ANTIVIRUS
//Classe simulando PROD de envio email
//Deu erro falando q precisa implementar sendEmail (Interface tem, mas nao foi implementado no Abstract. Abstract so implementou sendOrderConfirmationEmail)
//Nessa classe so tem o metodo especifico para o ENVIO DE EMAIL USANDO O MAILSENDER 
public class SmtpEmailService extends AbstractEmailService {

	//MailSender ira enviar email conforme a config no applicationProperties DEV e PROD
	//O framework vai instanciar com todos os dados do Application Properties
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		//LOG.info(msg.toString()); ---> simulando em TEST o envio. Na verdade era PRINT
		mailSender.send(msg); 
		LOG.info("Email enviado...");	
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email HTML...");
		//LOG.info(msg.toString()); ---> simulando em TEST o envio. Na verdade era PRINT
		javaMailSender.send(msg); 
		LOG.info("Email HTML enviado...");
		
	}

}
