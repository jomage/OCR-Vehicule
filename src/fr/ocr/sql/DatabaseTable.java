package fr.ocr.sql;

/**
 * Classe qui liste les tables manipulables ainsi que le nom de leurs colonnes.
 * @author cysboy
 */
public enum DatabaseTable {

	VEHICULE("vehicule", new String[] {"id", "marque", "nom", "moteur", "prix"}),
	MARQUE("marque", new String[] {"id", "nom"}),
	TYPEMOTEUR("type_moteur", new String[] {"id", "description"}),
	MOTEUR("moteur", new String[] {"id", "cylindre", "moteur", "prix"}),
	OPTION("option", new String[] {"id", "description", "prix"}),
	VEHICULE_OPTION("vehicule_option", new String[] {"id_vehicule", "id_option"});

	private String name = "";
	private String[] columns;

	DatabaseTable(String name, String[] columns) {
		this.name = name;
		this.columns = columns;
	}

	public String toString() {
		return name + columns;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getColumns() {
		return columns;
	}
}
