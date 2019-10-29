package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;

import fr.ocr.ihm.Garage;
import fr.ocr.ihm.VehiculeDetail;

/**
 * Comportement du bouton Détails / Voir Véhicule, qui s'affiche sur la JTable Vehicule.
 * @author Jomage
 *
 */
public class TableDetailButtonListener extends TableButtonListener {
	
	Garage garage;
	
	public TableDetailButtonListener(Garage g) {
		garage = g;
	}

	public void actionPerformed(ActionEvent e) {
		// ATTENTION : la ligne ci-dessous throws une Class Cast Exception si l'arrangement
		// des colonnes est modifié après la création du bouton !
		id = (int) table.getValueAt(row, table.getColumn("id").getModelIndex());
		new VehiculeDetail(garage, id);
	}
}
