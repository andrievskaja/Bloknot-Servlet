
package com.andrievskaja.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Людмила
 */
public class ConnectionManager {
      /**
     * Название СУБД
     * название необходимо указывать согласно документации к СУБД для формирования jdbc url
     * например - "mysql" 
     */
    private String databaseManagementSystemName;

    /**
     * Имя базы данных
     */
    private String databaseName;

    /**
     * Имя пользователя, под которым происходит вход в базу
     */
    private String userName;

    /**
     * Пароль
     */
    private String password;

    /**
     * ip адрес сервера, на котором работает СУБД.
     * Если работает на локальной машине, можно указывать localhost или 127.0.0.1
     */
    private String serverName;

    /**
     * порт, на котором работает СУБД. По умолчанию для mysql - 3306
     */
    private String portNumber;

    /**
     * Connection instance
     */
    private Connection connection;

    /**
     * Создает экземпляр класса используя параметры по умолчанию
     * <p/>
     * databaseManagementSystemName = mysql;
     * databaseName = testDB;
     * userName = root;
     * password = "";
     * serverName = localhost;
     * portNumber = 3306;
     */
    public ConnectionManager() {
        this("mysql", "hellobackend", "lyuda", "12345", "localhost", 3306);
    }

    /**
     * Создает экземпляр класса, позволяя задать все параметры
     *
     * @param databaseManagementSystemName Название СУБД
     * @param databaseName                 Имя базы данных
     * @param userName                     Имя пользователя для подключения к базе
     * @param password                     пароль
     * @param serverName                   ip адрес или доменное имя СУБД
     * @param portNumber                   номер порта
     */
    public ConnectionManager(String databaseManagementSystemName,
                             String databaseName,
                             String userName,
                             String password,
                             String serverName,
                             int portNumber) {
        this.databaseManagementSystemName = databaseManagementSystemName;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.serverName = serverName;
        this.portNumber = String.valueOf(portNumber);
    }

    /**
     * Создает подключение с СУБД или возвращает уже открытое
     *
     * @return объект класса Connection
     * @throws SQLException в случае ошибки подключения
     */
    public Connection getConnection() throws SQLException {

        if (connection != null && !connection.isClosed()) {

            return connection;
        }

        return openConnection(false);
    }

    Connection getBootstrapConnection() throws SQLException {
        return openConnection(true);

    }

    /**
     * Closes opened connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Opens new connection to the database
     *
     * @return opened connection
     * @throws SQLException
     */
    private Connection openConnection(boolean bootstrap) throws SQLException {

//        Properties connectionProps = new Properties();
//        connectionProps.put("user", this.userName);
//        connectionProps.put("password", this.password);

        String dbName = bootstrap ? "" : databaseName;

        if (this.databaseManagementSystemName.equals("mysql")) {
            connection = DriverManager.getConnection(
                    "jdbc:" + this.databaseManagementSystemName + "://" +
                            this.serverName +
                            ":" + this.portNumber + "/" + dbName
                    ,userName, password);
        } else if (this.databaseManagementSystemName.equals("derby")) {
            connection = DriverManager.getConnection(
                    "jdbc:" + this.databaseManagementSystemName + ":" +
                            dbName +
                            ";create=true",
                    userName, password);
        }

        return connection;
    }
}
