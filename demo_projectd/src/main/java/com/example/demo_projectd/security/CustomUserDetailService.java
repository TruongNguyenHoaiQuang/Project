package com.example.demo_projectd.security;


import com.example.demo_projectd.dao.MemoryUserDAO;
import com.example.demo_projectd.model.UserProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    @Qualifier(value = "memoryUserDAO")
    MemoryUserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProject user = userDAO.findByUsername(username);

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        grantList = Arrays.stream(user.getRoleList().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails userDetails = new User(username, encoder.encode(user.getPassword()), grantList);

        return userDetails;
    }
}
