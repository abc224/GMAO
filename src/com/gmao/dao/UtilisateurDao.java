package com.gmao.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.entities.Poste;
import com.gmao.entities.Utilisateur;

public class UtilisateurDao{

	private static SessionFactory factory;
	
	// methode pour ajouter un personnel
	public void ajout(Utilisateur u, int idPoste) {
		// TODO Auto-generated method stub
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Poste p = session.get(Poste.class, idPoste);
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction();
			u.setPoste(p);
			session.save(u);
			transaction.commit(); 
			} catch (Exception e) { 
				if (transaction != null) { 
					transaction.rollback();
				} 
				System.out.println("ERROR: " + e.getMessage());
				}
		finally { 
			session.close();
		}
	}

	
	//methode pour recuperer la liste des utilisateurs
	
	public List<Utilisateur> getUtilisateurs() {
		List<Utilisateur> utilisateurs = null;
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction(); 
			 utilisateurs = session.createQuery("from Utilisateur order by type").list();
			transaction.commit();	
			} catch (Exception e) { 
				if (transaction != null) { 
					transaction.rollback();
				} 
				System.out.println("ERROR: " + e.getMessage());
				}
		finally { 
			session.close();
		}
		
		return utilisateurs;
	}
	

	
	
	public boolean supprimer(Utilisateur u) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Utilisateur utilisateur = session.get(Utilisateur.class, u.getId());	
			session.delete(utilisateur);
			transaction.commit();
			return true;
		}
		catch (Exception e) {
			if (transaction != null)
			{
				transaction.rollback();
			}
			System.out.println("ERROR: " + e.getMessage());
		} finally
		{
			session.close();
		}
		return false;
	} 
		
	



	public void update(int id, String nom, String prenom, String email, int idPoste, String password, String type) {
		Session session = factory.openSession();
		Poste p = session.get(Poste.class, idPoste);
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Utilisateur utilisateur = session.get(Utilisateur.class,id);
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setEmail(email);
			utilisateur.setPoste(p);
			utilisateur.setType(type);
			session.update(utilisateur);
			transaction.commit();
		}
		catch (Exception e) {
			if (transaction != null)
			{
				transaction.rollback();
			}
			System.out.println("ERROR: " + e.getMessage());
		} finally
		{
			session.close();
		}
	} 
	
	
	
	public void modifPassword(int idUser, String password) {
		Session session = factory.openSession();
		Utilisateur u = session.get(Utilisateur.class, idUser);
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			u.setPassword(password);
			session.update(u);
			transaction.commit();
		}
		catch (Exception e) {
			if (transaction != null)
			{
				transaction.rollback();
			}
			System.out.println("ERROR: " + e.getMessage());
		} finally
		{
			session.close();
		}
	}
	
	public static Utilisateur findUserByEmail(String email) {
			Utilisateur utilisateur = null;
			factory = HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction transaction = null; 
			try {
				transaction = session.beginTransaction(); 
				List<Utilisateur> utilisateurs = session.createQuery("from Utilisateur where email='"+email+"'").list();
				transaction.commit();
				if(!utilisateurs.isEmpty()) {
				utilisateur = utilisateurs.get(0);
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
			
			return utilisateur;
	}
	
	
	// trouver un utilisateur par son id
	
	public Utilisateur findUserById(int id) {
		Utilisateur utilisateur = null;
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction(); 
			utilisateur = (Utilisateur) session.createQuery("from Utilisateur where id="+id).list().get(0);
			transaction.commit();	
			} catch (Exception e) { 
				if (transaction != null) { 
					transaction.rollback();
				} 
				System.out.println("ERROR: " + e.getMessage());
				}
		finally { 
			session.close();
		}
		
		return utilisateur;
}
	
	public int nbreUtilisateurs() {
		int nbre =0;
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction(); 
			nbre=((Long) session.createQuery("select count(*) from Utilisateur").iterate().next() ).intValue();
			transaction.commit();
	
			} catch (Exception e) { 
				if (transaction != null) { 
					transaction.rollback();
				} 
				System.out.println("ERROR: " + e.getMessage());
				}
		finally { 
			session.close();
		}
		
		return nbre;
	}

	
}

