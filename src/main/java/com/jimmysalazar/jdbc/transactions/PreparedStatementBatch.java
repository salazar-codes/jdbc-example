package com.jimmysalazar.jdbc.transactions;

import com.github.javafaker.Faker;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class PreparedStatementBatch {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
             PreparedStatement ps = connection.prepareStatement("insert into person (name,lastname,nickname) values (?,?,?)");){

            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            Faker faker = new Faker();

            try {
                for(int i= 0;i< 100;i++) {
                    ps.setString(1,faker.name().firstName());
                    ps.setString(2,faker.name().lastName());
                    ps.setString(3,faker.dragonBall().character());
                    // SI algún registro está mal, sólo se omitirá ese
                    // Se utiliza para hacer cargas tipo vulk
                    ps.addBatch();
                }
                int[] executeBatch = ps.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM person");
            System.out.println("Select exec");
            while (rs.next()){
                System.out.printf("\nId[%d] \tName = [%s] \tLastname [%s] Nickname [%s]",rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }
    }
}
