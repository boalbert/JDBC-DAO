package se.boalbert.integration;

import se.boalbert.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements methods for useage in main-class
 */
public class UserDaoImpl implements UserDao {

    /**
     * Connection url to the database, used in the constructor for connecting
     */
    private static final String CONNECTION_STRING = "jdbc:sqlserver://localhost:1433;databaseName=everyloop;user=boalbert;password=XXXXXX!";

    private Connection conn;

    /**
     * Statements used in the constructor
     */
    private PreparedStatement createStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    /**
     * Constructor for interface.
     * Creates a connection and holds all prepared statements.
     * Throws RunTimeException with specifier which method threw exception.
     */
    public UserDaoImpl() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            createStmt = conn.prepareStatement("INSERT INTO Users (ID, FirstName, LastName, Email) VALUES (?, ?, ?, ?)");
            getAllStmt = conn.prepareStatement("SELECT * FROM Users");
            getByNameStmt = conn.prepareStatement("SELECT * FROM Users where FirstName like ?");
            updateStmt = conn.prepareStatement("UPDATE Users SET Email = ? where ID = ?");
            deleteStmt = conn.prepareStatement("DELETE FROM Users WHERE id = ?");

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO contructor: " + e);
        }
    }

    /**
     * Adds a user to the database.
     * @param id in the database
     * @param firstname of user
     * @param lastname of user
     * @param email for user
     */
    @Override
    public void createUser(String id, String firstname, String lastname, String email) {
        try {
            createStmt.setString(1, id);
            createStmt.setString(2, firstname);
            createStmt.setString(3, lastname);
            createStmt.setString(4, email);

            createStmt.executeUpdate();

            System.out.println(id + " - " + firstname + " has been added to database.");

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO createUser(): " + e);
        }
    }

    /**
     *
     * @return a list of all registred users in the database
     */
    @Override
    public List<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();

        try {
            ResultSet rs = getAllStmt.executeQuery();

            while (rs.next()) {

                result.add(new User(
                                rs.getString("ID"),
                                rs.getString("FirstName"),
                                rs.getString("LastName"),
                                rs.getString("Email")
                        )
                );

            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO getAllUsers(): " + e);
        }
    }

    /**
     * Search for user by name, searching for "A" will return all user starting with "A".
     * @param firstName of user you want to find, dont have to specify whole firstname
     * @return an User object
     */
    @Override
    public List<User> getUserByName(String firstName) {

        ArrayList<User> result = new ArrayList<>();

        try {
            getByNameStmt.setString(1, firstName + "%");

            ResultSet rs = getByNameStmt.executeQuery();

            while (rs.next()) {

                result.add(new User(
                                rs.getString("ID"),
                                rs.getString("FirstName"),
                                rs.getString("LastName"),
                                rs.getString("Email")
                        )
                );

            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO getUserByName(): " + e);
        }


    }

    /**
     * This method lets you update the mail of a user in the database
     * @param id personnummer of the user you want to update
     * @param email to update, overwrites the old email
     * @return boolean true if success, else false
     */
    @Override
    public boolean updateUserEmailById(String id, String email) {

        try {
            updateStmt.setString(1, email);
            updateStmt.setString(2, id);
            updateStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO updateUserById(): " + e);
        }

    }

    /**
     * Permanently deletes a user from the database
     * @param id personnummer of the user you want to delete
     * @return boolean, true if it was a success
     */
    @Override
    public boolean deleteUserById(String id) {

        try {
            deleteStmt.setString(1, id);
            deleteStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO deleteUserById(): " + e);
        }

    }
}
