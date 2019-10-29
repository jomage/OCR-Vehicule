package fr.ocr.bean;

/**
 * Représente un Moteur.
 * Un moteur a une valeur identifiante unique "id", un type de moteur, un 
 * prix en nombre flottant double précision et une cylindrée exprimée en texte.
 * @author Jomage
 *
 */
public class Moteur extends TableGenerique {

	private int id = 0;
	private TypeMoteur type;
	private Double prix;
	private String cylindre;

	public Moteur() {
	}

	public Moteur(int id, TypeMoteur type, String cyl, Double pPrix) {
		this.id = id;
		this.type = type;
		this.cylindre = cyl;
		this.prix = pPrix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeMoteur getType() {
		return type;
	}

	public void setType(TypeMoteur type) {
		this.type = type;
	}

	public String getCylindre() {
		return cylindre;
	}

	public void setCylindre(String cylindre) {
		this.cylindre = cylindre;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Double getPrix() {
		return this.prix;
	}

	public String toString() {
		return type + " " + cylindre;
	}
	
	public Object[] toArray() {
		Object[] array = new Object[4];
		array[0] = this.getId();
		array[1] = this.getCylindre();
		array[2] = this.getType();
		array[3] = this.getPrix();
		return array;
	}
}
