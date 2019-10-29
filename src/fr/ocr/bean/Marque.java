package fr.ocr.bean;

/**
 * Modèle pour la table Marque.
 * Représente une marque de constructeur automobile.
 * Une marque a une valeur identifiante unique "id" et un nom.
 * @author Jomage
 *
 */
public class Marque extends TableGenerique {

	private int id = 0;
	private String nom = "";

	public Marque() {
	}

	public Marque(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String toString() {
		return this.nom;
	}

	public Object[] toArray() {
		Object[] array = new Object[2];
		array[0] = this.getId();
		array[1] = this.getNom();
		return array;
	}
}