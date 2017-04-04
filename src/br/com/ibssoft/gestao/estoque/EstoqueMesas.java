package br.com.ibssoft.gestao.estoque;

import java.io.Serializable;

public class EstoqueMesas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int totalMesas;
	private int mesasAlugadas;
	
	public EstoqueMesas (int total, int alugadas) throws IllegalArgumentException {
		if ((total>=0)&&(alugadas>=0)){
			this.totalMesas = total;
			this.mesasAlugadas = alugadas;
		} else{
			throw new IllegalArgumentException();
		}
	}
	
	
	public int getTotalMesas() {
		return totalMesas;
	}
	public int getMesasAlugadas() {
		return mesasAlugadas;
	}
	
	
	public int getMesasDisponiveis() {
		return this.totalMesas-this.mesasAlugadas;
	}
	
	public void adicionaMesas(int mesasNovas) throws IllegalArgumentException {
		if (mesasNovas>0){
			totalMesas += mesasNovas;
		}else{
			throw new IllegalArgumentException("Foi passado um valor menor ou igual a zero");
		}
	}
	
	public void removeMesas(int mesasRemovidas) throws IllegalArgumentException {
		if ((mesasRemovidas>0)&&(mesasRemovidas<=totalMesas)){
			totalMesas -= mesasRemovidas;
		}else{
			throw new IllegalArgumentException("Foi passado um valor menor ou igual a zero");
		}
	}
}
