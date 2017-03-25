package br.com.ibssoft.gestao.estoque;

import java.io.Serializable;

public class EstoqueCadeiras implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int totalCadeiras;
	private int cadeirasAlugadas;
	
	public EstoqueCadeiras (int total, int alugadas) throws IllegalArgumentException {
		if ((total>=0)&&(alugadas>=0)){
			this.totalCadeiras = total;
			this.cadeirasAlugadas = alugadas;
		} else{
			throw new IllegalArgumentException();
		}
	}
	
	public int getTotalCadeiras() {
		return totalCadeiras;
	}
	public int getCadeirasAlugadas() {
		return cadeirasAlugadas;
	}
	
	
	public int getCadeirasDisponiveis() {
		return this.totalCadeiras-this.cadeirasAlugadas;
	}
	
	public void adicionaCadeiras(int cadeirasNovas) throws IllegalArgumentException {
		if (cadeirasNovas>0){
			totalCadeiras += cadeirasNovas;
		}else{
			throw new IllegalArgumentException("Foi passado um valor menor ou igual a zero");
		}
	}
	
	public void removeCadeiras(int cadeirasRemovidas) throws IllegalArgumentException {
		if (cadeirasRemovidas>0){
			totalCadeiras -= cadeirasRemovidas;
		}else{
			throw new IllegalArgumentException("Foi passado um valor menor ou igual a zero");
		}
	}
}
