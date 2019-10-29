package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import fr.ocr.bean.Marque;
import fr.ocr.bean.Moteur;
import fr.ocr.bean.Option;
import fr.ocr.bean.TypeMoteur;
import fr.ocr.bean.Vehicule;
import fr.ocr.dao.AbstractDAOFactory;

public class VehiculeDetail extends JDialog implements ActionListener {

	private static final long serialVersionUID = -3072232491693981945L;
	
	// On passe le garage pour pouvoir refresh l'affichage en fermant cette fenêtre.
	Garage garage;
	
	// Flag édition ou pas
	private boolean mode_edition = false;
	
	// Éléments de l'IHM
	private JPanel panNom, panMarque, panMoteur, panPrix, panOptions, panBoutons, contenu;
	private JTextField textNom;
	private JFormattedTextField textPrix;
	private JComboBox<Marque> comboMarque;
	private JComboBox<Moteur> comboMoteur;
	private JLabel labelNom, labelMarque, labelMoteur, labelPrix;
	private JCheckBox[] checkBoxesOptions;
	private JButton btnValider, btnAnnuler;
	
	// Beans / Éléments Métiers Java
	Vehicule vehi;
	List<Object> listOptions;
	
	// DAO
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	
	/**
	 * Constructeur par défaut. Pas d'argument = mode création.
	 * La nouvelle entrée aura une id générée automatiquement par la BDD.
	 */
	public VehiculeDetail(Garage g) {
		this.setModal(true);
		this.garage = g;
		this.setTitle("Ajouter un nouveau véhicule");
		this.setSize(550, 415);
		this.setLocationRelativeTo(this.getParent());
		// On crée le nouveau véhicule.
		vehi = new Vehicule();
		initElements();
		fillData();
		this.setVisible(true);
	}

	/**
	 * Constructeur avec argument. Mode édition.
	 * Ouvre une fenêtre avec les informations de l'entrée possédant l'id entrée en paramètre.
	 * @param id l'id du véhicule à éditer.
	 */
	public VehiculeDetail(Garage g, int id) {
		this.setModal(true);
		this.garage = g;
		mode_edition = true;
		this.setSize(550, 415);
		this.setLocationRelativeTo(this.getParent());
		vehi = adf.getVehiculeDAO().find(id);
		initElements();
		if (vehi == null) {
			JOptionPane.showMessageDialog(this, "Erreur : Véhicule non trouvé !",
					"Erreur à l'ouverture du panneau Détails", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		} else {
			fillData();
		}
		this.setVisible(true);
	}

	/**
	 * Initialise et place les éléments de l'interface.
	 */
	private void initElements() {
		// Bloc Nom
		panNom = new JPanel();
		panNom.setPreferredSize(new Dimension(220, 80));
		labelNom = new JLabel("Nom");
		textNom = new JTextField();
		textNom.setPreferredSize(new Dimension(110, 28));
		panNom.add(labelNom);
		panNom.add(textNom);
		panNom.setBorder(BorderFactory.createTitledBorder("Nom du véhicule"));

		// Bloc Marque
		panMarque = new JPanel();
		panMarque.setPreferredSize(new Dimension(220, 80));
		labelMarque = new JLabel("Marque");
		comboMarque = new JComboBox<Marque>();
		panMarque.add(labelMarque);
		panMarque.add(comboMarque);
		panMarque.setBorder(BorderFactory.createTitledBorder("Marque du véhicule"));

		// Bloc Type Moteur
		panMoteur = new JPanel();
		panMoteur.setPreferredSize(new Dimension(440, 80));
		labelMoteur = new JLabel("Moteur");
		comboMoteur = new JComboBox<Moteur>();
		panMoteur.add(labelMoteur);
		panMoteur.add(comboMoteur);
		panMoteur.setBorder(BorderFactory.createTitledBorder("Type de moteur du véhicule"));

		// Bloc Prix
		panPrix = new JPanel();
		panPrix.setPreferredSize(new Dimension(220, 80));
		labelPrix = new JLabel("Prix");
		NumberFormatter formatter = new NumberFormatter(new DecimalFormat("#,##0.00"));
		textPrix = new JFormattedTextField(formatter);
		textPrix.setValue(0.0D);
		textPrix.setColumns(10);
		textPrix.setPreferredSize(new Dimension(110, 28));
		
		panPrix.add(labelPrix);
		panPrix.add(textPrix);
		panPrix.setBorder(BorderFactory.createTitledBorder("Prix du véhicule"));

		// Bloc Options
		panOptions = new JPanel();
		// On affichera les options dans la méthode fillData.
		panOptions.setPreferredSize(new Dimension(440, 80));
		panOptions.setBorder(BorderFactory.createTitledBorder("Options disponibles"));

		// Bloc Boutons
		panBoutons = new JPanel();
		btnValider = new JButton("Valider");
		btnValider.addActionListener(this);
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this);
		panBoutons.add(btnValider);
		panBoutons.add(btnAnnuler);

		// Général
		contenu = new JPanel();
		contenu.add(panNom);
		contenu.add(panMarque);
		contenu.add(panMoteur);
		contenu.add(panPrix);
		contenu.add(panOptions);
		this.getContentPane().add(panBoutons, BorderLayout.SOUTH);
		this.getContentPane().add(contenu, BorderLayout.CENTER);
	}

