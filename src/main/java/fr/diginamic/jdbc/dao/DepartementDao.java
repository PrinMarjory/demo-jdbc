package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Departement;
import fr.diginamic.jdbc.exception.ExceptionTechnique;

public class DepartementDao {
	
	private Connection connect() {
		
		// Chargement driver
		try {
			DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors du chargement du Driver", e);
		}
		
		// Connexion à la base
		Connection connection = null;
		try {
			connection= DriverManager.getConnection("jdbc:mariadb://localhost:3306/recensement", "root", "");
		}
		catch (SQLException e){
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la connexion à la base de données", e);
		}	
		return connection;
	}
	
	private void deconnect(Connection c, PreparedStatement s, ResultSet r) {
		try {
			if (r != null) {
				r.close();
			}
			if (s != null) {
				s.close();
			}
			if (c != null) {
				c.close();
			}
		}
		catch (SQLException e){
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la déconnexion à la base de données", e);
		}
	}

	public List<Departement> extraire() {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		ResultSet curseur = null;
		try {
			// Création d'une ArrayList avec la liste des départements
			monStatement = connection.prepareStatement("SELECT ID FROM DEPARTEMENT");
			curseur = monStatement.executeQuery();
			ArrayList<Departement> listeDepartement = new ArrayList<>();
			
			while (curseur.next()) {
				String id = curseur.getString("ID");
				listeDepartement.add(new Departement(id));
			}
			
			return listeDepartement;
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de l'extraction de la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, curseur);
		}
	}

	public void insert(Departement dep) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		try {
			monStatement = connection.prepareStatement("INSERT INTO DEPARTEMENT (ID) VALUES (?)");
			monStatement.setString(1, dep.getId());
			monStatement.executeUpdate();
		}	
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de l'insertion dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, null);
		}
	}

	public boolean delete(Departement dep) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		boolean isDeleted = false;
		
		try {
			monStatement = connection.prepareStatement("DELETE FROM DEPARTEMENT WHERE ID ?");
			monStatement.setString(1, dep.getId());
			monStatement.executeUpdate();
			return isDeleted;
		}	
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la suppression d'un élément dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, null);
		}
	}

}
