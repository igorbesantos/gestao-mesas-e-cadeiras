package br.com.ibssoft.gestao.aluguel;

import java.time.LocalDate;

import br.com.ibssoft.gestao.cliente.Cliente;
import sun.util.calendar.LocalGregorianCalendar.Date;

public class Aluguel {
	
	private Cliente cliente;
	private LocalDate data;
	
	public static void main(String[] args){
		LocalDate data = LocalDate.now();
		System.out.println(data);
	}
}
