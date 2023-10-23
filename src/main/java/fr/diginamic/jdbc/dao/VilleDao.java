package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Departement;
import fr.diginamic.jdbc.entites.Ville;
import fr.diginamic.jdbc.exception.ExceptionTechnique;

public class VilleDao {
	
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

//	public List<Ville> extraire() {
//		Connection connection = connect();
//		PreparedStatement monStatement = null;
//		ResultSet curseur = null;
//		try {
//			// Création d'une ArrayList avec la liste des villes
//			monStatement = connection.prepareStatement("SELECT ID,NOM,POPULATION,ID_DEP,ID_REG FROM VILLE");
//			curseur = monStatement.executeQuery();
//			ArrayList<Ville> listeVille = new ArrayList<>();
//			
//			while (curseur.next()) {
//				Integer id = curseur.getInt("ID");
//				String nom = curseur.getString("NOM");
//				Integer pop = curseur.getInt("POPULATION");
//				String idDep = curseur.getString("ID_DEP");
//				Integer idReg = curseur.getInt("ID_REG");
//				
//				listeVille.add(new Ville(id, nom, pop, idDep, idReg));
//			}
//			
//			return listeVille;
//		}
//		catch (SQLException e) {
//			System.err.println(e.getMessage());
//			throw new ExceptionTechnique("Un problème est survenu lors de l'extraction de la base de données", e);
//		}
//		finally {
//			deconnect(connection, monStatement, curseur);
//		}
//	}

	public void insert(Ville v) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		try {
			monStatement = connection.prepareStatement("INSERT INTO VILLE (NOM, POPULATION, ID_DEP, ID_REG) VALUES (?,?,?,?)");
			monStatement.setString(1, v.getNom());
			monStatement.setInt(2, v.getPopulation());
			monStatement.setString(3, v.getIdDep());
			monStatement.setInt(4, v.getIdReg());
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
	
	public void insertAll(ArrayList<Ville> listeVilles) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		ResultSet curseur = null;
		String idDep = "";
		int idReg = -1;
		String nomVille = "";
		
		try {
			for (Ville v: listeVilles) {
				
				// Recherche si département existe déjà dans la table sinon on le créé
				idDep = "";
				monStatement = connection.prepareStatement("SELECT ID FROM DEPARTEMENT WHERE ID=?");
				monStatement.setString(1, v.getIdDep());
				curseur = monStatement.executeQuery();
				while (curseur.next()) {
					idDep = curseur.getString("ID");
				}
				if (idDep == "") {
					monStatement = connection.prepareStatement("INSERT INTO DEPARTEMENT (ID) VALUES (?)");
					monStatement.setString(1, v.getIdDep());
					monStatement.executeUpdate();
				}
				
				// Recherche si région existe déjà dans la table sinon on le créée
				idReg = -1;
				monStatement = connection.prepareStatement("SELECT ID, NOM FROM REGION WHERE ID=?");
				monStatement.setInt(1, v.getIdReg());
				curseur = monStatement.executeQuery();
				while (curseur.next()) {
					idReg = curseur.getInt("ID");
				}
				if (idReg == -1) {
					monStatement = connection.prepareStatement("INSERT INTO REGION (ID, NOM) VALUES (?, ?)");
					monStatement.setInt(1, v.getIdReg());
					monStatement.setString(2, v.getNomReg());
					monStatement.executeUpdate();
				}
				
				// Ajout de la ville
				monStatement = connection.prepareStatement("INSERT INTO VILLE (NOM, POPULATION, ID_DEP, ID_REG) VALUES (?, ?, ?, ?)");
				monStatement.setString(1, v.getNom());
				monStatement.setInt(2, v.getPopulation());
				monStatement.setString(3, v.getIdDep());
				monStatement.setInt(4, v.getIdReg());
				monStatement.executeUpdate();			
			}
		}	
		
		catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new ExceptionTechnique("Un problème est survenu lors de l'insertion dans la base de données", e);
		}
		finally {
			deconnect(connection, monStatement, curseur);
		}
	}

	public int updateNom(String ancienNom, String nouveauNom) {	
		Connection connection = connect();
		PreparedStatement monStatement = null;
		int nb = 0;
		try {
			monStatement = connection.prepareStatement("UPDATE VILLE SET NOM=? WHERE NOM=?");
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

	public boolean delete(Ville v) {
		Connection connection = connect();
		PreparedStatement monStatement = null;
		boolean isDeleted = false;
		
		try {
			monStatement = connection.prepareStatement("DELETE FROM VILLE WHERE NOM=?");
			monStatement.setString(1, v.getNom());
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
