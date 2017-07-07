package br.com.ibssoft.gestao.cliente;

import java.io.Serializable;

public class Cliente implements Serializable, Comparable<Cliente> {

	private static final long serialVersionUID = 1L;	
	
	private Integer id;
	private String nome;
	private String end; //endereço
	private String tel; //telefone

	public Cliente(String nome, String end, String tel) {
		this.nome = nome;
		this.end = end;
		this.tel = tel;
	}
	
	public void setId (int id){
		this.id = id;
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
	
	public Integer getId() {
		return id;
	}

	@Override
	public int compareTo(Cliente outroCliente) {
		return this.nome.compareTo(outroCliente.nome);
	}
	
	@Override
	public String toString() {
		return ("["+this.id+" | "+this.nome+" | "+this.end+" | "+this.tel+"]");
	}

}
