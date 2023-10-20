package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Fournisseur;
import fr.diginamic.jdbc.exception.ExceptionTechnique;

public class TestSelect {

	public static void main(String[] args) {
		
		// Chargement driver
		try {
			DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors du chargement du Driver", e);
		}
		
		// Connection à la base
		Connection connectionMaria = null;
		try {
			connectionMaria = DriverManager.getConnection("jdbc:mariadb://localhost:3306/compta", "root", "");
			System.out.println("Connection réalisée avec succès !");
			
			// Création d'une ArrayList avec la liste des fournisseurs
			Statement monStatement = connectionMaria.createStatement();
			ResultSet curseur = monStatement.executeQuery("SELECT ID,NOM FROM FOURNISSEUR");
			ArrayList<Fournisseur> listeFournisseur = new ArrayList<>();
			
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				
				listeFournisseur.add(new Fournisseur(id, nom));
			}
			curseur.close();
			
			// Affichage de la liste des forunisseurs
			for (Fournisseur f : listeFournisseur) {
				System.out.println(f);
			}
			
			// Déconnection
			monStatement.close();
			connectionMaria.close();
			System.out.println("Déconnection réalisée avec succès !");
		
		}
		catch (SQLException e){
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la connexion à la base de données", e);
		}
		
		finally {
			try {
				if (connectionMaria != null && !connectionMaria.isClosed()) {
					connectionMaria.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
