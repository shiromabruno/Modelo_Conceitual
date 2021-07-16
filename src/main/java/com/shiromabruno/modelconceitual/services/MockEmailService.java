package com.shiromabruno.modelconceitual.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

//Erro PKIX LOCAL = desativar ANTIVIRUS
// Classe para simular o email e aparecer no LOG
// Classe nao implementou sendOrderConfirmationEmail ou prepareSipleMailMessageFromPedido do AbstractEmailService, mas implementou o sendEmail da INTERFACE
//Nessa classe so tem o metodo especifico para o MOCK (que apenas LOGA)
public class MockEmailService extends AbstractEmailService{
	
	//Tipo static pra nao ter que criar para todo email. So um ja eh o suficiente
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString()); // --> simulando em TEST o envio. Na verdade era PRINT
		LOG.info("Email enviado...");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email HTML...");
		LOG.info(msg.toString()); // --> simulando em TEST o envio. Na verdade era PRINT
		LOG.info("Email HTML enviado...");
	}

}
