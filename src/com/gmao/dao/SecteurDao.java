package com.gmao.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.entities.Secteur;



public class SecteurDao {
	private static SessionFactory factory;
	
	// methode pour ajouter un secteur
		public void ajout(Secteur s) {
			// TODO Auto-generated method stub
			factory = HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction transaction = null; 
			try {
				transaction = session.beginTransaction();
				session.save(s);
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
		
		
		//recuperation de tous les secteurs
		
		public List<Secteur> listeSecteurs(){
			List<Secteur> listeSecteurs = null;
			factory = HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction transaction = null; 
			try {
				transaction = session.beginTransaction(); 
				listeSecteurs = session.createQuery("from Secteur").list();
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
			
			return listeSecteurs;
		}
		
		
		// suppression d'un secteur
			public boolean supprimer(Secteur s) {
				// TODO Auto-generated method stub
				Session session = factory.openSession();
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					Secteur secteur = session.get(Secteur.class, s.getId());	
					session.delete(secteur);
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
			
			//modification d'un secteur
			public void update(int id, String libelle) {
				Session session = factory.openSession();
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					Secteur secteur = session.get(Secteur.class,id);
					secteur.setLibelle(libelle);
					session.update(secteur);
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
