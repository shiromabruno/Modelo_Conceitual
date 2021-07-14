package com.shiromabruno.modelconceitual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.shiromabruno.modelconceitual.domain.Pedido;

//classe abstrata nao pode ter instancia. Eh uma classe incompleta e precisa ser extendida para um concrete
//Como eh abstract, nao eh obrigado a implementar todos os metodos da INTERFACE (sendEmail nao foi implementado)
public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSipleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	//protected - esse metodo pode ser acessado por subclasses e nao pode ser acessado pelos usuarios da classe (controladores e servicos)
	protected SimpleMailMessage prepareSipleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Codigo: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

}
