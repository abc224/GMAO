package com.gmao.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.entities.Equipement;
import com.gmao.entities.EtatEquipement;
import com.gmao.entities.Secteur;



public class EquipementDao {
private static SessionFactory factory;
	
	// methode pour ajouter un equipement
	public void ajout(Equipement equipement,int idSecteur, int idEtatEquipement) {
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Secteur secteur = session.get(Secteur.class, idSecteur);
		EtatEquipement etat = session.get(EtatEquipement.class, idEtatEquipement);
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction();
			equipement.setSecteur(secteur);
			equipement.setEtatEquipement(etat);

			session.save(equipement);
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
	
	//methode pour recuperer la liste des equipements
	public List<Equipement> listeEquipements(){
		List<Equipement> listeEquipements = null;
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction(); 
			listeEquipements = session.createQuery("from Equipement").list();
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
		
		return listeEquipements;
	}
	
	//methode pour recuperer la liste des equipements par secteur
		public List<Equipement> listeEquipements(int idSecteur){
			List<Equipement> listeEquipements = null;
			factory = HibernateUtil.getSessionFactory();
			Session session = factory.openSession();
			Transaction transaction = null; 
			try {
				transaction = session.beginTransaction(); 
				listeEquipements = session.createQuery("from Equipement where id_secteur="+idSecteur).list();
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
			
			return listeEquipements;
		}
		
	
	// methode pour supprimer un equipement
	public void supprimer(Equipement equipement) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Equipement e = session.get(Equipement.class, equipement.getId());
			session.delete(e);
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
	
	//methode pour modifier un equipement
	public void update(int id,String code, String libelle, String marque, Date dateMiseEnMarche, int idSecteur, int idEtatEquipement) {
		Session session = factory.openSession();
		Secteur secteur = session.get(Secteur.class, idSecteur);
		EtatEquipement etat = session.get(EtatEquipement.class, idEtatEquipement);
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Equipement equipement = session.get(Equipement.class,id);
			equipement.setCode(code);
			equipement.setLibelle(libelle);
			equipement.setMarque(marque);
			equipement.setDateMiseEnMarche(dateMiseEnMarche);
			equipement.setSecteur(secteur);
			equipement.setEtatEquipement(etat);
		
			session.update(equipement);
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
	
	public int nbreEquipements() {
		int nbre =0;
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction(); 
			nbre=((Long) session.createQuery("select count(*) from Equipement").iterate().next() ).intValue();
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
