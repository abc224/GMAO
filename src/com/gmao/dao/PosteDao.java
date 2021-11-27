package com.gmao.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.entities.Poste;


public class PosteDao {
	private static SessionFactory factory;
	
	// methode pour ajouter un poste

	public void ajout(Poste p) {
		// TODO Auto-generated method stub
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction();
			session.save(p);
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
	
	
	//recuperation de tous les postes
	
	public List<Poste> listePostes(){
		List<Poste> listePostes = null;
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction(); 
			listePostes = session.createQuery("from Poste").list();
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
		
		return listePostes;
	}
	
	// suppression d'un poste
		public boolean supprimer(Poste p) {
			// TODO Auto-generated method stub
			Session session = factory.openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				Poste poste = session.get(Poste.class, p.getId());	
				session.delete(poste);
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
		
		//modification d'un poste
		public void update(int id, String nom) {
			Session session = factory.openSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				Poste poste = session.get(Poste.class,id);
				poste.setNom(nom);
				session.update(poste);
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
		
}

