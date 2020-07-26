package com.example.demo_projectd.dao;

import com.example.demo_projectd.model.UserProject;
import org.springframework.stereotype.Repository;

@Repository(value = "mySQLUserDAO")
public class SQLUserDAO implements UserDAO {


    @Override
    public UserProject findByUsername(String username) {
        return null;
    }

    @Override
    public void addUser(UserProject userProject) {

    }

    @Override
    public void detectImage(String nameImage) {

    }
}
