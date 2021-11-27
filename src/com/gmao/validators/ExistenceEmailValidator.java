package com.gmao.validators;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gmao.config.HibernateUtil;
import com.gmao.dao.DaoException;


@FacesValidator(value="emailValidator")

public class ExistenceEmailValidator implements Validator {

    private static final String EMAIL_EXISTE_DEJA = "Cette adresse email est déjà utilisée";
    private static SessionFactory factory;

    public static String verifierExistenceEmail(String email) {
		factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = null; 
		try {
			transaction = session.beginTransaction();
			List<?> utilisateur = session.createQuery("from Utilisateur where email='"+email+"'").list();
			transaction.commit(); 
			if(utilisateur.size()!=0) {
				return "existe";
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
    

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
        /* Recuperation de la valeur a traiter depuis le parametre value */
        String email = (String) value;
        try {
            if ( verifierExistenceEmail(email) != null ) {
                throw new ValidatorException(
                        new FacesMessage( FacesMessage.SEVERITY_ERROR, EMAIL_EXISTE_DEJA, null ) );
            }
        } catch ( DaoException e ) {
            FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR, e.getMessage(), null );
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage( component.getClientId( facesContext ), message );
        }
    }
}
