package com.gmao.backingBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;

import com.gmao.dao.EquipementDao;
import com.gmao.dao.EtatEquipementDao;
import com.gmao.dao.SecteurDao;
import com.gmao.entities.Equipement;
import com.gmao.entities.EtatEquipement;
import com.gmao.entities.Secteur;

@Named("equipementBean")
@SessionScoped
public class EquipementBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	EquipementDao equipementDao = new EquipementDao();
	SecteurDao secteurDao = new SecteurDao();
	EtatEquipementDao etatEquipementDao = new EtatEquipementDao();
	
	private Boolean disabled = true;
	
	private int id;
	
	@NotNull( message = "Veuillez saisir un code" )
	private String code;
	
	@NotNull( message = "Veuillez saisir un libelle" )
	private String libelle;
	
	@NotNull( message = "Veuillez saisir une marque" )
	private String marque;
	
	@NotNull(message = "Veuillez choisir une date")
	private Date dateMiseEnMarche = new Date();
	
	@Min(value=1, message="choisissez un secteur")
	private int idSecteur;
	
	@NotNull(message= "Veuillez choisir un secteur")
	private Secteur secteur;
	
	@Min(value=1, message="choisissez un etat")
	private int idEtatEquipement;
	
	@NotNull(message = "veuillez choisir un etat")
	private EtatEquipement etat;
	
	private List<Secteur> listeSecteurs;
	private List<EtatEquipement> listeEtatEquipements;
	
	private List<Equipement> listeEquipements;
	private Equipement selectedEquipement;
	private List<Equipement> filteredEquipements = equipementDao.listeEquipements();
	
	
	private int nbreEquipements;
	
	
	//les getters et les setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public Date getDateMiseEnMarche() {
		return dateMiseEnMarche;
	}
	public void setDateMiseEnMarche(Date dateMiseEnMarche) {
		this.dateMiseEnMarche = dateMiseEnMarche;
	}
	public int getIdSecteur() {
		return idSecteur;
	}
	public void setIdSecteur(int idSecteur) {
		this.idSecteur = idSecteur;
	}
	public Secteur getSecteur() {
		return secteur;
	}
	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}
	public int getIdEtatEquipement() {
		return idEtatEquipement;
	}
	public void setIdEtatEquipement(int idEtatEquipement) {
		this.idEtatEquipement = idEtatEquipement;
	}
	public EtatEquipement getEtat() {
		return etat;
	}
	public void setEtat(EtatEquipement etat) {
		this.etat = etat;
	}
	public List<Secteur> getListeSecteurs() {
		return secteurDao.listeSecteurs();
	}
	public void setListeSecteurs(List<Secteur> listeSecteurs) {
		this.listeSecteurs = listeSecteurs;
	}
	public List<EtatEquipement> getListeEtatEquipements() {
		return etatEquipementDao.listeEtatEquipements();
	}
	public void setListeEtatEquipements(List<EtatEquipement> listeEtatEquipements) {
		this.listeEtatEquipements = listeEtatEquipements;
	}
	


	public List<Equipement> getListeEquipements() {
		return equipementDao.listeEquipements();	
	}
	public void setListeEquipements(List<Equipement> listeEquipements) {
		this.listeEquipements = listeEquipements;
	}
	public Equipement getSelectedEquipement() {
		return selectedEquipement;
	}
	public void setSelectedEquipement(Equipement selectedEquipement) {
		this.selectedEquipement = selectedEquipement;
	}
	

	
	
	
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public List<Equipement> getFilteredEquipements() {
		return filteredEquipements;
	}

	public void setFilteredEquipements(List<Equipement> filteredEquipements) {
		this.filteredEquipements = filteredEquipements;
	}



	public int getNbreEquipements() {
		return equipementDao.nbreEquipements();
	}

	public void setNbreEquipements(int nbreEquipements) {
		this.nbreEquipements = nbreEquipements;
	}

		//les methodes
		public void onSecteurChange() {
			if(idSecteur!=0) {
			listeEquipements = equipementDao.listeEquipements(idSecteur);
			}
			else {
				listeEquipements = equipementDao.listeEquipements();
			}
		}
		
	
		
		public void onRowSelect(SelectEvent event) {
			disabled = false;
		}
		
		//methode pour ajouter un equipement
		public void ajout() {
			Equipement e = new Equipement();
			e.setId(id);
			e.setCode(code);
			e.setLibelle(libelle);
			e.setMarque(marque);
			e.setDateMiseEnMarche(dateMiseEnMarche);;
			equipementDao.ajout(e,idSecteur, idEtatEquipement);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Equipement ajouté avec succes","");
	        FacesContext.getCurrentInstance().addMessage( null, message );			
		}	
		
		
		//methode pour modifier un equipement
		public void update() {
		   Equipement e = selectedEquipement;
		   equipementDao.update(e.getId(), e.getCode(),e.getLibelle(), e.getMarque(), e.getDateMiseEnMarche(), e.getSecteur().getId(), e.getEtatEquipement().getId());
		   addDetailMessage("equipement modifié avec succes" );
		   }
		
		
		//methode pour supprimer un equipement
		public void supprimer() {
		        equipementDao.supprimer(selectedEquipement);
		        disabled=true;
		        addDetailMessage("Equipement supprimé avec succes" ); 
		    }
		

		
		
		
		//methodes pour afficher les messages  
		   public static void addDetailMessage(String message, FacesMessage.Severity severity) {

		       FacesMessage facesMessage = Messages.create("").detail(message).get();
		       if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
		           facesMessage.setSeverity(severity);
		       }
		       Messages.add(null, facesMessage);
		   }
			
		   public static void addDetailMessage(String message) {
		       addDetailMessage(message, null);
		   }		

	

}