/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrievskaja.dao;

import com.andrievskaja.service.model.view.RecordForm;
import com.andrievskaja.service.model.view.RecordView;
import com.andrievskaja.service.model.view.UserView;
import com.andrievskaja.servise.RecordServise;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Людмила
 */
public class RecordDao implements RecordServise {

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
    public List<RecordView> getAll(Long userId) {
        List<RecordView> records = new LinkedList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("SELECT *FROM record WHERE  user_id = ?");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            RecordView record;
            while (resultSet.next()) {
                record = new RecordView();
                record.setId(Long.parseLong(resultSet.getString("id")));
                record.setText(resultSet.getString("text"));
                record.setDate(resultSet.getString("date"));
                records.add(record);
            }
            resultSet.close();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        System.out.println(records);
        return records;
    }

    @Override
    public String delete(Long id, Long userId) {
        Connection connection = null;
        int result = 0;
        try {
            connection = getConnection();
            Statement stmt = connection.createStatement();
            String SQL = "DELETE FROM record WHERE id='" + id + "' and user_id = '" + userId + "'";
            result = stmt.executeUpdate(SQL);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "no";
        }
        if (result == 1) {
            return "ok";
        } else {
            return "no";
        }
    }

    @Override
    public String edit(Long id, String text, Long userId) {
        Connection connection = null;
        int result = 0;
        try {
            connection = getConnection();
            Statement stmt = connection.createStatement();
            String SQL = "UPDATE record SET text= '" + text + "' WHERE id='" + id + "' and user_id = '" + userId + "'";
            result = stmt.executeUpdate(SQL);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "no";
        }
        if (result == 1) {
            return "ok";
        } else {
            return "no";
        }
    }
     @Override
    public RecordView save(RecordForm form) {
        ResultSet resultSet = null;

        String time = new SimpleDateFormat("yyyy-MM-dd HH:MM").format(Calendar.getInstance().getTime());
        RecordView recordView = new RecordView();
        int res = 0;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String statements = "INSERT INTO record (id, text, user_id, date) VALUES (NULL,\"" + form.getText() + "\" ,\"" + form.getUserId() + "\" , \"" + time + " \")";
            res = statement.executeUpdate(statements, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                recordView.setId(resultSet.getLong(1));
                recordView.setUserId(form.getUserId());
                recordView.setDate(time); 
                recordView.setText(form.getText());
            }
            if (res == 0) {
                return null;
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return recordView;
    }
}
