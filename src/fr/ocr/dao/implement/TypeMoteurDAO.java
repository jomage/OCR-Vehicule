package fr.ocr.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.bean.TypeMoteur;
import fr.ocr.dao.DAO;

/**
 * Data Access Object pour la table TypeMoteur.
 * @author Jomage
 *
 */
public class TypeMoteurDAO extends DAO<TypeMoteur> {

	public TypeMoteurDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(TypeMoteur obj) {
		// TODO Auto-generated method stub
		System.out.println("Create TypeMoteur : NYI");
		return false;
	}

	@Override
	public boolean delete(TypeMoteur obj) {
		// TODO Auto-generated method stub
		System.out.println("Delete TypeMoteur : NYI");
		return false;
	}

	@Override
	public boolean update(TypeMoteur obj) {
		// TODO Auto-generated method stub
		System.out.println("Update TypeMoteur : NYI");
		return false;
	}

	@Override
	public TypeMoteur find(int id) {
		TypeMoteur t = new TypeMoteur();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = state.executeQuery("SELECT * FROM type_moteur WHERE id = " + id);
			// Si le resultat de la requête n'est pas vide
			if (resultSet.first()) {
				t = new TypeMoteur(id, resultSet.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public List<Object> getAll() {
		List<Object> list = new ArrayList<Object>();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = state.executeQuery("SELECT * FROM type_moteur");
			
			while (resultSet.next()) {
				list.add(new TypeMoteur(resultSet.getInt("id"), resultSet.getString("description")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
