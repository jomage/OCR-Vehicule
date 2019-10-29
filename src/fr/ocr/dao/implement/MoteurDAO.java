package fr.ocr.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.bean.Moteur;
import fr.ocr.dao.AbstractDAOFactory;
import fr.ocr.dao.DAO;

/**
 * Data Access Object pour la table Moteur.
 * @author Jomage
 *
 */
public class MoteurDAO extends DAO<Moteur> {

	public MoteurDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Moteur obj) {
		// TODO Auto-generated method stub
		System.out.println("Create Moteur : NYI");
		return false;
	}

	@Override
	public boolean delete(Moteur obj) {
		// TODO Auto-generated method stub
		System.out.println("Delete Moteur : NYI");
		return false;
	}

	@Override
	public boolean update(Moteur obj) {
		// TODO Auto-generated method stub
		System.out.println("Update Moteur : NYI");
		return false;
	}
	
	/**
	 * Retourne un objet Moteur correspondant à l'entrée possédant l'id entrée en paramètre dans la BDD.
	 * Renvoie un moteur "vide" (id = 0) si non trouvé.
	 * @param id l'id du moteur à aller chercher dans la BDD.
	 * @return un objet Moteur ayant pour id id.
	 */
	@Override
	public Moteur find(int id) {
		Moteur m = new Moteur();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = state.executeQuery("SELECT * FROM moteur WHERE id = " + id);
			// Si le resultat de la requête n'est pas vide
			if (resultSet.first()) {
				m.setId(id);
				m.setPrix(resultSet.getDouble("prix"));
				m.setCylindre(resultSet.getString("cylindre"));
				
				AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
				m.setType(adf.getTypeMoteurDAO().find(resultSet.getInt("moteur")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	@Override
	public List<Object> getAll() {
		List<Object> list = new ArrayList<Object>();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = state.executeQuery("SELECT * FROM moteur");

			AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
			
			while (resultSet.next()) {
				Moteur m = new Moteur();
				m.setId(resultSet.getInt("id"));
				m.setPrix(resultSet.getDouble("prix"));
				m.setCylindre(resultSet.getString("cylindre"));
				m.setType(adf.getTypeMoteurDAO().find(resultSet.getInt("moteur")));
				list.add(m);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
