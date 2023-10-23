package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Region;
import fr.diginamic.jdbc.exception.ExceptionTechnique;

public class RegionDao {
	
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

	public List<Region> extraire() {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		ResultSet curseur = null;
		try {
			// Création d'une ArrayList avec la liste des régions
			monStatement = connection.prepareStatement("SELECT ID,NOM FROM REGION");
			curseur = monStatement.executeQuery();
			ArrayList<Region> listeRegion = new ArrayList<>();
			
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String nom = curseur.getString("REGION");
				
				listeRegion.add(new Region(id, nom));
			}
			
			return listeRegion;
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de l'extraction de la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, curseur);
		}
	}

	public void insert(Region reg) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		try {
			monStatement = connection.prepareStatement("INSERT INTO REGION (NOM) VALUES (?)");
			monStatement.setString(1, reg.getNom());
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

	public int update(String ancienNom, String nouveauNom) {	
		Connection connection = connect();
		PreparedStatement monStatement = null;
		int nb = 0;
		try {
			monStatement = connection.prepareStatement("UPDATE REGION SET NOM=? WHERE NOM=?");
			monStatement.setString(1, nouveauNom);
			monStatement.setString(2, ancienNom);
			nb = monStatement.executeUpdate();
			return nb;
		}		
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la modification dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, null);
		}
	}

	public boolean delete(Region reg) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		boolean isDeleted = false;
		
		try {
			monStatement = connection.prepareStatement("DELETE FROM REGION WHERE NOM=?");
			monStatement.setString(1, reg.getNom());
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
