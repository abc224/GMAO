package com.gmao.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.entities.EtatEquipement;


public class EtatEquipementDao {
	private static SessionFactory factory;
	
	// methode pour ajouter un etat
		public void ajout(EtatEquipement ee) {
			// TODO Auto-generated method stub
			factory = HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction transaction = null; 
			try {
				transaction = session.beginTransaction();
				session.save(ee);
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
		
		
		public List<EtatEquipement> listeEtatEquipements(){
			List<EtatEquipement> listeEtatEquipements = null;
			factory = HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction transaction = null; 
			try {
				transaction = session.beginTransaction(); 
				listeEtatEquipements = session.createQuery("from EtatEquipement").list();
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
			
			return listeEtatEquipements;
		}
		
		
		
			public boolean supprimer(EtatEquipement ee) {
				// TODO Auto-generated method stub
				Session session = factory.openSession();
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					EtatEquipement etatEquipement = session.get(EtatEquipement.class, ee.getId());	
					session.delete(ee);
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
			
			
			public void update(int id, String libelle) {
				Session session = factory.openSession();
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					EtatEquipement ee = session.get(EtatEquipement.class,id);
					ee.setLibelle(libelle);
					
					session.update(ee);
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
