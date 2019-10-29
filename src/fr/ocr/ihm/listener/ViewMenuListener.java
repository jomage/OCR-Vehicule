package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import fr.ocr.sql.DatabaseTable;


/**
 * Classe permettant de gérer l'affichage des différentes tables de la BDD en
 * fonction du menu cliqué.
 * 
 * @author cysboy
 */
public class ViewMenuListener implements ActionListener {
	DatabaseTable table;
	JFrame frame;

	public ViewMenuListener(JFrame f, DatabaseTable tab) {
		table = tab;
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
//		frame.getContentPane().removeAll();
//		frame.getContentPane().add(
//				new JScrollPane(TableFactory.getTable(table)));
//		frame.getContentPane().revalidate();

	}
}
