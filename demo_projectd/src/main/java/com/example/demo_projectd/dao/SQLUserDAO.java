package com.example.demo_projectd.dao;

import com.example.demo_projectd.model.UserProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository(value = "mySQLUserDAO")
@Transactional
public class SQLUserDAO implements UserDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public UserProject findByUsername(String username)
    {
        UserProject userProject =(UserProject) this.entityManager
                        .createQuery("from Account u where u.username= '"+ username +"'").getSingleResult();

        return userProject;
    }

    @Override
    public void addUser(UserProject userProject) {
        if(!checkByUsername(userProject.getUsername()))
        {
            entityManager.persist(userProject);
        }
    }

    @Override
    public boolean checkByUsername(String username) {
        UserProject userProject = (UserProject) this.entityManager
                .createQuery("from Account u where u.username= '"+ username +"'").getSingleResult();

        return (userProject == null);
    }

    @Override
    public void detectImage(String nameImage) {

    }
}
