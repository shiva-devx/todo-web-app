package dao;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDao {
    public int registerUser(User user){
        String insert_new_user = "INSERT INTO " + DBConnection.getProps("USER_TABLE") + " (name,username,password) VALUES (?,?,?);";
//        String insert_new_user = "INSERT INTO user (name,username,password) VALUES (?,?,?);";
        int rowAffected = 0;
        System.out.println("connection with db");
        try(Connection connection = DBConnection.getConnection()){
            System.out.println(connection);
            PreparedStatement ps = connection.prepareStatement(insert_new_user);
            ps.setString(1,user.getName());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());

            rowAffected = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("connection failed " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return rowAffected;
    }

    // todo : implement other feature like change username, password, name, add profile image
}
