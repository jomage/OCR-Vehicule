package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import fr.ocr.ihm.listener.NewVehiculeListener;
import fr.ocr.ihm.listener.ViewMenuListener2;
import fr.ocr.sql.DatabaseTable;

public class Garage extends JFrame {

	private static final long serialVersionUID = -2330900376849645048L;
	
	// Indique quelle table est actuellement affichée.
	private DatabaseTable currentView;

	//Les différents objets de notre IHM
	private JMenuBar bar = new JMenuBar();
	private JMenu menuVehicule = new JMenu("Vehicule");
	private JMenuItem menuVehiculeAjouter = new JMenuItem("Ajouter");
	private JMenuItem menuVehiculeVoir = new JMenuItem("Voir");

	private JMenu menuMarque = new JMenu("Marque");
	private JMenuItem menuMarqueVoir = new JMenuItem("Voir");

	private JMenu menuMoteur = new JMenu("Moteur");
	private JMenuItem menuMoteurVoir = new JMenuItem("Voir");

	private JMenu menuOption = new JMenu("Option");
	private JMenuItem menuOptionVoir = new JMenuItem("Voir");

	private JMenu menuTypemoteur = new JMenu("Type de moteur");
	private JMenuItem menuTypemoteurVoir = new JMenuItem("Voir");

	public Garage() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JTable");
		this.setSize(800, 400);
		// Affiche la table Vehicule par défaut
		setCurrentView(DatabaseTable.VEHICULE);
		
		// Données de notre tableau
		refreshCurrentView();

		this.setLocationRelativeTo(null);
		initMenu();
	}
	
	/**
	 * Méthode qui initialise les points de menu.
	 */
	private void initMenu() {
		menuVehicule.add(menuVehiculeVoir);
		menuVehicule.add(menuVehiculeAjouter);
		menuVehiculeAjouter.addActionListener(new NewVehiculeListener(this));
		menuVehiculeAjouter.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		menuVehiculeVoir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		menuVehiculeVoir.addActionListener(new ViewMenuListener2(this,
				DatabaseTable.VEHICULE));
		menuVehicule.setMnemonic('v');

		menuMarque.add(menuMarqueVoir);
		menuMarque.setMnemonic('a');
		menuMarqueVoir.addActionListener(new ViewMenuListener2(this,
				DatabaseTable.MARQUE));

		menuMoteur.add(menuMoteurVoir);
		menuMoteur.setMnemonic('m');
		menuMoteurVoir.addActionListener(new ViewMenuListener2(this,
				DatabaseTable.MOTEUR));

		menuOption.add(menuOptionVoir);
		menuOption.setMnemonic('o');
		menuOptionVoir.addActionListener(new ViewMenuListener2(this,
				DatabaseTable.OPTION));

		menuTypemoteur.add(menuTypemoteurVoir);
		menuTypemoteur.setMnemonic('t');
		menuTypemoteurVoir.addActionListener(new ViewMenuListener2(this,
				DatabaseTable.TYPEMOTEUR));

		bar.add(menuVehicule);
		bar.add(menuMarque);
		bar.add(menuMoteur);
		bar.add(menuOption);
		bar.add(menuTypemoteur);

		this.setJMenuBar(bar);
	}

	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Garage g = new Garage();
				g.setVisible(true);
			}
		});
	}
	
	/**
	 * Méthode qui rafraichit l'affichage de la table en cours de visualisation. Lance un thread dans l'EDT.
	 */
	public void refreshCurrentView() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				// On enlève tout
				Garage.this.getContentPane().removeAll();
				// Selon ce qu'il y a a afficher, on add la table.
				switch (getCurrentView()) {
				case VEHICULE:
					Garage.this.getContentPane().add(
							new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.VEHICULE)),
							BorderLayout.CENTER);
					break;
				case MARQUE:
					Garage.this.getContentPane().add(
							new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.MARQUE)),
							BorderLayout.CENTER);
					break;
				case MOTEUR:
					Garage.this.getContentPane().add(
							new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.MOTEUR)),
							BorderLayout.CENTER);
					break;
				case OPTION:
					Garage.this.getContentPane().add(
							new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.OPTION)),
							BorderLayout.CENTER);
					break;
				case TYPEMOTEUR:
					Garage.this.getContentPane().add(
							new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.TYPEMOTEUR)),
							BorderLayout.CENTER);
					break;
				default:
					break;
				}

				Garage.this.getContentPane().revalidate();
			}
		});

		if (SwingUtilities.isEventDispatchThread()) {
			t.start();
		} else {
			SwingUtilities.invokeLater(t);
		}
	}

	public DatabaseTable getCurrentView() {
		return currentView;
	}

	public void setCurrentView(DatabaseTable currentView) {
		this.currentView = currentView;
		refreshCurrentView();
	}
	
	public void refreshCurrentView2() {
		// On enlève tout
		Garage.this.getContentPane().removeAll();
		// Selon ce qu'il y a a afficher, on add la table.
		switch (getCurrentView()) {
		case VEHICULE:
			Garage.this.getContentPane().add(
					new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.VEHICULE)),
					BorderLayout.CENTER);
			break;
		case MARQUE:
			Garage.this.getContentPane().add(
					new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.MARQUE)),
					BorderLayout.CENTER);
			break;
		case MOTEUR:
			Garage.this.getContentPane().add(
					new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.MOTEUR)),
					BorderLayout.CENTER);
			break;
		case OPTION:
			Garage.this.getContentPane().add(
					new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.OPTION)),
					BorderLayout.CENTER);
			break;
		case TYPEMOTEUR:
			Garage.this.getContentPane().add(
					new JScrollPane(TableFactory.getTable(Garage.this, DatabaseTable.TYPEMOTEUR)),
					BorderLayout.CENTER);
			break;
		default:
			break;
		}

		Garage.this.getContentPane().revalidate();
	}
}
