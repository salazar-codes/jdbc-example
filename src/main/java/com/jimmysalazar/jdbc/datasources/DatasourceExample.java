package com.jimmysalazar.jdbc.datasources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class DatasourceExample {
    public static void main(String[] args) throws SQLException {
        //JdbcDataSource datasource = new JdbcDataSource();
        //datasource.setURL("jdbc:h2:~/test");
        //Connection connection = datasource.getConnection();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/test");
        config.setUsername("");
        config.setPassword("");
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(2000);

        HikariDataSource connectionPool = new HikariDataSource(config);

        Connection connection = connectionPool.getConnection();
        connection.close();

        connectionPool.close();
    }
}
