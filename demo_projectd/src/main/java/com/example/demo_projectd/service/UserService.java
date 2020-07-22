package com.example.demo_projectd.service;

import com.example.demo_projectd.dao.UserDAO;
import com.example.demo_projectd.model.UserProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service(value = "studentService")
public class UserService {

    @Autowired
    @Qualifier(value = "mySQLUserDAO")
    UserDAO userDAO;

    public void addUser(UserProject userProject) {
        userDAO.addUser(userProject);
    }

    public void detectImage(String nameImage) throws IOException {
        userDAO.detectImage(nameImage);
    }
}
