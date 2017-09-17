/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrievskaja.servise;

import com.andrievskaja.service.model.view.UserView;

/**
 *
 * @author Людмила
 */
public interface UserServise {

    public UserView findByLogin(String login, String password);

    public UserView save(String name, String password);
}
