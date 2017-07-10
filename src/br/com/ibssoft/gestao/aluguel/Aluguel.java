package br.com.ibssoft.gestao.aluguel;

import java.time.LocalDate;

import br.com.ibssoft.gestao.cliente.Cliente;

public class Aluguel {
	
	private Cliente cliente;
	private DiaAluguel dia;
	private Integer qtdMesas;
	private Integer qtdCadeiras;
	
	public Aluguel(Cliente cliente, DiaAluguel dia, Integer qtdMesas, Integer qtdCadeiras) {
		this.cliente = cliente;
		this.dia = dia;
		this.qtdMesas = qtdMesas;
		this.qtdCadeiras = qtdCadeiras;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Integer getQtdMesas() {
		return qtdMesas;
	}

	public Integer getQtdCadeiras() {
		return qtdCadeiras;
	}
	public LocalDate getData() {
		return dia.getData();
	}
	
}
