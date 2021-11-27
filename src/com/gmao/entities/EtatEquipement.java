package com.gmao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "etatequipement")
public class EtatEquipement {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_NAME")
	@SequenceGenerator(name = "SEQUENCE_NAME", sequenceName = "SEQUENCE_NAME", allocationSize = 1, initialValue = 1)
	private int id;
	
	@Column(name="libelle", length=50, nullable=true)
	private String libelle;
	
//les getters et les setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public EtatEquipement() {
		// TODO Auto-generated constructor stub
	}
	
	//constructeur sans paramètre

	public String getLibelle() {
		return libelle;
	}



	
	
}