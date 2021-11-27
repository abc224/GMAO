package com.gmao.backingBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gmao.dao.LoginDao;
import com.gmao.entities.Utilisateur;


@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean loggedIn;
	LoginDao loginDao = new LoginDao();
	
	@NotNull( message = "Veuillez saisir une adresse email" )
	@Pattern( regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Merci de saisir une adresse mail valide" )
	private String email;
	
	@NotNull( message = "Veuillez saisir un mot de passe" )
	@Size( min = 8, message = "Le mot de passe doit contenir au moins 8 caractères" )
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String doLogin() {
		
		Utilisateur u = loginDao.connexion(email, password);
		if(u != null) {
			loggedIn = true;
			
			if(u.getType().equals("admin")) {
				HttpSession session = Util.getSession();
	            session.setAttribute("idUser", u.getId());
	            loggedIn = true;
				return "/admin/accueilAdmin?faces-redirect=true";
			}else if(u.getType().equals("personnel")) {
				HttpSession session = Util.getSession();
	            session.setAttribute("idUser", u.getId());
	            loggedIn = true;
				return "/personnel/accueilPersonnel?faces-redirect=true";
			}
			
		
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email ou mot de passe incorrect!","Email" );
        FacesContext.getCurrentInstance().addMessage( null, message );
	return null;
	}
	
	public String doLogout() {
		HttpSession session = Util.getSession();
	      session.invalidate();
	      return "/index?faces-redirect=true";
	}
	
	
}

