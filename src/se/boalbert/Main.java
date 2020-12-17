package se.boalbert;

import se.boalbert.integration.UserDao;
import se.boalbert.integration.UserDaoImpl;
import se.boalbert.model.User;

import java.util.List;

public class Main {

	private static final UserDao userDao = new UserDaoImpl();

	public static void main(String[] args) {


		/**
		 * Create a user and insert it to the database
		 */
		userDao.createUser("880515-6634", "Albert", "Andersson", "bo.andersson@iths.se");

		/**
		 * Populates a list of all users in the database, prints it to the console
		 */
		List<User> listAll = userDao.getAllUsers();
		System.out.println(listAll);

		/**
		 * Return a list of users matching the search String
		 */
		List<User> listName = userDao.getUserByName("An");
		System.out.println(listName);

		/**
		 * Updates email of a user in the database via ID/Personnummer
		 */
		userDao.updateUserEmailById("880515-6634", "bo.andersson@iths.se");
		System.out.println(userDao.getUserByName("Bo"));

		/**
		 * Deletes a user from the database via personnummer
		 */
		userDao.deleteUserById("571110-3843");

	}
}


