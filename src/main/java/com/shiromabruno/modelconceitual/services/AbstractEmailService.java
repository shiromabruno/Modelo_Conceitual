package com.shiromabruno.modelconceitual.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.shiromabruno.modelconceitual.domain.Pedido;

//Erro PKIX LOCAL = desativar ANTIVIRUS
//classe abstrata nao pode ter instancia. Eh uma classe incompleta e precisa ser extendida para um concrete
//Como eh abstract, nao eh obrigado a implementar todos os metodos da INTERFACE (sendEmail sendHtmlEmail nao foi implementado [fica para as classes especificas Mock e SMTP])
//Nessa classe tem os metodos IGUAIS para o MockEmailService e SMTPEmailService
public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSipleMailMessageFromPedido(obj);
		sendEmail(sm); // esse SEND depende do tipo do objeto. Se for MockEmailService roda o sendEmail de la, se for SmtpEmailService roda sendEmail de la
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
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj); // esse "pedido" a esquerda corresponde com apelido la no PEDIDO.id, PEDIDO.cliente.nome, PEDIDO.pagamento.estado...
	    return templateEngine.process("email/confirmacaoPedido", context); // "email/confirmacaoPedido" --> associa com confirmacaoPedido.html que esta no RESOURCE/Templates. Nao precisa colocar o .html e nem falar q esta dentro do src/main/resource/templates --> Ja pega de la por padrao. Retorna um STRING
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		}
		catch(MessagingException e) { //caso de excecao no MimeMessage, manda pelo Email normal String
			sendOrderConfirmationEmail(obj);
		}	
	}

	//protected para ser implementado em uma possivel subclasse
	//metodo pooooode gerar MessagingException
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException  {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject(sender);
		mmh.setSubject("Pedido confirmado! Codigo: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true); // TRUE indica que o conteudo eh HTML
		
		return mimeMessage;
	}

}
