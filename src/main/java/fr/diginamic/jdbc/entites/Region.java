package fr.diginamic.jdbc.entites;

/**
 * Représente une région avec son id et son nom
 * 
 * @author Marjory PRIN
 */
public class Region {
	
	/** L'id de la région **/
	private int id;
	
	/** Le nom de la région **/
	private String nom;

	/**
	 * Constructeur 
	 * @param id : id de la région
	 * @param nom : nom de la région
	 */
	public Region(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
}
