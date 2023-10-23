package fr.diginamic.jdbc.entites;

/**
 * Représente un département avec son id et son code
 * 
 * @author Marjory PRIN
 */
public class Departement {
	
	/** L'id du département **/
	private String id;
	
	/**
	 * Constructeur 
	 * @param id : id du département
	 */
	public Departement(String id) {
		super();
		this.id = id;
	}

	/** Getter
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
}
