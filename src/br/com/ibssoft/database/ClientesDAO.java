package br.com.ibssoft.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.ibssoft.gestao.cliente.Cliente;

public class ClientesDAO {
	
	Connection con;
	
	public ClientesDAO(Connection connection){
		this.con = connection;
	}
	
	public List<Cliente> getListaClientes () throws SQLException {
		List<Cliente> lista = new ArrayList<>(); 
		String sql = "SELECT * FROM CLIENTES";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();
		while(resultSet.next()){
			String nome = resultSet.getString("Nom");
			String end = resultSet.getString("End");
			String tel = resultSet.getString("Tel");
			Cliente cliente = new Cliente(nome, end, tel);
			int id = resultSet.getInt("IdCli");
			cliente.setId(id);
			lista.add(cliente);
		}
		return lista;
	}
	
	public Integer getQtdClientes () throws SQLException{
		Integer qtdClientes=null;
		String sql = "SELECT COUNT(IdCli) as qtdClientes FROM CLIENTES";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			qtdClientes = resultSet.getInt("qtdClientes");
		}
		return qtdClientes;
	}
	
	public boolean adicionaCliente(Cliente cliente) throws SQLException{
		String nome = cliente.getNome();
		String end = cliente.getEnd();
		String tel = cliente.getTel();
		String sql = "INSERT INTO CLIENTES (NOM, END, TEL) VALUES (?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, nome);
		preparedStatement.setString(2, end);
		preparedStatement.setString(3, tel);
		preparedStatement.execute();
		ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		if(generatedKeys.next()){
			System.out.println("Cliente "+generatedKeys.getInt(1)+" gerado com sucesso!");
			return true;
		}
		return false;
	}
	
	public boolean removeCliente(int id) throws SQLException{
		String sql = "DELETE FROM CLIENTES WHERE IdCli=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		preparedStatement.execute();
		
		if(preparedStatement.getUpdateCount()==1){
			System.out.println("Cliente "+id+" removido com sucesso!");
			return true;
		}
		return false;
	}
}
