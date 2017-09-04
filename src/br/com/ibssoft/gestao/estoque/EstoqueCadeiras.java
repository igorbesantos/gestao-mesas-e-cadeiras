package br.com.ibssoft.gestao.estoque;

import java.io.Serializable;

public class EstoqueCadeiras implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer totalCadeiras;
	private Integer cadeirasAlugadas;
	
	public EstoqueCadeiras (int total, int alugadas) throws IllegalArgumentException {
		if ((total>=0)&&(alugadas>=0)){
			this.totalCadeiras = total;
			this.cadeirasAlugadas = alugadas;
		} else{
			throw new IllegalArgumentException();
		}
	}
	
	public EstoqueCadeiras (int total) throws IllegalArgumentException {
		if ((total>=0)){
			this.totalCadeiras = total;
			this.cadeirasAlugadas = 0;
		} else{
			throw new IllegalArgumentException();
		}
	}
	
	public Integer getTotalCadeiras() {
		return totalCadeiras;
	}
	public Integer getCadeirasAlugadas() {
		return cadeirasAlugadas;
	}
	
	
	public Integer getCadeirasDisponiveis() {
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
		if ((cadeirasRemovidas>0)&&(cadeirasRemovidas<=totalCadeiras)){
			totalCadeiras -= cadeirasRemovidas;
		}else{
			throw new IllegalArgumentException("Foi passado um valor menor ou igual a zero");
		}
	}
}
