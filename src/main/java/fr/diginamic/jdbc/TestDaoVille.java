package fr.diginamic.jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.dao.VilleDao;
import fr.diginamic.jdbc.entites.Ville; 

public class TestDaoVille {

	public static void main(String[] args) throws IOException {
		
		// Initialisation et création de la liste des villes
		Path path = Paths.get("C:/Users/marjo/Documents/Diginamic/Java/approche-objet/recensement.csv");
		ArrayList<Ville> listeVilles = new ArrayList<>();
		int population = 0;
		String[] lineSplit = new String[0];
		
		// Lecture du fichier
		List<String> lines = Files.readAllLines(path);
		
		// Remplissage de la liste
		for (int i = 1; i<lines.size(); i++) {
			lineSplit = lines.get(i).split(";");
			population = Integer.parseInt(lineSplit[9].trim().replaceAll(" ", ""));
			listeVilles.add(new Ville(-1, lineSplit[6], population, lineSplit[2], Integer.parseInt(lineSplit[0]), lineSplit[1]));
		}

		// Insertion dans la base
		VilleDao vDao = new VilleDao();
		
		long tempsDepart = System.currentTimeMillis();
		vDao.insertAll(listeVilles);
		long tempsFin = System.currentTimeMillis();
		
		long chrono = tempsFin - tempsDepart;
		System.out.println("Chrono : " + chrono);
	}

}
