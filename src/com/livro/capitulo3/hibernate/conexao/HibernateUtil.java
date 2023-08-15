package com.livro.capitulo3.hibernate.conexao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	//private static final SessionFactory sessionFactory = buildSessionFactory();
	private static final SessionFactory sessionFactoryAnnotation = buildSessionFactoryAnnotation();
	
	//os dois métodos abaixo fazem uso do mapeamento via hbm.xml
	/*
	private static SessionFactory buildSessionFactory() {
		try {
			Configuration cfg = new Configuration();
			cfg.configure("hibernate.cfg.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Criação inicial do objeto SessionFactory falhou. Erro: "+ e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	*/
	
	//refiz esses métodos para não desativar o métodos acima
	//aqui faz uso de annotations
	private static SessionFactory buildSessionFactoryAnnotation() {
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("hibernate.cfg.xml");
			return cfg.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Criação inicial do objeto SessionFactory falhou. Erro: "+ e.getMessage());
			throw new ExceptionInInitializerError();
		}
	}
	
	public static SessionFactory getSessionFactoryAnnotation() {
		return sessionFactoryAnnotation;
	}
}
