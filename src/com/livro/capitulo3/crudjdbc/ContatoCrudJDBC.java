package com.livro.capitulo3.crudjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContatoCrudJDBC {
	
	public void salvar(Contato contato) {
		Connection conn = this.geraConexao();
		PreparedStatement insereSt = null;
		String sql = "insert into contato(nome, telefone, email, dt_cad, obs) values (?, ?, ?, ?, ?)";

		try {
			insereSt = conn.prepareStatement(sql);
			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			insereSt.executeUpdate(); // apropriada para alterações no BD
		} catch (SQLException e) {
			System.err.println("Erro ao incluir contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				insereSt.close();
				conn.close();
			} catch (Throwable e2) {
				System.err.println("Erro ao fechar operação de insert. Mensagem: " + e2.getMessage());
			}
		}
	}

	public void atualizar(Contato c) {
		Connection conn = this.geraConexao();
		PreparedStatement ps = null;
		String sql = "update contato set nome = ?, telefone = ?, email = ?, obs = ? where codigo = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, c.getNome());
			ps.setString(2, c.getTelefone());
			ps.setString(3, c.getEmail());
			ps.setString(4, c.getObservacao());
			ps.setInt(5, c.getCodigo());
			ps.executeUpdate(); // apropriada para alterações no BD
		} catch (SQLException e) {
			System.err.println("Erro ao atualizar o contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Throwable e2) {
				System.err.println("Erro ao fechar as operações de atualização. Mensagem: " + e2.getMessage());
			}
		}
	}

	public void excluir(Contato c) {
		Connection conn = this.geraConexao();
		PreparedStatement ps = null;
		String sql = "delete from contato where codigo = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getCodigo());
			ps.executeUpdate(); // apropriada para alterações no BD
		} catch (SQLException e) {
			System.err.println("Erro ao tentar excluir contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (Throwable e2) {
				System.err.println("Erro ao fechar as operações de exclusão. Mensagem: " + e2.getMessage());
			}
		}
	}

	public List<Contato> listar() {
		Connection conn = this.geraConexao();
		List<Contato> contatos = new ArrayList<>();
		Statement consulta = null;
		ResultSet resultado = null;
		Contato contato = null;
		String sql = "select * from contato";
		try {
			consulta = conn.createStatement();
			resultado = consulta.executeQuery(sql);
			while (resultado.next()) {
				contato = new Contato();
				contato.setCodigo(new Integer(resultado.getInt("codigo")));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
				contatos.add(contato);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar código do contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				consulta.close();
				resultado.close();
				conn.close();
			} catch (Throwable e2) {
				System.err.println("Erro ao fechar operações de consulta. Mensagem: " + e2.getMessage());
			}
		}
		return contatos;
	}

	public Contato buscaContato(int codigo) {
		Contato contato = null;
		Connection conn = this.geraConexao();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from contato where codigo = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, codigo);
			ps.executeQuery(); // apropriada para select
			rs = ps.getResultSet();

			while (rs.next()) {
				contato = new Contato();
				contato.setCodigo(new Integer(rs.getInt("codigo")));
				contato.setNome(rs.getString("nome"));
				contato.setTelefone(rs.getString("telefone"));
				contato.setEmail(rs.getString("email"));
				contato.setDataCadastro(rs.getDate("dt_cad"));
				contato.setObservacao(rs.getString("obs"));
			}
		} catch (SQLException e) {
			System.err.println("Erro ao consultar o contato pelo código. Mensagem: " + e.getMessage());
		} finally {
			try {
				ps.close();
				rs.close();
				conn.close();
			} catch (Throwable e2) {
				System.err.println("Erro ao fechar as operações de busca de contato. Mensagem: " + e2.getMessage());
			}
		}

		return contato;
	}

	public Connection geraConexao() {
		Connection conn = null;
		try {
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "pass123";
			conn = DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException e) {
			System.err.println("Ocorreu um erro de SQL. Erro: " + e.getMessage());
		}
		return conn;
	}

	public static void main(String[] args) {
		ContatoCrudJDBC crud = new ContatoCrudJDBC();

		Contato beltrano = new Contato();
		beltrano.setNome("Beltrano Solar");
		beltrano.setTelefone("(47) 5555-3333");
		beltrano.setEmail("beltrano@teste.com.br");
		beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
		beltrano.setObservacao("Novo cliente");
//		crud.salvar(beltrano);

		Contato fulano = new Contato();
		fulano.setNome("Fulano Lunar");
		fulano.setTelefone("(47) 7777-2222");
		fulano.setEmail("fulano@teste.com.br");
		fulano.setDataCadastro(new Date(System.currentTimeMillis()));
		fulano.setObservacao("Novo contato - possível cliente");
	//	crud.salvar(fulano);
		System.out.println("Contatos cadastrados: " + crud.listar().size());
		
		//testar os outros métodos
		System.out.println(crud.buscaContato(2));
		Contato c4 = crud.buscaContato(4);
		//crud.excluir(c4);
		System.out.println("Contatos cadastrados: " + crud.listar().size());
		
		Contato c3 = crud.buscaContato(3);
		c3.setNome("Ciclano Júpiter");
		c3.setTelefone("(45) 9999-1100");
		c3.setEmail("ciclano@teste.com.br");
		c3.setObservacao("Requer visita");
		//crud.atualizar(c3);
		System.out.println("Contatos cadastrados: " + crud.listar().size());
		//todos os métodos estão funcionando. No livro apenas 2 métodos foram demonstrados. salvar e listar
	}
}
