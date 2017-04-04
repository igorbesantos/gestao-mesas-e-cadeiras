package br.com.ibssoft.gestao.cliente;

import java.io.Serializable;

public class Cliente implements Serializable, Comparable<Cliente> {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String end; //endereço
	private String tel; //telefone

	public Cliente(String nome, String end, String tel) {
		this.nome = nome;
		this.end = end;
		this.tel = tel;
	}
	
	public String getNome() {
		return nome;
	}

	public String getEnd() {
		return end;
	}

	public String getTel() {
		return tel;
	}

	@Override
	public int compareTo(Cliente outroCliente) {
		return this.nome.compareTo(outroCliente.nome);
	}

}
