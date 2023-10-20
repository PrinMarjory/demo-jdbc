package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import fr.diginamic.jdbc.exception.ExceptionTechnique;

public class TestInsertion {

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
			
			// Ajout d'un fournisseur
			Statement monStatement = connectionMaria.createStatement();
			try {
			int nb = monStatement.executeUpdate("INSERT INTO FOURNISSEUR (ID,NOM) VALUES (4,'La Maison de la Peinture')");
			}
			catch (SQLIntegrityConstraintViolationException e) {
				System.err.println("Cet ID existe déjà dans la base");
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
