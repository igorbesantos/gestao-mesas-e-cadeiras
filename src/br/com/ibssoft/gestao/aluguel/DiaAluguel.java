package br.com.ibssoft.gestao.aluguel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import br.com.ibssoft.database.AlugueisDAO;
import br.com.ibssoft.database.ConnectionPool;
import br.com.ibssoft.gestao.estoque.EstoqueJogos;

public class DiaAluguel {
	
	private LocalDate data;
	private EstoqueJogos estoque; 
	
	private DiaAluguel(LocalDate data, EstoqueJogos estoque){
		this.data = data;
		this.estoque = estoque;
	}
	
	public static DiaAluguel of (Connection connection, LocalDate data){
			try {
				AlugueisDAO dao = new AlugueisDAO(connection);
				EstoqueJogos estoque;
				estoque = dao.getEstoqueOf(connection, data);
				return new DiaAluguel(data, estoque);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public LocalDate getData() {
		return this.data;
	}
	
	public EstoqueJogos getEstoqueJogos() {
		return this.estoque;
	}
	
	@Override
	public String toString() {
		return ("["+data+"]");
	}

	public void adicionaMesas(Integer qtd, Connection con) throws SQLException{
		new AlugueisDAO(con).atualizaXMesasAPartirDe(qtd, this);
		this.estoque = DiaAluguel.of(con, this.data).getEstoqueJogos();
	}

	public void removeMesas(Integer qtd, Connection con) throws SQLException{
		qtd = qtd*(-1);
		new AlugueisDAO(con).atualizaXMesasAPartirDe(qtd, this);
		this.estoque = DiaAluguel.of(con, this.data).getEstoqueJogos();
	}

	public void removeCadeiras(Integer qtd, Connection con) throws SQLException{
		qtd = qtd*(-1);
		new AlugueisDAO(con).atualizaXCadeirasAPartirDe(qtd, this);
		this.estoque = DiaAluguel.of(con, this.data).getEstoqueJogos();
	}

	public void adicionaCadeiras(Integer qtd, Connection con) throws SQLException{
		new AlugueisDAO(con).atualizaXCadeirasAPartirDe(qtd, this);
		this.estoque = DiaAluguel.of(con, this.data).getEstoqueJogos();
	}
}
