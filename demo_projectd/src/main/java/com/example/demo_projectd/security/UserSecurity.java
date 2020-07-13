package com.example.demo_projectd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity

public class UserSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
//        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic().and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/login").permitAll();
//        http.authorizeRequests().antMatchers("/homeSigned").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
//        http.authorizeRequests().antMatchers("/").access("hasAnyRole('ROLE_ADMIN')");

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

        http.authorizeRequests().and()
                .rememberMe().tokenRepository(this.persistentTokentResposity())
                .tokenValiditySeconds(1*24*60*60);
    }

    private PersistentTokenRepository persistentTokentResposity() {
        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
        return memory;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception{
//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("chau").password("chau").roles("USER")
//                .and().withUser("admin").password("admin").roles("ADMIN");
        auth.userDetailsService(customUserDetailService).passwordEncoder(PasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder PasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


}
