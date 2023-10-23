package fr.diginamic.jdbc.entites;
import fr.diginamic.jdbc.utils.Format;

/**
 * Représente une ville 
 * @author Marjory PRIN
 */
public class Ville {
	
	/** l'ID de la ville **/
	private int id;
	
	/** le nom de la ville**/
	private String nom;
	
	/** la population totale **/
	private int population;
	
	/** l'ID du département où se trouve la ville **/
	private String idDep;
	
	/** l'ID de la région où se trouve la ville **/
	private int idReg;
	
	/** le nom de la région où se trouve la ville **/
	private String nomReg;
	
	
	
	/** Constructeur 
	 * @param id : id de ville
	 * @param nom : nom de la ville
	 * @param population : population totale de la ville
	 * @param idDep : id du département
	 * @param idReg : id de la région
	 */
	public Ville(int id, String nom, int population, String idDep, int idReg, String nomReg) {
		super();
		this.id = id;
		this.nom = nom;
		this.population = population;
		this.idDep = idDep;
		this.idReg = idReg;
		this.nomReg = nomReg;
	}


	/**
	 * Affichage d'une ville et de ses données
	 * @return le nom de la ville et sa population 
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		String result = s.append(nom).append(" : ").append(population).append(" hab.").toString();
		return result;
	}

	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/** Setter
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/** Getter
	 * @return the idDep
	 */
	public String getIdDep() {
		return idDep;
	}

	/** Setter
	 * @param idDep the idDep to set
	 */
	public void setIdDep(String idDep) {
		this.idDep = idDep;
	}

	/** Getter
	 * @return the idReg
	 */
	public int getIdReg() {
		return idReg;
	}

	/** Getter
	 * @return the nomReg
	 */
	public String getNomReg() {
		return nomReg;
	}


	/** Setter
	 * @param nomReg the nomReg to set
	 */
	public void setNomReg(String nomReg) {
		this.nomReg = nomReg;
	}


	/** Setter
	 * @param idReg the idReg to set
	 */
	public void setIdReg(int idReg) {
		this.idReg = idReg;
	}

	/** Getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	
	
}
