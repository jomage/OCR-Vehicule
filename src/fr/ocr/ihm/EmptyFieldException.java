package fr.ocr.ihm;

import javax.swing.JOptionPane;

public class EmptyFieldException extends Exception {

	private static final long serialVersionUID = -7542861382808347049L;

	public EmptyFieldException(String err) {
		super(err);
		JOptionPane.showMessageDialog(null, err, "Champ obligatoire manquant",
				JOptionPane.ERROR_MESSAGE);
	}
}
