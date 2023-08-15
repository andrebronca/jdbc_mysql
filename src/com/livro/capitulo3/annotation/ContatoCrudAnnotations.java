package com.livro.capitulo3.annotation;

import java.sql.Date;
import java.util.List;

import com.livro.capitulo3.hibernate.conexao.HibernateUtil;
import com.livro.capitulo3.hibernate.crudxml.Contato;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContatoCrudAnnotations {
	public void salvar(ContatoAnnotations contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactoryAnnotation().openSession();
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
	
	public void atualizar(ContatoAnnotations contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactoryAnnotation().openSession();
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
	
	public void excluir(ContatoAnnotations contato) {
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactoryAnnotation().openSession();
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
	public List<ContatoAnnotations> listar(){
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<ContatoAnnotations> resultado = null;
		try {
			sessao = HibernateUtil.getSessionFactoryAnnotation().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato");
			resultado = (List<ContatoAnnotations>) consulta.list();
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
	
	public ContatoAnnotations buscaContato(int id) {
		ContatoAnnotations contato = null;
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		try {
			sessao = HibernateUtil.getSessionFactoryAnnotation().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato where codigo = :parametro");
			consulta.setInteger("parametro", id);
			contato = (ContatoAnnotations) consulta.uniqueResult();
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
		ContatoCrudAnnotations cca = new ContatoCrudAnnotations();
		ContatoAnnotations ca = null;
		String[] nomes = {"Solano annotations", "Lunare annotations", "Venusiana annotations"};
		String[] fones = {"(12) 3344-5566", "(13) 4455-6677", "(14) 5566-7788"};
		String[] emails = {"solano@teste.com", "lunare@teste.com", "venusiana@teste.com"};
		String[] observacoes = {"novo cliente", "cliente em dia", "ligar na terça-feira"};
		
		for(int i = 0; i < nomes.length; i++) {
			ca = new ContatoAnnotations();
			ca.setNome(nomes[i]);
			ca.setTelefone(fones[i]);
			ca.setEmail(emails[i]);
			ca.setObservacao(observacoes[i]);
			ca.setDataCadastro(new Date(System.currentTimeMillis()));
			cca.salvar(ca);
		}
		
		System.out.println("Total de registros cadastrados: "+ cca.listar().size());
	}
}
