package fr.ocr.bean;

/**
 * Représente une option de voiture.
 * Une option a une valeur identifiante unique "id", une description en texte
 * et un prix en nombre à virgule double précision.
 * @author Jomage
 *
 */
public class Option extends TableGenerique {

	private int id = 0;
	private String description = "";
	private double prix = 0d;

	public Option() {
	}

	public Option(int id, String nom, double prix) {
		this.id = id;
		this.description = nom;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String nom) {
		this.description = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String toString() {
		return description;
	}
	
	public Object[] toArray() {
		Object[] array = new Object[3];
		array[0] = this.getId();
		array[1] = this.getDescription();
		array[2] = this.getPrix();
		return array;
	}

}
