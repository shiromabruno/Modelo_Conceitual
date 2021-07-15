package com.shiromabruno.modelconceitual.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

// Classe para simular o email e aparecer no LOG
// Classe nao implementou sendOrderConfirmationEmail ou prepareSipleMailMessageFromPedido do AbstractEmailService, mas implementou o sendEmail da INTERFACE
public class MockEmailService extends AbstractEmailService{
	
	//Tipo static pra nao ter que criar para todo email. So um ja eh o suficiente
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString()); // --> simulando em TEST o envio. Na verdade era PRINT
		LOG.info("Email enviado...");
	}

}
