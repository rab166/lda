package com.app.lda.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class SecurityConfig {
		
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("ldap://${spring.ldap.urls}:${ldap.port:389}")
    private String url;

    @Value("${spring.ldap.username}")
    private String user;

    @Value("${spring.ldap.password}")
    private String password;

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
	  logger.info("LDAP: {}", url);
	   auth
	      .ldapAuthentication()
	         .userSearchFilter("(uid={0})")
	         .contextSource()
	            .url(url + "/dc=example,dc=com")
	            .managerDn(user)
	            .managerPassword(password);
	}

  /*
   * Uncomment to test ldap connectivity without application properties 
   * 
   * */
  /*@Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
	   auth
	      .ldapAuthentication()
	         .userSearchFilter("(uid={0})")
	         .contextSource()
	            .url("ldap://ldap.forumsys.com:389/dc=example,dc=com")
	            .managerDn("cn=read-only-admin,dc=example,dc=com")
	            .managerPassword("password");
	}
  */
  
  /*
   * Uncomment to test ldap connectivity without application properties and search within particular OU e.g. scientists or mathematicians
   * 
   * */
  /*@Autowired
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
  }*/

}