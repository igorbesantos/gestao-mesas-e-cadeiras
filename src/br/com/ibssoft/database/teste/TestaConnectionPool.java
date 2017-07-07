package br.com.ibssoft.database.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.ibssoft.database.ClientesDAO;
import br.com.ibssoft.database.ConnectionPool;
import br.com.ibssoft.gestao.cliente.Cliente;

public class TestaConnectionPool {

	public static void main(String[] args) {
		try(Connection con = new ConnectionPool().getConnection()){
			ClientesDAO dao = new ClientesDAO(con);
			List<Cliente> clientes = dao.getListaClientes();
			for(Cliente cliente: clientes){
				System.out.println(cliente.toString());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
