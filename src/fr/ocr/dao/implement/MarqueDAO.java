package fr.ocr.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.bean.Marque;
import fr.ocr.dao.DAO;

/**
 * Data Access Object pour la table Marque.
 * @author Jomage
 *
 */
public class MarqueDAO extends DAO<Marque> {

	public MarqueDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Marque obj) {
		System.out.println("Create Marque : NYI");
		return false;
	}

	@Override
	public boolean delete(Marque obj) {
		System.out.println("Delete Marque : NYI");
		return false;
	}

	@Override
	public boolean update(Marque obj) {
		System.out.println("Update Marque : NYI");
		return false;
	}

	/**
	 * Retourne un objet Marque correspondant à l'entrée possédant l'id id dans la BDD.
	 * Renvoie une marque "vide" (id = 0, nom = "") si non trouvée.
	 * @param id l'id de la marque à aller chercher dans la BDD.
	 * @return un objet Marque ayant pour id id.
	 */
	@Override
	public Marque find(int id) {
		Marque m = new Marque();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = state.executeQuery("SELECT * FROM marque WHERE id = " + id);
			// Si le resultat de la requête n'est pas vide
			if (resultSet.first()) {
				m = new Marque(id, resultSet.getString("nom"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public List<Object> getAll(){
		List<Object> list = new ArrayList<Object>();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			ResultSet resultSet = state.executeQuery("SELECT * FROM marque");
			
			while (resultSet.next()) {
				list.add(new Marque(resultSet.getInt("id"), resultSet.getString("nom")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
