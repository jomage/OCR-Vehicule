package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ocr.ihm.Garage;
import fr.ocr.ihm.VehiculeDetail;

/**
 * Listener pour créer un nouveau véhicule.
 * @author Jomage
 *
 */
public class NewVehiculeListener implements ActionListener {
	Garage garage;
	
	public NewVehiculeListener(Garage g) {
		garage = g;
	}

	public void actionPerformed(ActionEvent e) {
		new VehiculeDetail(garage);
	}
}
