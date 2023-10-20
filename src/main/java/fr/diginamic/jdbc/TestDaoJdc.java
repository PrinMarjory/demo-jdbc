package fr.diginamic.jdbc;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestDaoJdc {

	public static void main(String[] args) {
		FournisseurDaoJdbc fDao = new FournisseurDaoJdbc();
		
		// Insertion d'un fournisseur
		Fournisseur f1 = new Fournisseur(4, "France de matériaux");
		fDao.insert(f1);
		
		// Extraction de la liste des fournisseurs
		List<Fournisseur> listeFournisseur = new ArrayList<>();
		listeFournisseur = fDao.extraire();
		System.out.println("Liste des fournisseurs après ajout :");
		
		// Affichage de la liste des forunisseurs
		for (Fournisseur f : listeFournisseur) {
			System.out.println(f);
		}
		
		// Modification du fournisseur
		fDao.update("France de matériaux", "France matériaux");
		
		// Extraction de la liste des fournisseurs
		listeFournisseur = fDao.extraire();
		System.out.println("\nNouvelle liste des fournisseurs après modification :");
		
		// Affichage de la liste des forunisseurs
		for (Fournisseur f : listeFournisseur) {
			System.out.println(f);
		}
		
		// Suppression du fournisseur
		fDao.delete(f1);
		
		// Extraction de la liste fianale des fournisseurs 
		listeFournisseur = fDao.extraire();
		System.out.println("\nListe finale des fournisseurs après supression :");
		
		// Affichage de la liste des forunisseurs
		for (Fournisseur f : listeFournisseur) {
			System.out.println(f);
		}
	}

}
