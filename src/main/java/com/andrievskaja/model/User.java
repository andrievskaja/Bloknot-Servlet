/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrievskaja.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Людмила
 */
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})
})
@Entity
public class User {

    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private Set<String> roles;
    private List<Record>record;
    private Date lastLogin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public User() {
    }

    public User(Long id, String email, 
            String login, String password, 
            Set<String> roles, 
            Date lastLogin) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.lastLogin = lastLogin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ElementCollection
    @CollectionTable(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"User_id", "roles"})
    })
    public Set<String> getRoles() {
         if (roles == null) {
            roles = new LinkedHashSet<>();
        }
        return roles;

    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
  @OneToMany( cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, mappedBy = "user")
    public List<Record> getRecord() {
        return record;
    }

    public void setRecord(List<Record> record) {
        this.record = record;
    }





}
