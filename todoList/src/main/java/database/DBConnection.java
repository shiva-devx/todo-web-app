package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {

    private static final Properties props = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = DBConnection.class.getClassLoader()
                .getResourceAsStream("dbConfig.properties")) {

            if (inputStream == null) {
                throw new RuntimeException("dbConfig.properties not found in classpath");
            }

            props.load(inputStream);

            // Verify properties were loaded correctly
            System.out.println("Loaded properties: " + props);

        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file", e);
        }
    }
    public static Connection getConnection(){
        Connection connection = null;
        try {

            try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("dbConfig.properties")) {
                if (inputStream == null) {
                    throw new IOException("properties file not found in the classPath");
                }

//                props.load(inputStream);
            }

            String dbname = props.getProperty("DBNAME");
            String url = "jdbc:mysql://localhost:3306/" + dbname;
            String username = props.getProperty("USERNAME");
            String password = props.getProperty("PASSWORD");

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException  e) {
            System.err.println("Failed to load driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
        }

        return connection;
    }


    public static String getProps(String propName){
        String value = props.getProperty(propName);
        if (value == null) {
            System.out.println("Property '" + propName + "' not found. Available properties: " + props.keySet());
            throw new RuntimeException("Property '" + propName + "' not found. Available properties: " + props.keySet());
        }
        return value.trim(); // Trim in case there are spaces

    }


}
