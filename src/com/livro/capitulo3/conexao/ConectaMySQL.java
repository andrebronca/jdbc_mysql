package com.livro.capitulo3.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaMySQL {
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "pass123";
			
			conn = DriverManager.getConnection(url, usuario, senha);
			System.out.println("conectado");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro de SQL. Erro: "+ e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e2) {
				System.out.println("Erro ao fechar a conexão. Erro: "+ e2.getMessage());
			}
		}
	}
}
