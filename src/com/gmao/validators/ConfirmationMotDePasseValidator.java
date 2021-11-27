package com.gmao.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator( value = "confirmationMotDePasseValidator" )
public class ConfirmationMotDePasseValidator implements Validator {

    private static final String CHAMP_MOT_DE_PASSE       = "composantMotDePasse";
    private static final String MOTS_DE_PASSE_DIFFERENTS = "Le mot de passe et la confirmation doivent être identiques.";

    @Override
    public void validate( FacesContext context, UIComponent component, Object value ) throws ValidatorException {
        /*
         * Recuperation de l'attribut mot de passe parmi la liste des attributs
         * du composant confirmation
         */
        UIInput composantMotDePasse = (UIInput) component.getAttributes().get( CHAMP_MOT_DE_PASSE );
        /*
         * Recuperation de la valeur du champ, c'est-a-dire le mot de passe
         * saisi
         */
        String motDePasse = (String) composantMotDePasse.getValue();
        /* Recuperation de la valeur du champ confirmation */
        String confirmation = (String) value;

        if ( confirmation != null && !confirmation.equals( motDePasse ) ) {
            /*
             * Envoi d'une exception contenant une erreur de validation JSF
             * initialisee avec le message destinee l'utilisateur, si les mots
             * de passe sont differents
             */
            throw new ValidatorException(
                    new FacesMessage( FacesMessage.SEVERITY_ERROR, MOTS_DE_PASSE_DIFFERENTS, null ) );
        }
    }
}

