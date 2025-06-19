package dao;

import database.DBConnection;
import model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    public boolean validate(Login login) throws SQLException {
        boolean status = false;

        System.out.println("validating user in loginDao");
        String userExist = "SELECT * FROM " + DBConnection.getProps("USER_TABLE") + " WHERE username = ? AND password = ? ;";
        try(Connection connection = DBConnection.getConnection()){
            PreparedStatement ps = connection.prepareStatement(userExist);
            ps.setString(1,login.getUsername());
            ps.setString(2,login.getPassword());

            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            status = rs.next(); // true if it has a result set, that mean user exist
        }

        return status;
    }


}
