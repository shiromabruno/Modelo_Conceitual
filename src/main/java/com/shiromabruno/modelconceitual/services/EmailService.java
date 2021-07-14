package com.shiromabruno.modelconceitual.services;

import org.springframework.mail.SimpleMailMessage;

import com.shiromabruno.modelconceitual.domain.Pedido;

// contrato do email para instanciar o MockEmail e o SMTPEmail
public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
