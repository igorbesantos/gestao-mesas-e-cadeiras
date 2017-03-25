package br.com.ibssoft.teste;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import br.com.ibssoft.gestao.estoque.*;

public class TestaEstoque {

	public static void main(String[] args) {
		new TestaEstoque().go();

	}
	
	public void go(){
		EstoqueMesas mesas = new EstoqueMesas(22,5); 
		EstoqueCadeiras cadeiras = new EstoqueCadeiras(80,20); 
		EstoqueJogos jogos = new EstoqueJogos(mesas,cadeiras);
		
		try{
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("status.ser"));
			stream.writeObject(jogos);
			stream.close();
		}catch(IOException ex){ex.printStackTrace();}
	}

}
