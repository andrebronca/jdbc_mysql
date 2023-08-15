package com.livro.capitulo3.hibernate.conexao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	//private static final SessionFactory sessionFactory = buildSessionFactory();
	private static final SessionFactory sessionFactoryAnnotation = buildSessionFactoryAnnotation();
	
	//os dois m�todos abaixo fazem uso do mapeamento via hbm.xml
	/*
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Cria��o inicial do objeto SessionFactory falhou. Erro: "+ e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	*/
	
	//refiz esses m�todos para n�o desativar o m�todos acima
	//aqui faz uso de annotations
	private static SessionFactory buildSessionFactoryAnnotation() {
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("hibernate.cfg.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Cria��o inicial do objeto SessionFactory falhou. Erro: "+ e.getMessage());
			throw new ExceptionInInitializerError();
		}
	}
	
	public static SessionFactory getSessionFactoryAnnotation() {
		return sessionFactoryAnnotation;
	}
}
