package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;

import fr.ocr.dao.AbstractDAOFactory;
import fr.ocr.ihm.Garage;

/**
 * Listener pour effacer un véhicule de la base de données.
 * @author Jomage
 *
 */
public class TableDeleteButtonListener extends TableButtonListener {

	Garage garage;
	
	public TableDeleteButtonListener(Garage g) {
		garage = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// ATTENTION : la ligne ci-dessous throws une Class Cast Exception si l'arrangement
		// des colonnes est modifié après la création du bouton !
		id = (int) table.getValueAt(row, table.getColumn("id").getModelIndex());
		// System.out.println("Bouton DELETE | col: " + column + " row: " + row + " id: " + id);
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		adf.getVehiculeDAO().delete(adf.getVehiculeDAO().find(id));
		garage.refreshCurrentView();
	}

}
