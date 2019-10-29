package fr.ocr.dao.implement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.bean.Option;
import fr.ocr.bean.Vehicule;
import fr.ocr.dao.AbstractDAOFactory;
import fr.ocr.dao.DAO;

/**
 * Data Access Object pour la table Vehicule.
 * @author Jomage
 *
 */
public class VehiculeDAO extends DAO<Vehicule> {

	public VehiculeDAO(Connection conn) {
		super(conn);
	}

	/**
	 * Cette méthode ajoute le véhicule v passé en paramètre, dans la DB.
	 * Une nouvelle id sera choisie avec l'incrément de la table Vehicule.
	 * @param v l'objet Véhicule qui sera inséré dans la DB, avec ses options.
	 */
	@Override
	public boolean create(Vehicule v) {

		try {
			// On récupère le prochain ID
			ResultSet nextID = connect.prepareStatement("CALL NEXT VALUE FOR seq_vehicule_id").executeQuery();
			if (nextID.next()) {
				int id = nextID.getInt(1);

				// Insertion de la nouvelle entrée dans la BDD
				Statement state = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				state.executeQuery("INSERT INTO Vehicule (MARQUE, MOTEUR, PRIX, NOM, ID)" +
						" VALUES (" + v.getMarque().getId() +
						", " + v.getMoteur().getId() +
						", "+ v.getPrix() +
						", '"+ v.getNom() +
						"', " + id + ")");
				List<Option> optionList = v.getOptions();
				addOptionList(optionList, id);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Méthode qui met à jour les options 'options' de la table vehicule_option pour une id_vehicule donnée.
	 * Cette méthode accède à la DB et met à jour la table vehicule_option avec toutes les options présentes dans la liste.
	 * Attention, cette méthode DELETE toute option non présente dans la liste passée en paramètre !
	 * Si la liste est NULL ou vide, se contente de DELETE toute option éventuellement présente pour l'id_vehicule donnée.
	 * @param optionList la List<Option> à ajouter à la DB.
	 * @param idVehicule l'id du véhicule "propriétaire" de ces options.
	 * @throws SQLException 
	 */
	private void updateOptionList(List<Option> optionList, int idVehicule) throws SQLException {

		// DELETE les options présentes
		delOptionList(idVehicule);
		
		// on INSERT les nouvelles options
		addOptionList(optionList, idVehicule);
	}

	/**
	 * Méthode qui efface dans la DB toutes les options ayant un véhicule idVehicule.
	 * @param idVehicule l'id du véhicule qui possède les options à effacer.
	 * @throws SQLException
	 */
	private void delOptionList(int idVehicule) throws SQLException {
		Statement state = this.connect.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		
		state.executeQuery("DELETE FROM vehicule_option WHERE id_vehicule = " + idVehicule);
	}

	/**
	 * Méthode qui ajoute la liste d'options optionList avec l'id_vehicule idVehicule.
	 * Si la liste est null ou vide, ne fait rien.
	 * @param optionList
	 * @param idVehicule
	 * @throws SQLException 
	 */
	private void addOptionList(List<Option> optionList, int idVehicule) throws SQLException {
		
		if (optionList != null && !optionList.isEmpty()) {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		
			String queryOptions = "INSERT INTO vehicule_option VALUES ";
			for (Option option : optionList) {
				queryOptions += "(" + idVehicule + ", "+ option.getId() + "),";
			}
			// on remplace la dernière virgule par un point-virgule
			queryOptions = queryOptions.substring(0, queryOptions.length()-1) + ";";
			state.executeQuery(queryOptions);
		}
	}

	@Override
	public boolean delete(Vehicule v) {
		try {
			// On efface les options de la table vehicule_option en premier.
			delOptionList(v.getId());
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			
			state.executeQuery("DELETE FROM vehicule WHERE id = " + v.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Vehicule v) {
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// update véhicule
			state.executeQuery("UPDATE vehicule SET"
					+ " marque = '" + v.getMarque().getId() + "',"
					+ " moteur = '" + v.getMoteur().getId() + "',"
					+ " prix = '" + v.getPrix() + "',"
					+ " nom = '" + v.getNom() + "'"
					+ " WHERE id = " + v.getId());
			// udpate options
			// Si la liste des options n'est pas vide, on delete les existantes puis on ajoute les nouvelles.
			updateOptionList(v.getOptions(), v.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Vehicule find(int id) {
		Vehicule v = new Vehicule();
		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet result = state.executeQuery("SELECT * FROM Vehicule" + 
					" LEFT JOIN vehicule_option ON id_vehicule = id" +
					" WHERE id = " + id);

			if (result.first()) {
				v.setId(id);
				v.setNom(result.getString("nom").trim()); //trim() parce qu'il y a des caractères invisibles après le nom dans la BDD
				v.setPrix(result.getDouble("prix"));
				// On va chercher le reste des infos dans les tables correspondantes.
				AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
				v.setMarque(adf.getMarqueDAO().find(result.getInt("marque")));
				v.setMoteur(adf.getMoteurDAO().find(result.getInt("moteur")));
				// List Options :
				do {
					// Attention : si le véhicule n'a pas d'option, alors la méthode getInt renvoie quand même 0,
					// ce qui correspond à l'option Toit Ouvrant. On teste avant si le résultat a renvoyé null.
					int idOption = result.getInt("id_option");
					if (!result.wasNull()) {
						v.addOption(adf.getOptionDAO().find(idOption));
					}
				} while (result.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return v;
	}

	/**
	 * Méthode qui retourne tous les véhicules présents dans la BDD, sous forme de liste.
	 * @return
	 */
	public List<Object> getAll() {
		List<Object> list = new ArrayList<Object>();

		try {
			Statement state = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = state.executeQuery("SELECT id, marque, nom, moteur, prix, id_option FROM vehicule" + 
					" LEFT JOIN vehicule_option ON vehicule_option.id_vehicule = vehicule.id");
			
			while (resultSet.next()) {
				Vehicule v = new Vehicule();
				v.setId(resultSet.getInt("id"));
				v.setNom(resultSet.getString("nom"));
				v.setPrix(resultSet.getDouble("prix"));
				// On va chercher le reste des infos dans les tables correspondantes.
				AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
				v.setMarque(adf.getMarqueDAO().find(resultSet.getInt("marque")));
				v.setMoteur(adf.getMoteurDAO().find(resultSet.getInt("moteur")));
				// List Options :
				// On regarde si on a un résultat non NULL (SQLNULL).
				int idOption = resultSet.getInt("id_option");
				if (!resultSet.wasNull()) {
					// On a au moins une option. On ajoute celle qu'on a trouvé, puis on passe à la ligne suivante.
					// (chaque option est sur une ligne différente dans le resultSet.)
					v.addOption(adf.getOptionDAO().find(idOption));
					// SI la ligne suivante est pour un véhicule différent (on a trouvé toutes les options du véhicule courant),
					// ALORS on reviens une ligne en arrière (sinon, on risque desauter des infos pour le prochain véhicule.)
					while (resultSet.next() && resultSet.getInt("id") == v.getId()) {
						v.addOption(adf.getOptionDAO().find(resultSet.getInt("id_option")));
					}
					resultSet.previous();
				}
				list.add(v);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}
}
