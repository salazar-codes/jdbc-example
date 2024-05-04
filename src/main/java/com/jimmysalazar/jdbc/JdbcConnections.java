package com.jimmysalazar.jdbc;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

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

            rows = statement.executeUpdate(); // Para sentencias DML
            System.out.println("Rows impacted " + rows);

            statement.close();

            // Permite ejecutar SQL, más no admite parámetros
            Statement statementQuery = connection.createStatement();
            ResultSet rs = statementQuery.executeQuery("SELECT * FROM person");
            while (rs.next()){
                System.out.printf("\nId[%d] \tName = [%s] \tLastname [%s] Nickname [%s]",rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4));
            }

            // Permite ejecutar SQL, sí admite parámetros
            PreparedStatement statementDelete = connection.prepareStatement("delete from person");
            rows = statementDelete.executeUpdate(); // Para sentencias DML
            System.out.println("Rows impacted " + rows);
            statementDelete.close();

            // No es común, pero el CallableStatement sirve para ejecutar procedimientos almacenados

            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
