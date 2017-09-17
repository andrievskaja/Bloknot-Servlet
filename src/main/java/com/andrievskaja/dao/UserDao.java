/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrievskaja.dao;

import com.andrievskaja.service.model.view.UserView;
import com.andrievskaja.servise.UserServise;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Людмила
 */
public class UserDao implements UserServise {

//    private static ConnectionManager connectionManager = new ConnectionManager("mysql", "hellobackend", "lyuda", "12345", "localhost", 3306);

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/hellobackend?user=lyuda&password=12345");
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
        return connection;
    }

    @Override
    public UserView save(String name, String password) {
        new Bootstrap("hellobackend", getConnection()).init();
        ResultSet resultSet = null;

        String time = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        UserView userView = new UserView();
        int res = 0;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String statements = "INSERT INTO user (id, lastLogin, login, email, password) VALUES (NULL,\"" + time + "\" ,\"" + name + "\" ,\"" + name + "\" , \"" + password + " \")";
            res = statement.executeUpdate(statements, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                userView.setId(resultSet.getLong(1));
                userView.setEmail(name);
                userView.setLogin(name);
            }
            if (res == 0) {
                return null;
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return userView;
    }

    @Override
    public UserView findByLogin(String login, String password) {
        UserView userView = new UserView();
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT *FROM user u WHERE  u.login = '" + login + "' and u.password = '" + password + "'");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userView.setId(Long.parseLong(resultSet.getString("id")));
                userView.setLogin(resultSet.getString("login"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userView;
    }

    private UserView userView(ResultSet resultSet) throws SQLException {
        UserView userView = new UserView();

        return userView;
    }
}
