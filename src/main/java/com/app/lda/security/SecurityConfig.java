package com.app.lda.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests((authorize) -> authorize
        .anyRequest().authenticated()
      )
      .formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth
      .ldapAuthentication()
      .userSearchFilter("(uid={0})")
      .userSearchBase("dc=example,dc=com")
      .groupSearchBase("ou=scientists,dc=example,dc=com")
      .groupSearchFilter("cn={0}")
      .contextSource()
      .url("ldap://ldap.forumsys.com")
      .port(389)
      .managerDn("cn=read-only-admin,dc=example,dc=com")
      .managerPassword("password");
  }
  /*public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userDnPatterns("uid={0},dc=example,dc=com")
        .groupSearchBase("ou=mathematicians")
        //.userSearchBase("ou=mathematicians,dc=example,dc=com")
        .contextSource()
          .url("ldap://ldap.forumsys.com:389/dc=example,dc=com")
          //.managerDn("uid=gauss,dc=example,dc=com")
          //.managerPassword("password")
          .and()
        .passwordCompare()
          .passwordEncoder(new BCryptPasswordEncoder())
          .passwordAttribute("UserPassword");
  }*/

}