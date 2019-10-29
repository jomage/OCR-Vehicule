package fr.ocr.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.bean.Option;
import fr.ocr.dao.DAO;
/**
 * Data Access Object pour la table Option.
 * @author Jomage
 *
 */
public class OptionDAO extends DAO<Option> {

	public OptionDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Option obj) {
		// TODO Auto-generated method stub
		System.out.println("Create Option : NYI");
		return false;
	}

	@Override
	public boolean delete(Option obj) {
		// TODO Auto-generated method stub
		System.out.println("Delete Option : NYI");
		return false;
	}

	@Override
	public boolean update(Option obj) {
		// TODO Auto-generated method stub
		System.out.println("Update Option : NYI");
		return false;
	}

	@Override
	public Option find(int id) {
		Option o = new Option();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = state.executeQuery("SELECT * FROM option WHERE id = " + id);
			
			if (resultSet.first()) {
				o.setId(id);
				o.setDescription(resultSet.getString("description"));
				o.setPrix(resultSet.getDouble("prix"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return o;
	}
	
	@Override
	public List<Object> getAll() {
		List<Object> list = new ArrayList<Object>();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = state.executeQuery("SELECT * FROM option");
			
			while (resultSet.next()) {
				list.add(new Option(resultSet.getInt("id"),
									resultSet.getString("description"),
									resultSet.getDouble("prix")));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
