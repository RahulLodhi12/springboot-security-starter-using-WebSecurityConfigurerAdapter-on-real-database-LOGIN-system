package com.tcs.training.security;

import com.tcs.training.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService; //interact with Real Database

    //For customizing authentication:
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        /*
        This tells Spring Security:
            Use the MyUserDetailsService class to fetch user credentials.
            Spring will call loadUserByUsername() method behind the scenes. -> MyUserDetailsService class has loadUserByUsername() method
            This is the authentication part – verifying username and password using database.
        */
    }

    //For customizing authorization:
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/user/{id}").hasRole("USER")
                .antMatchers("/allUser").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
        /*
        Breakdown:
            - authorizeRequests(): Begin security rule declarations.
            - antMatchers("/user").hasRole("USER"):
                - Only users with ROLE_USER can access /user.
            - antMatchers("/user/{id}").hasRole("USER"):
                - Same as above, for specific users by ID.
            - antMatchers("/").permitAll():
                - The homepage (/) is open to everyone (no login required).
            - .formLogin():
                - Enables the default Spring Security login page.

            This is the authorization part – defining who can access what.
        */
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
