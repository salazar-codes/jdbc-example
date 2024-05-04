package com.jimmysalazar.jdbc;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConnections {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            System.out.println("Connected");

            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));

            PreparedStatement statement = connection.prepareStatement("insert into person (name,lastname,nickname) values (?,?,?)");
            statement.setString(1,"Jimmy");
            statement.setString(2,"Salz");
            statement.setString(3,"jmsa");
            int rows = statement.executeUpdate();
            System.out.println("Rows impacted " + rows);

            statement.setString(1,"Rbt");
            statement.setString(2,"saz");
            statement.setString(3,"rbsa");

            rows = statement.executeUpdate();
            System.out.println("Rows impacted " + rows);

            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
