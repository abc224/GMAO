package com.gmao.dao;


import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.entities.Utilisateur;


public class LoginDao {
	private static SessionFactory factory;
	
	//methode pour connecter un utilisateur
	
	public Utilisateur connexion(String email, String pwd) {
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction();
			List<Utilisateur> utilisateur = session.createQuery("from Utilisateur where email='"+email+"' and password='"+pwd+"'").list();
			//transaction.commit(); 
			if(utilisateur.size()!=0) {
				return utilisateur.get(0);
				}

			} catch (Exception e) { 
				if (transaction != null) { 
					transaction.rollback();
				} 
				System.out.println("ERROR: " + e.getMessage());
				}
		finally { 
			session.close();
		}
        return null;
	}
}

