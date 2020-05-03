package com.shiromabruno.modelconceitual.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.shiromabruno.modelconceitual.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	// Atraves da instanteDoPedido soma + 7 dias e grava no PagamentoComBoleto pagto
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
