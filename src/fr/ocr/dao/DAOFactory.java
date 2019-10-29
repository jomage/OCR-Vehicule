package fr.ocr.dao;

import java.sql.Connection;

import fr.ocr.bean.Marque;
import fr.ocr.bean.Moteur;
import fr.ocr.bean.Option;
import fr.ocr.bean.TypeMoteur;
import fr.ocr.bean.Vehicule;
import fr.ocr.dao.implement.MarqueDAO;
import fr.ocr.dao.implement.MoteurDAO;
import fr.ocr.dao.implement.OptionDAO;
import fr.ocr.dao.implement.TypeMoteurDAO;
import fr.ocr.dao.implement.VehiculeDAO;
import fr.ocr.sql.HsqldbConnection;

public class DAOFactory extends AbstractDAOFactory {
	
	/**
	 * Connexion à la BDD.
	 */
	protected static final Connection conn = HsqldbConnection.getInstance();

	/**
	 * Retourne un objet MarqueDAO
	 */
	@Override
	public DAO<Marque> getMarqueDAO() {
		return new MarqueDAO(conn);
	}

	@Override
	public DAO<Moteur> getMoteurDAO() {
		return new MoteurDAO(conn);
	}

	@Override
	public DAO<Option> getOptionDAO() {
		return new OptionDAO(conn);
	}

	@Override
	public DAO<TypeMoteur> getTypeMoteurDAO() {
		return new TypeMoteurDAO(conn);
	}

	@Override
	public DAO<Vehicule> getVehiculeDAO() {
		return new VehiculeDAO(conn);
	}

}
