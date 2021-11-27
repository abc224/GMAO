package com.gmao.backingBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import com.gmao.dao.PosteDao;
import com.gmao.dao.UtilisateurDao;
import com.gmao.entities.Poste;
import com.gmao.entities.Utilisateur;
import com.sun.faces.context.SessionMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Named
@SessionScoped

public class UtilisateurBean implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
		
	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private SessionMap sessionMap =  (SessionMap) externalContext.getSessionMap();
	int idUser =  (int) sessionMap.get("idUser");
	
	private boolean loggedIn;

	private Boolean disabled = true;
	
	private int id;
	@NotNull( message = "Veuillez saisir un nom" )
	private String nom;
	
	@NotNull( message = "Veuillez saisir un prenom" )
	private String prenom;
	
	@NotNull( message = "Veuillez saisir une adresse email" )
	@Pattern( regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Merci de saisir une adresse mail valide" )
	private String email;
	
	@NotNull( message = "Veuillez saisir un mot de passe" )
	@Size( min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres" )
	private String password;
	
	private String type;
	
	private Poste poste;
	
	private int idPoste;
	
	Utilisateur selectedUtilisateur;
	List<Utilisateur> utilisateurs;
	List<Poste> postes;
	private int nbreUtilisateurs;
	UtilisateurDao utilisateurDao = new UtilisateurDao();
	PosteDao posteDao = new PosteDao();	
	
	
	
	public void ajout() {
		Utilisateur user = new Utilisateur();
		user.setId(id);
		user.setType(type);
		user.setEmail(email);
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setPassword("00000000");	
		utilisateurDao.ajout(user, idPoste);
		addDetailMessage("personnel ajouté avec succes");
	}
	

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurDao.getUtilisateurs();
	}
	
   public void supprimer() {
     
       if(utilisateurDao.supprimer(selectedUtilisateur)) {
    	   disabled=true;
    	   addDetailMessage("utilisateur supprimé avec succes" ); 
       }
       else {
    	   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Impossible de supprimer cet utilisateur","");
           FacesContext.getCurrentInstance().addMessage( null, message );
       } 
    }
  
   
   public void update() { 
	   utilisateurDao.update(selectedUtilisateur.getId(), selectedUtilisateur.getNom(), selectedUtilisateur.getPrenom(), selectedUtilisateur.getEmail(),selectedUtilisateur.getPoste().getId(), selectedUtilisateur.getPassword(), selectedUtilisateur.getType());
	   addDetailMessage("utilisateur modifié avec succes" );
   }
   
   public void modifPassword() {
	   utilisateurDao.modifPassword(idUser, password);
	   addDetailMessage("Mot de passe modifié avec succes");
   }
   
   public void modifPasswordByAdmin() {
	   utilisateurDao.modifPassword(selectedUtilisateur.getId(), password);
	   addDetailMessage("Mot de passe modifié avec succes");
   }
   
 
   

   
   public void reset() {
       PrimeFaces.current().resetInputs("form:panel");
   }
   
 //les getters et les setters
  

	public void setUtilisateurs(List utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setType(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	
	 
	 
	 
	 public int getNbreUtilisateurs() {
		return utilisateurDao.nbreUtilisateurs();
	}

	public void setNbreUtilisateurs(int nbreUtilisateurs) {
		this.nbreUtilisateurs = nbreUtilisateurs;
	}

	public Utilisateur getSelectedUtilisateur() {
		return selectedUtilisateur;
	}

	public void setSelectedUtilisateur(Utilisateur selectedUtilisateur) {
		this.selectedUtilisateur = selectedUtilisateur;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public int getIdPoste() {
		return idPoste;
	}

	public void setIdPoste(int idPoste) {
		this.idPoste = idPoste;
	}

	public Poste getPoste() {
		return poste;
	}

	public void setPoste(Poste poste) {
		this.poste = poste;
	}

	public List<Poste> getPostes() {
		return posteDao.listePostes();
	}

	public void setPostes(List<Poste> postes) {
		this.postes = postes;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public void onRowSelect() {
	    disabled = false;
	}
	
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


