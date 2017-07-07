package br.com.ibssoft.database.teste;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.ibssoft.database.ClientesDAO;
import br.com.ibssoft.database.ConnectionPool;
import br.com.ibssoft.gestao.cliente.Cliente;

public class TestaInsercao {

	public static void main(String[] args) {
		try(Connection con = new ConnectionPool().getConnection()){
			ClientesDAO clientesDAO = new ClientesDAO(con);
			String nome = "João da silva";
			String end = "Rua dos bobos";
			String tel = "3391-0000";
			Cliente cliente = new Cliente(nome, end, tel);
			clientesDAO.adicionaCliente(cliente);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
