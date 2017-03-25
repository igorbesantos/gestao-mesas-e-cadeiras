package br.com.ibssoft.gestao.estoque;

import java.io.Serializable;

public class EstoqueJogos implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private EstoqueMesas mesas;
	private EstoqueCadeiras cadeiras;
	
	public EstoqueJogos(EstoqueMesas mesas, EstoqueCadeiras cadeiras) {
		this.mesas = mesas;
		this.cadeiras = cadeiras;
	}
	
	public EstoqueMesas getEstoqueMesas() {
		return mesas;
	}
	public EstoqueCadeiras getEstoqueCadeiras() {
		return cadeiras;
	}

	public int getTotalJogos(){
		int qtdMesas = mesas.getTotalMesas();
		int qtdCadeiras = cadeiras.getTotalCadeiras();
		
		if(4*qtdMesas <= qtdCadeiras){
			return qtdMesas;
		}else{
			return qtdCadeiras/4;	
		}
	}
	
	public int getJogosDisponiveis(){
		int qtdMesas = mesas.getMesasDisponiveis();
		int qtdCadeiras = cadeiras.getCadeirasDisponiveis();
		
		if(4*qtdMesas <= qtdCadeiras){
			return qtdMesas;
		}else{
			return qtdCadeiras/4;	
		}
	}
}
