package br.com.ibssoft.gestao;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.com.ibssoft.gestao.estoque.*;

public class Gestao implements Serializable{
	
	private static final long seriaVersionUID = 1L;
	
	private EstoqueMesas estMes;
	private EstoqueCadeiras estCad;
	private EstoqueJogos estJog;
	private File file = new File("status.ser");
	
	
	public boolean startOp(){ //start operation
		if(this.restoreStatus() == false){
			estMes = new EstoqueMesas(0,0);
			estCad = new EstoqueCadeiras(0,0);
			estJog = new EstoqueJogos(estMes, estCad);
			return false;
		}else {
			return true;
		}
	}

	public boolean stopOp(){ //stop operation
		return this.storeStatus();
	}

	private boolean restoreStatus(){
		try{
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
				estJog = (EstoqueJogos) inputStream.readObject();
				inputStream.close();
				estMes = estJog.getEstoqueMesas();
				estCad = estJog.getEstoqueCadeiras();
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean storeStatus(){
		try{
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
				outputStream.writeObject(estJog);
			outputStream.close();
		}catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public EstoqueCadeiras getEstCad() {
		return estCad;
	}

	public EstoqueJogos getEstJog() {
		return estJog;
	}
	public EstoqueMesas getEstMes() {
		return estMes;
	}

}
