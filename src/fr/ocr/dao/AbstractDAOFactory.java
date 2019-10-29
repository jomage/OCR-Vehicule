package fr.ocr.dao;

import fr.ocr.bean.Marque;
import fr.ocr.bean.Moteur;
import fr.ocr.bean.Option;
import fr.ocr.bean.TypeMoteur;
import fr.ocr.bean.Vehicule;

public abstract class AbstractDAOFactory {
	
	public static final int DAO_FACTORY = 0;

	// Ces méthodes retournent l'objet correspondant en intéragissant avec la BDD.
	public abstract DAO<Marque> getMarqueDAO();

	public abstract DAO<Moteur> getMoteurDAO();

	public abstract DAO<Option> getOptionDAO();

	public abstract DAO<TypeMoteur> getTypeMoteurDAO();
	
	public abstract DAO<Vehicule> getVehiculeDAO();

	// Méthode permettant de récupérer les Factory
	public static AbstractDAOFactory getFactory(int type) {
		switch(type) {
		case DAO_FACTORY:
			return new DAOFactory();
		default:
			return null;
		}
	}
}