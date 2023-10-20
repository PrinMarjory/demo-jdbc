package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Fournisseur;
import fr.diginamic.jdbc.exception.ExceptionTechnique;

public class FournisseurDaoJdbc implements FournisseurDao {
	
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
		Connection connectionMaria = null;
		try {
			connectionMaria = DriverManager.getConnection("jdbc:mariadb://localhost:3306/compta", "root", "");
		}
		catch (SQLException e){
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la connexion à la base de données", e);
		}	
		return connectionMaria;
	}
	
	private void deconnect(Connection c, Statement s, ResultSet r) {
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

	@Override
	public List<Fournisseur> extraire() {
		Connection connection = connect();
		Statement monStatement = null;
		ResultSet curseur = null;
		try {
			// Création d'une ArrayList avec la liste des fournisseurs
			monStatement = connection.createStatement();
			curseur = monStatement.executeQuery("SELECT ID,NOM FROM FOURNISSEUR");
			ArrayList<Fournisseur> listeFournisseur = new ArrayList<>();
			
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				
				listeFournisseur.add(new Fournisseur(id, nom));
			}
			
			return listeFournisseur;
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de l'extraction de la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, curseur);
		}
	}

	@Override
	public void insert(Fournisseur fournisseur) {
		Connection connection = connect();
		Statement monStatement = null;
		try {
			monStatement = connection.createStatement();
			StringBuilder s = new StringBuilder("");
			s = s.append("INSERT INTO FOURNISSEUR (ID,NOM) VALUES (").append(fournisseur.getId())
					.append(",'").append(fournisseur.getNom()).append("')");
			String sql = s.toString();
			int nb = monStatement.executeUpdate(sql);
		}
		catch (SQLIntegrityConstraintViolationException e) {
			System.err.println("Cet ID existe déjà dans la base");
		}		
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de l'insertion dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, null);
		}
	}

	@Override
	public int update(String ancienNom, String nouveauNom) {	
		Connection connection = connect();
		Statement monStatement = null;
		int nb = 0;
		try {
			monStatement = connection.createStatement();
			StringBuilder s = new StringBuilder("");
			s = s.append("UPDATE FOURNISSEUR SET NOM='").append(nouveauNom)
					.append("' WHERE NOM='").append(ancienNom).append("'");
			String sql = s.toString();
			nb = monStatement.executeUpdate(sql);
		}		
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la modification dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, null);
			return nb;
		}
	}

	@Override
	public boolean delete(Fournisseur fournisseur) {
		Connection connection = connect();
		Statement monStatement = null;
		boolean isDeleted = false;
		
		try {
			monStatement = connection.createStatement();
			StringBuilder s = new StringBuilder("");
			s = s.append("DELETE FROM FOURNISSEUR WHERE ID=").append(fournisseur.getId());
			String sql = s.toString();
			int nb = monStatement.executeUpdate(sql);
		}	
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de la suppression d'un élément dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, null);
			return isDeleted;
		}
	}

}
