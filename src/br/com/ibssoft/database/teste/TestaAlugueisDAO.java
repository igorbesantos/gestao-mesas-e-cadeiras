package br.com.ibssoft.database.teste;

import java.time.LocalDate;

import br.com.ibssoft.gestao.aluguel.DiaAluguel;

public class TestaAlugueisDAO {

	public static void main(String[] args) {
		LocalDate data = LocalDate.of(2017, 7, 9);
		DiaAluguel dia = DiaAluguel.of(data);
		System.out.println(dia);
	}

}
