package br.com.ibssoft.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import br.com.ibssoft.gestao.aluguel.DiaAluguel;
import br.com.ibssoft.gestao.estoque.EstoqueCadeiras;
import br.com.ibssoft.gestao.estoque.EstoqueJogos;
import br.com.ibssoft.gestao.estoque.EstoqueMesas;

public class AlugueisDAO {
	
	private Connection con;
	
	public AlugueisDAO(Connection connection){
		this.con = connection;
	}
	
	public EstoqueJogos getEstoqueOf(LocalDate data) throws SQLException{
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
	
	public boolean atualizaXMesasAPartirDe(int x, DiaAluguel dia) throws SQLException{
		String sql = "UPDATE diasaluguel SET TotMes=TotMes+? WHERE DatAl >= ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, x);
		preparedStatement.setString(2, dia.getData().toString());
		preparedStatement.execute();
		return (preparedStatement.getUpdateCount() > 0);
	}
	
	public boolean atualizaXCadeirasAPartirDe(int x, DiaAluguel dia) throws SQLException{
		String sql = "UPDATE diasaluguel SET TotCad=TotCad+? WHERE DatAl >= ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setInt(1, x);
		preparedStatement.setString(2, dia.getData().toString());
		preparedStatement.execute();
		return (preparedStatement.getUpdateCount() > 0);
	}

	private void cadastraDia(LocalDate data) throws SQLException {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		String sql;
		int totMes=0;
		int totCad=0;
		
		sql="SELECT MAX(DatAl), QtdMes AS totMes, QtdCad AS totCad FROM alugueis WHERE DatAl < ?";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
			totMes=resultSet.getInt("totMes");
			totCad=resultSet.getInt("totCad");
		}
		
		sql = "INSERT INTO diasaluguel (DatAl, TotMes, TotCad) VALUES (?, ?, ?)";
		preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		preparedStatement.setInt(2, totMes);
		preparedStatement.setInt(3, totCad);
		preparedStatement.execute();
	}

	private boolean isDiaCadastrado(LocalDate data) throws SQLException {
		String sql = "SELECT DatAl FROM diasaluguel WHERE DatAl = ?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, data.toString());
		preparedStatement.executeQuery();
		return preparedStatement.getResultSet().next();
	}
}