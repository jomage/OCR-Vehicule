package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ocr.ihm.Garage;
import fr.ocr.sql.DatabaseTable;

/**
 * Version modifiée de ViewMenuListener. Permet d'appeler la méthode qui affiche la table dans Garage.
 * Cette version requiert un Garage en paramètre.
 * 
 * @author Jomage
 *
 */
public class ViewMenuListener2 implements ActionListener {

	DatabaseTable table;
	Garage garage;
	
	public ViewMenuListener2(Garage g, DatabaseTable t) {
		table = t;
		garage = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		garage.setCurrentView(table);
	}

}
