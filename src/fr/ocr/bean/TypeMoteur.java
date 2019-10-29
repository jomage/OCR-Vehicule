package fr.ocr.bean;

/**
 * Représente une type de moteur.
 * Possède une description en texte et une valeur identifiante unique.
 * @author Jomage
 *
 */
public class TypeMoteur extends TableGenerique {

	private String description = "";
	private int id = 0;

	public TypeMoteur(int id, String nom) {
		super();
		this.description = nom;
		this.id = id;
	}

	public TypeMoteur() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String nom) {
		this.description = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return this.description;
	}
	
	public Object[] toArray() {
		Object[] array = new Object[2];
		array[0] = this.getId();
		array[1] = this.getDescription();
		return array;
	}
}
