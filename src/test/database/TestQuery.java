package test.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import fr.ocr.sql.HsqldbConnection;

public class TestQuery {

	public static void main(String[] args) {

		Connection connect = HsqldbConnection.getInstance();

		try {
			
			Statement state = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			// ------------------
			/*
			System.out.println("Affichage du véhicule id 24");

			ResultSet result = state.executeQuery("SELECT id, marque, nom, moteur, id_option FROM Vehicule" + 
					" LEFT JOIN vehicule_option ON id_vehicule = id" +
					" WHERE id = 24");

			afficherResultat(result);
			
			// ------------------
			
			System.out.println("Affichage du véhicule id 32");

			ResultSet result2 = state.executeQuery("SELECT id, marque, nom, moteur, id_option FROM vehicule" + 
					" LEFT JOIN vehicule_option ON vehicule_option.id_vehicule = vehicule.id" +
					" WHERE id = 32");

			afficherResultat(result2);
			*/
			// ------------------

			System.out.println("Affichage de tous les véhicules avec toutes leurs options");

//			ResultSet result3 = state.executeQuery("SELECT id, marque, nom, moteur, id_option FROM vehicule" + 
//					" LEFT JOIN vehicule_option ON vehicule_option.id_vehicule = vehicule.id" +
//					"");
			
//			ResultSet result3 = state.executeQuery("SELECT * FROM vehicule" + 
//					" LEFT JOIN vehicule_option ON id_vehicule = id");
			ResultSet result3 = state.executeQuery("SELECT id, marque, nom, moteur, prix, id_option FROM vehicule" + 
					" LEFT JOIN vehicule_option ON vehicule_option.id_vehicule = vehicule.id");
			
			afficherResultat(result3);
			
			// ------------------
			
			connect.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static void afficherResultat(ResultSet r) throws SQLException {
		if (r != null) {
			String line1 = "╔";
			String line2 = "║";
			String line3 = "╠";
			String lineDataSeparator = "╟";
			String lineData = "║";
			String lineLast = "╚"; //╝
			
			// ---------------------------
			// Getting and displaying rows
			
			ResultSetMetaData rMeta = r.getMetaData();
			for (int i = 1; i <= rMeta.getColumnCount(); i++) {
				line1 += "═══════════════════╤";
				line3 += "═══════════════════╪";
				lineDataSeparator += "───────────────────┼";
				lineLast += "═══════════════════╧";
			}
			System.out.println(line1.substring(0, line1.length()-1) + "╗");
			
			for (int i = 1; i <= rMeta.getColumnCount(); i++) {
				line2 += StringUtils.center(rMeta.getColumnName(i).toUpperCase(), 19) + "│";
			}
			System.out.println(line2.substring(0, line2.length()-1) + "║");
			System.out.println(line3.substring(0, line3.length()-1) + "╣");
			
			// ------------------
			// Displaying results
			while (r.next()) {
				lineData = "║";
				for (int i = 1; i <= rMeta.getColumnCount(); i++) {
					if (r.getObject(i) != null) {
						lineData += StringUtils.center(r.getObject(i).toString().trim(), 19) + "│";
					} else {
						lineData += StringUtils.center("NULL", 19) + "│";
					}
				}
				
				System.out.println(lineData.substring(0, lineData.length()-1) + "║");
				if (!r.isLast())
					System.out.println(lineDataSeparator.substring(0, lineDataSeparator.length()-1) + "╢");
			}
			
			System.out.println(lineLast.substring(0, lineLast.length()-1) + "╝");
			
		} else {
			System.out.println("result from query is null.");
		}
	}

}
