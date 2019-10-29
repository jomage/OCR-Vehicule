package fr.ocr.ihm;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import fr.ocr.bean.TableGenerique;
import fr.ocr.dao.AbstractDAOFactory;
import fr.ocr.ihm.listener.TableDeleteButtonListener;
import fr.ocr.ihm.listener.TableDetailButtonListener;
import fr.ocr.sql.DatabaseTable;

/**
 * Classe permettant de créer l'objet JTable en fonction de la table que nous
 * souhaitons afficher.
 * 
 * @author Jomage
 *
 */
public class TableFactory {
	
	public static JTable getTable(Garage garage, DatabaseTable table) {
		
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		JTable jTable = new JTable();
		if (table == DatabaseTable.VEHICULE) {
			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel = new DefaultTableModel(listToArray(adf.getVehiculeDAO().getAll()), table.getColumns());
			tableModel.addColumn("ACTION");
			tableModel.addColumn("DETAIL");
			jTable = new JTable(tableModel);
			
		} else if (table == DatabaseTable.VEHICULE_OPTION) {
			// pas vraiment à afficher, c'est une table de jointure...
			// do nothing
		} else if (table == DatabaseTable.MOTEUR) {
			jTable = new JTable(listToArray(adf.getMoteurDAO().getAll()), table.getColumns());
		} else if (table == DatabaseTable.TYPEMOTEUR) {
			jTable = new JTable(listToArray(adf.getTypeMoteurDAO().getAll()), table.getColumns());
		} else if (table == DatabaseTable.MARQUE) {
			jTable = new JTable(listToArray(adf.getMarqueDAO().getAll()), table.getColumns());
		} else if (table == DatabaseTable.OPTION) {
			jTable = new JTable(listToArray(adf.getOptionDAO().getAll()), table.getColumns());
		}
		
		// Ajout des boutons supprimer et éditer/détails sur l'affichage de la table véhicule
		if (table.equals(DatabaseTable.VEHICULE)) {
			jTable.getColumn("ACTION").setCellRenderer(
					new ButtonRenderer("SUPPRIMER"));
			jTable.getColumn("ACTION").setCellEditor(
					new ButtonEditor(new JCheckBox(), "SUPPRIMER",
						new TableDeleteButtonListener(garage)));
			
			jTable.getColumn("DETAIL").setCellRenderer(
					new ButtonRenderer("DETAIL"));
			jTable.getColumn("DETAIL").setCellEditor(
					new ButtonEditor(new JCheckBox(), "DETAIL",
							new TableDetailButtonListener(garage)));
		}
		
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.setRowHeight(30);
		return jTable;
		
	}
	
	private static Object[][] listToArray(List<Object> data) {
		Object[][] table = new Object[data.size()][];
		
		for (int i = 0 ; i < table.length ; i++) {
			table[i] = ((TableGenerique) data.get(i)).toArray();
		}
		
		return table;
	}
}
