package br.com.ibssoft.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class ConnectionPool {
	
	private DataSource dataSource;
	
	public ConnectionPool () {
		MysqlConnectionPoolDataSource pool = new MysqlConnectionPoolDataSource();
		pool.setUrl("jdbc:mysql://localhost/gestao_alugueis");
		pool.setUser("root");
		pool.setPassword("njb8yp8f");
		dataSource = pool;
		System.out.println("Conexão aberta!");
	}
	
	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}
