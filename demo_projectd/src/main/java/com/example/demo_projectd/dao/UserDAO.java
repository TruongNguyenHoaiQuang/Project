package com.example.demo_projectd.dao;

import com.example.demo_projectd.model.UserProject;

import java.io.IOException;

public interface UserDAO {

    public UserProject findByUsername(String username);

    public void addUser(UserProject userProject);

    public void detectImage(String nameImage) throws IOException;
}
