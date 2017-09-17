package com.andrievskaja.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Initializes database if it is not exist
 */
public class Bootstrap {

    /**
     * Database name
     */
    private String dbName;

    /**
     * Connection manager
     */
    private Connection connection;

    public Bootstrap(String dbName, Connection connection) {
        this.dbName = dbName;
        this.connection = connection;
    }

    /**
     * Initializes database.
     *
     * @return true if database was initialised from scratch, false otherwise /
     * создает базу если ее нет
     */
    public boolean init() {
        boolean result = false;
        try {
    
            Statement statement = connection.createStatement();

           
            statement.executeUpdate("USE " + dbName);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (\n"
                    + "    id smallint auto_increment,\n"
                    + "    email VARCHAR(255) NOT NULL,\n"
                    + "    lastLogin    VARCHAR(255),\n"
                    + "    login VARCHAR(255)NOT NULL,\n"
                    + "    name   varchar(45),\n"
                    + "	password VARCHAR(255)NOT NULL,\n"
                    + "    PRIMARY KEY (id)) ENGINE InnoDB");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS record (\n"
                    + "    id SMALLINT NOT NULL auto_increment,\n"
                    + "    text VARCHAR(255) NOT NULL,\n"
                    + "	date VARCHAR(255),\n"
                    + "    user_id SMALLINT NOT NULL,\n"
                    + "    PRIMARY KEY (id),\n"
                    + "    FOREIGN KEY (user_id) REFERENCES user(id)) "
                    + "ENGINE InnoDB");

            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
