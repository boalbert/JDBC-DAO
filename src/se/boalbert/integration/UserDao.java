package se.boalbert.integration;

import se.boalbert.model.User;

import java.util.List;

public interface UserDao {


    void createUser(String id, String firstname, String lastname, String email);

    List<User> getAllUsers();

    List<User> getUserByName(String firstName);

    boolean updateUserEmailById(String id, String email);

    boolean deleteUserById(String id);

}
