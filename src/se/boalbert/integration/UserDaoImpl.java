package se.boalbert.integration;

import se.boalbert.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String CONNECTION_STRING = "jdbc:sqlserver://localhost:1433;databaseName=everyloop;user=boalbert;password=login123!";

    private Connection conn;

    private PreparedStatement createStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

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

    @Override
    public void create(String id, String firstname, String lastname, String email) {
        try {
            createStmt.setString(1, id);
            createStmt.setString(2, firstname);
            createStmt.setString(3, lastname);
            createStmt.setString(4, email);

            createStmt.executeUpdate();

            System.out.println(id + " - " + firstname + " has been added to database.");

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO create: " + e);
        }
    }

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
            throw new RuntimeException("Problem in DAO getAll(): " + e);
        }
    }

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
            throw new RuntimeException("Problem in DAO getAll(): " + e);
        }


    }

    @Override
    public boolean updateUserById(String id, String email) {

        try {
            updateStmt.setString(1, email);
            updateStmt.setString(2, id);
            updateStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO update: " + e);
        }

    }

    @Override
    public boolean deleteUserById(String id) {

        try {
            deleteStmt.setString(1, id);
            deleteStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Problem in DAO delete: " + e);
        }

    }
}
