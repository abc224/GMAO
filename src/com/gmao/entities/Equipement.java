package com.gmao.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "equipement")
public class Equipement {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_NAME")
	@SequenceGenerator(name = "SEQUENCE_NAME", sequenceName = "SEQUENCE_NAME", allocationSize = 1, initialValue = 1)
	private int id;
	
	@Column(name="code", length=50, nullable=true)
	private String code;
	
	@Column(name="libelle", length=50, nullable=true)
	private String libelle;
	
	@Column(name="marque", length=50, nullable=true)
	private String marque;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dateMiseEnMarche;
	
	@ManyToOne
	@JoinColumn(name = "id_secteur", foreignKey = @ForeignKey(name = "EQUIPEMENT_SECTEUR_ID_FK"))
	private Secteur secteur;
	
	@ManyToOne
	@JoinColumn(name = "id_etat_equipement", foreignKey = @ForeignKey(name = "EQUIPEMENT_ETAT_ID_FK"))
	private EtatEquipement etatEquipement;
	
	//les getters et les setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Date getDateMiseEnMarche() {
		return dateMiseEnMarche;
	}

	public void setDateMiseEnMarche(Date dateMiseEnMarche) {
		this.dateMiseEnMarche = dateMiseEnMarche;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public Secteur getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}

	public EtatEquipement getEtatEquipement() {
		return etatEquipement;
	}

	public void setEtatEquipement(EtatEquipement etatEquipement) {
		this.etatEquipement = etatEquipement;
	}

	
}