	/**
	 * Remplit les combo box marque, moteur et ajoute les options à l'interface en allant les chercher dans la BDD.
	 */
	private void fillData() {
		// Remplissage combobox Marque :
		if (!mode_edition)
			comboMarque.addItem(new Marque(-1, "Sélectionnez une marque"));
		for (Object o : adf.getMarqueDAO().getAll()) {
			comboMarque.addItem((Marque) o);
		}

		// Moteur
		if (!mode_edition)
			comboMoteur.addItem(new Moteur(-1, new TypeMoteur(), "Sélectionnez un Moteur", -1d));
		for (Object m : adf.getMoteurDAO().getAll()) {
			comboMoteur.addItem((Moteur) m);
		}

		// Options
		// Création des boutons correspondant à chaque option possible.
		listOptions = adf.getOptionDAO().getAll();
		checkBoxesOptions = new JCheckBox[listOptions.size()];
		for (int i = 0 ; i < listOptions.size() ; i++) {
			checkBoxesOptions[i] = new JCheckBox(listOptions.get(i).toString());
			panOptions.add(checkBoxesOptions[i]);
		}

		// REMPLISSAGE DES DONNÉES EN CAS DE MODE D'ÉDITION
		if (mode_edition) {
			// NOM
			textNom.setText(vehi.getNom());

			// MARQUE
			for (int i = 0; i<comboMarque.getItemCount();i++) {
				if (comboMarque.getItemAt(i).getId() == vehi.getMarque().getId()) {
					comboMarque.setSelectedIndex(i);
				}
			}
			//comboMarque.setSelectedItem(vehi.getMarque()); // ne fonctionne pas.

			// MOTEUR
			for (int i = 0; i<comboMoteur.getItemCount();i++) {
				if (comboMoteur.getItemAt(i).getId() == vehi.getMoteur().getId()) {
					comboMoteur.setSelectedIndex(i);
				}
			}
			
			// PRIX
			textPrix.setText(Double.toString(vehi.getPrix()));
			try {
				textPrix.commitEdit();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			// OPTIONS
			// On coche les options correspondantes.
			for (Object o : vehi.getOptions()) {
				// pour chaque option dans la liste des options de vehi
				int i = 0;
				while (o.toString() != checkBoxesOptions[i].getText() && i < checkBoxesOptions.length) {
					i++;
				}
				if (i < checkBoxesOptions.length)
					checkBoxesOptions[i].setSelected(true);
			}
		}
	}
	
	/**
	 * Complète vehi avec les données des fields et inscrit le nouveau vehi dans la BDD en appelant le dao.
	 */
	private void validerVehicule() {
		if (isDataValid()) {
			// On rentre les données dans l'objet Vehicule vehi
			vehi.setNom(textNom.getText());
			vehi.setMarque((Marque) comboMarque.getSelectedItem());
			vehi.setMoteur((Moteur) comboMoteur.getSelectedItem());
			vehi.setPrix(((Number) textPrix.getValue()).doubleValue());
			
			// System.out.println("225 > NOMBRE OBTENU : " + ((Number) textPrix.getValue()).doubleValue() + " | vehi.getPrix() : " + vehi.getPrix());
			
			ArrayList<Option> newListOptions = new ArrayList<Option>();
			for (int i = 0 ; i < checkBoxesOptions.length ; i++) {
				if (checkBoxesOptions[i].isSelected())
					newListOptions.add((Option) listOptions.get(i));
			}
			vehi.setListOptions(newListOptions);
			// si mode_edition : update avec le dao
			if (mode_edition) {
				if (!adf.getVehiculeDAO().update(vehi))
					JOptionPane.showMessageDialog(this, "Erreur lors de l'édition du véhicule. Le véhicule n'a pas été édité.",
							"Erreur : Édition Véhicule", JOptionPane.ERROR_MESSAGE);
			} else {
				// si pas mode_edition : créer avec le dao
				if (!adf.getVehiculeDAO().create(vehi))
					JOptionPane.showMessageDialog(this, "Erreur lors de la création du véhicule. Le véhicule n'a pas été créé.",
							"Erreur : Création Véhicule", JOptionPane.ERROR_MESSAGE);
			}
			this.dispose();
		} else {
			// Données invalides, on ne fait rien.
		}
	}

	/**
	 * Méthode qui vérifie les différents champs et notifie l'utilisateur qu'un champ est invalide.
	 * 
	 */
	private boolean isDataValid() {
		boolean isValid = true;
		String message = "Une ou plusieurs entrées sont invalides. Veuillez vérifier le ou les champs suivants :\n";
		if (textNom.getText().trim().isEmpty()) {
			message += "\tchamp Nom\n";
			isValid = false;
		}
		if (!mode_edition && comboMarque.getSelectedIndex() == 0) {
			message += "\tchamp Marque\n";
			isValid = false;
		}
		if (!mode_edition && comboMoteur.getSelectedIndex() == 0) {
			message += "\tchamp Moteur\n";
			isValid = false;
		}
		
		if (((Number) textPrix.getValue()).doubleValue() < 0) {
			message += "\tchamp Prix\n";
			isValid = false;
		}	
		
//		try {
//			System.out.println("VehiculeDetail.java@264 > " + textPrix.getValue());
//			Double.valueOf(textPrix.getText());
//		} catch (NumberFormatException e) {
//			message += "\tchamp Prix\n";
//			isValid = false;
//		}
		
		if (!isValid)
			JOptionPane.showMessageDialog(this, message, "Erreur de saisie", JOptionPane.INFORMATION_MESSAGE);
		
		return isValid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Bouton Cancel :
		if (e.getActionCommand() == "Annuler") {
			this.dispose();
		}
		// bouton Valider :
		if (e.getActionCommand() == "Valider") {
			validerVehicule();
		}
		// Dans les 2 cas, on refresh les véhicules en sortant de cette fenêtre.
		garage.refreshCurrentView();
	}
}
