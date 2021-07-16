package com.shiromabruno.modelconceitual.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.shiromabruno.modelconceitual.domain.Pedido;

// Erro PKIX LOCAL = desativar ANTIVIRUS
// contrato do email para instanciar o MockEmail e o SMTPEmail
public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);

}
