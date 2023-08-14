package com.livro.capitulo3.hibernate.crudxml;

import java.sql.Date;
import java.util.List;

import com.livro.capitulo3.hibernate.conexao.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContatoCrudXML {

	public void salvar(Contato contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.save(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.err.println("Não foi possível inserir o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e2) {
				System.err.println("Erro ao fechar a operação de inserção. Mensagem: " + e2.getMessage());
			}
		}
	}

	public void atualizar(Contato contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.update(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.err.println("Não foi possível alterar o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e2) {
				System.err.println("Não foi possível fechar a operação de atualização. Erro: " + e2.getMessage());
			}
		}
	}

	public void excluir(Contato contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.delete(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.err.println("Não foi possível excluir o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e2) {
				System.err.println("Não foi possível fechar a operação de exclusão. Erro: " + e2.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Contato> listar(){
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Contato> resultado = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato");
			resultado = consulta.list();
			transacao.commit();
			//return resultado;
		} catch (HibernateException e) {
			System.err.println("Não foi possível selecionar os contatos. Erro: " + e.getMessage());
			throw new HibernateException(e);
		} finally {
			try {
				sessao.close();
			} catch (Throwable e2) {
				System.err.println("Não foi possível fechar a operação de consulta. Erro: " + e2.getMessage());
			}
		}
		return resultado;
	}
	
	public Contato buscaContato(int valor) {
		Contato contato = null;
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato where codigo = :parametro");
			consulta.setInteger("parametro", valor);
			contato = (Contato) consulta.uniqueResult();
			transacao.commit();
		}catch (HibernateException e) {
			System.err.println("Não foi possível buscar o contato. Erro: " + e.getMessage());
		} finally {
			try {
				sessao.close();
			} catch (Throwable e2) {
				System.err.println("Não foi possível fechar a operação de consulta. Erro: " + e2.getMessage());
			}
		}
		return contato;
	}

	public static void main(String[] args) {
		ContatoCrudXML contatoCrudXML = new ContatoCrudXML();
		String[] nomes = {"Fulano Hibernate", "Beltrano Hibernate", "Ciclano Hibernate"};
		String[] fones = {"(47) 1122-3344", "(48) 3322-1144", "(49) 4411-3322"};
		String[] emails = {"fulano@teste.com", "beltrano@teste.com", "ciclano@teste.com"};
		String[] observacoes = {"Novo cliente", "Cliente em dia", "Visitar na sexta-feira"};
		Contato contato = null;
		for(int i = 0; i < nomes.length; i++) {
			contato = new Contato();
			contato.setNome(nomes[i]);
			contato.setTelefone(fones[i]);
			contato.setEmail(emails[i]);
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setObservacao(observacoes[i]);
			contatoCrudXML.salvar(contato);
		}
		System.out.println("Total de registros cadastrados: "+ contatoCrudXML.listar().size());
	}
}
