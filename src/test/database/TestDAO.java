package test.database;

import fr.ocr.bean.Vehicule;
import fr.ocr.dao.AbstractDAOFactory;

public class TestDAO {
	public static void main(String[] args) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		int vehicleId = 14;
		Vehicule v = adf.getVehiculeDAO().find(vehicleId);
		System.out.println("ID Véhicule " + v.getId() + " :\n" + v.toString());
	}
}
