package se.boalbert;

import se.boalbert.integration.UserDao;
import se.boalbert.integration.UserDaoImpl;
import se.boalbert.model.User;

import java.util.List;

public class Main {

	private static final UserDao userDao = new UserDaoImpl();

	public static void main(String[] args) {

		//  Create User
		userDao.create("880515-6634", "Albert", "Andersson", "bo.andersson@iths.se");

		//  List all users
		List<User> listAll = userDao.getAllUsers();
		System.out.println(listAll);

		//  Get users by name
		List<User> listName = userDao.getUserByName("An");
		System.out.println(listName);

		//  Update user
		userDao.updateUserById("880515-6634", "bo.andersson@iths.se");
		System.out.println(userDao.getUserByName("Bo"));

		//  Delete user
		userDao.deleteUserById("571110-3843");

	}
}


