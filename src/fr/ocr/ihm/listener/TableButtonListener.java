package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

/**
 * Classe qui est une implémentation d'ActionListener, avec la seule
 * particularité que les fields suivants sont présents :
 * un numéro de colonne, un numéro de ligne et une ID, ainsi qu'une JTable.
 * Son comportement ne réalise aucune action.
 * Pour redéfinir le comportement du listener, créer une nouvelle classe qui
 * extends TableButtonListener et redéfinir la méthode
 * actionPerformed.
 * @author Jomage
 *
 */
public class TableButtonListener implements ActionListener {
	
	protected int column, row, id;
	protected JTable table;

	public void setId(int id) {
		this.id = id;
	}
	
	public void setColumn(int col) {
		this.column = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void actionPerformed(ActionEvent event) {}
}