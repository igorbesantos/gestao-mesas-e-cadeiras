package br.com.ibssoft.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.ibssoft.gestao.aluguel.DiaAluguel;
import br.com.ibssoft.gestao.estoque.EstoqueCadeiras;
import br.com.ibssoft.gestao.estoque.EstoqueJogos;
import br.com.ibssoft.gestao.estoque.EstoqueMesas;

public class AlugueisDAO {
	
	private Connection con;
	
	public AlugueisDAO(Connection connection){
		this.con = connection;
	}
	
	public EstoqueJogos getEstoqueOf(Connection connection, LocalDate data) throws SQLException{
		if(!isDiaCadastrado(data)){
			cadastraDia(data);
		}
		int totMes, totCad, mesAlugadas, cadAlugadas;
		String sql = "SELECT TotMes AS 'mesas', TotCad AS 'cadeiras' FROM diasaluguel WHERE DatAl=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			totMes = resultSet.getInt("mesas");
			totCad = resultSet.getInt("cadeiras");
			
			sql="SELECT SUM(QtdMes) AS 'mesAlugadas', SUM(QtdCad) AS 'cadAlugadas' FROM alugueis WHERE DatAl = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, data.toString());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				mesAlugadas = resultSet.getInt("mesAlugadas");
				cadAlugadas = resultSet.getInt("cadAlugadas");
				
				EstoqueMesas mesas = new EstoqueMesas(totMes, mesAlugadas);
				EstoqueCadeiras cadeiras = new EstoqueCadeiras(totCad, cadAlugadas);
				EstoqueJogos estoque = new EstoqueJogos(mesas, cadeiras);
				return estoque;
			}
		}
		return null;
	}
	
	public boolean atualizaXMesasAPartirDe(int x, DiaAluguel dia){
		boolean isSuccessful = false;
		try {
			con.setAutoCommit(false);
			String sql = "UPDATE diasaluguel SET TotMes=TotMes+? WHERE DatAl = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, x);
			preparedStatement.setString(2, dia.getData().toString());
			preparedStatement.execute();
			if(preparedStatement.getUpdateCount()==1){
				sql = "SELECT TotMes FROM diasaluguel WHERE DatAl = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, dia.getData().toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					int totMes = resultSet.getInt("TotMes");
					sql = "UPDATE diasaluguel SET TotMes=? WHERE DatAl > ?";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, totMes);
					preparedStatement.setString(2, dia.getData().toString());
					preparedStatement.execute();
					con.commit();
					isSuccessful = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccessful;
	}
	
	public boolean atualizaXCadeirasAPartirDe(int x, DiaAluguel dia){
		boolean isSuccessful = false;
		try {
			con.setAutoCommit(false);
			String sql = "UPDATE diasaluguel SET TotCad=TotCad+? WHERE DatAl = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, x);
			preparedStatement.setString(2, dia.getData().toString());
			preparedStatement.execute();
			if(preparedStatement.getUpdateCount()==1){
				sql = "SELECT TotCad FROM diasaluguel WHERE DatAl = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, dia.getData().toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					int totCad = resultSet.getInt("TotCad");
					sql = "UPDATE diasaluguel SET TotCad=? WHERE DatAl > ?";
					preparedStatement = con.prepareStatement(sql);
					preparedStatement.setInt(1, totCad);
					preparedStatement.setString(2, dia.getData().toString());
					preparedStatement.execute();
					con.commit();
					isSuccessful = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccessful;
	}

	private void cadastraDia(LocalDate data) throws SQLException {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		String sql;
		String datAl="data";
		int totMes=0;
		int totCad=0;
		sql="SELECT MAX(DatAl) FROM diasaluguel WHERE DatAl < ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			datAl = resultSet.getString(1);
		} if(datAl==null){
			sql="SELECT MIN(DatAl) FROM diasaluguel WHERE DatAl > ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, data.toString());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				datAl = resultSet.getString(1);
			}
		}
		sql = "SELECT TotMes, TotCad FROM diasaluguel WHERE DatAl = ?";
		preparedStatement= con.prepareStatement(sql);
		preparedStatement.setString(1, datAl);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			totMes = resultSet.getInt("TotMes");
			totCad = resultSet.getInt("TotCad");
		}
		if(totMes!=0 || totCad!=0){
			sql = "INSERT INTO diasaluguel (DatAl, TotMes, TotCad) VALUES (?, ?, ?)";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, data.toString());
			preparedStatement.setInt(2, totMes);
			preparedStatement.setInt(3, totCad);
			preparedStatement.execute();
		}
	}

	private boolean isDiaCadastrado(LocalDate data) throws SQLException {
		String sql = "SELECT DatAl FROM diasaluguel WHERE DatAl = ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		preparedStatement.executeQuery();
		return preparedStatement.getResultSet().next();
	}
	
	private Integer qtdAlugueisAPartirDe (LocalDate data) throws SQLException {
		String sql = "SELECT COUNT(Id) AS Qtd FROM alugueis WHERE DatAl >= ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			int qtd = resultSet.getInt("Qtd");
			return qtd;
		}
		return null;
	}
	
	//Método para utilização bastante específica -REPENSAR IMPLEMENTAÇÂO
	public String[][] dadosAlugueisAPartirDe(LocalDate data) throws SQLException {
		String[][] dados = new String[qtdAlugueisAPartirDe(data)][5];
		String cliente, dataFormatada;
		LocalDate localDate;
		Integer mesas, cadeiras, id;
		ResultSet resultSet;
		
		String sql = "SELECT a.Id as Id, a.DatAl as Data, c.Nom as Cliente, a.QtdMes as Mesas, a.QtdCad as Cadeiras "+
				"FROM alugueis AS a JOIN clientes AS c ON (a.IdCli = c.IdCli) "+
				"WHERE a.DatAl >= ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		resultSet = preparedStatement.executeQuery();
		for(int i=0; resultSet.next(); i++){
			cliente = resultSet.getString("Cliente");			
			mesas = resultSet.getInt("Mesas");			
			cadeiras = resultSet.getInt("Cadeiras");
			id = resultSet.getInt("Id");
			localDate = LocalDate.parse(resultSet.getString("Data"));
			dataFormatada = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			dados[i][0]=id.toString();
			dados[i][1]=dataFormatada;
			dados[i][2]=cliente;
			dados[i][3]=mesas.toString();
			dados[i][4]=cadeiras.toString();
		}
		return dados;
	}

	public void removeAluguel(int id) throws SQLException {
		String sql = "DELETE FROM alugueis WHERE Id = ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
	}
}
