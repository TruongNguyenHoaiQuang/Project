package com.example.demo_projectd.dao;

import com.example.demo_projectd.model.ObstacleDetection;
import com.example.demo_projectd.model.UserProject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "memoryUserDAO")
public class MemoryUserDAO implements UserDAO {

    private List<UserProject> listofUsers = new ArrayList<UserProject>();

    public MemoryUserDAO(){

        UserProject chau = new UserProject();
        chau.setId(1);
        chau.setUsername("chau");
        chau.setPassword("chau");
        chau.setFullname("Nguyen Dinh Chau");
        chau.setRoleList("ROLE_USER");

        UserProject admin = new UserProject();
        admin.setId(2);
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFullname("Admin");
        admin.setRoleList("ROLE_ADMIN");

        this.listofUsers.add(chau);
        this.listofUsers.add(admin);
    }

    @Override
    public UserProject findByUsername(String username) {
        for (UserProject user: this.listofUsers) {

            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return new UserProject();
    }

    @Override
    public void addUser(UserProject userProject) {
        for(int i = 0; i > this.listofUsers.size(); i++){
            UserProject user = this.listofUsers.get(i);
            if(user.getUsername().equalsIgnoreCase(userProject.getUsername())) {
                continue;
            }
            userProject.setId(i);
            userProject.setRoleList("ROLE_USER");
            listofUsers.add(userProject);
        }
    }

    @Override
    public void detectImage(String nameImage) throws IOException {

        ObstacleDetection.singleImageTest(nameImage);
    }

}
