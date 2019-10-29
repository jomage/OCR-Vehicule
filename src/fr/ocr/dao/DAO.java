package fr.ocr.dao;

import java.sql.Connection;
import java.util.List;

/**
 * Data Access Object générique.
 * @author Jomage
 *
 */
public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO(Connection conn){
		this.connect = conn;
	}

	/**
	 * Méthode de création
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean create(T obj);

	/**
	 * Méthode pour effacer
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations
	 * @param id
	 * @return T
	 */
	public abstract T find(int id);
	
	/**
	 * Méthode qui retourne toutes les entrées de type T sous forme de liste.
	 * @return List<T>
	 */
	public abstract List<Object> getAll();
}