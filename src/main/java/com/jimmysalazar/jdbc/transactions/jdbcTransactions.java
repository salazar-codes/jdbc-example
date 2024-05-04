package com.jimmysalazar.jdbc.transactions;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class jdbcTransactions {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
             PreparedStatement ps = connection.prepareStatement("insert into person (name,lastname,nickname) values (?,?,?)");){

            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            connection.setAutoCommit(false);
            Savepoint savePoint = null;

            try {

                ps.setString(1,"Jimmy");
                ps.setString(2,"Salz");
                ps.setString(3,"jmsa");
                ps.executeUpdate();

                savePoint = connection.setSavepoint("customSavePoint");

                ps.setString(1,"vvvv");
                ps.setString(2,"ccc");
                ps.setString(3,"ddd");
                ps.executeUpdate();

                // Ya no se considerará el savePoint
                // generará una SQL exception si se intenta ejecutar
                connection.releaseSavepoint(savePoint);

                ps.setString(1,null);
                ps.setString(2,"ff");
                ps.setString(3,"ccc");
                ps.executeUpdate();

                connection.commit();
                System.out.println("Records persisted");
            }catch (SQLException e){

                if(savePoint == null){
                    connection.rollback();
                    System.out.println("Rolling back");
                }else{
                    connection.rollback(savePoint);
                    System.out.println("Rolling back savePoint");
                }

                //e.printStackTrace();
            }finally {
                connection.setAutoCommit(true); // Esta es una buena práctica, comportamiento por defecto
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
