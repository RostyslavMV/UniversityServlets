package com.rmv.university;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
@Setter
public class DBManager {

    private Connection connection;

    public DBManager() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PropertiesConfiguration config = new PropertiesConfiguration();

        try {
            config.load("db/liquibase.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        String url = config.getString("url");
        Properties props = new Properties();
        props.setProperty("user",config.getString("username"));
        props.setProperty("password",config.getString("password"));

        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
