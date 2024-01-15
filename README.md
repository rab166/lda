# Introduction

This small spring boot application tests LDAP integration and authenticates the user. 

LDAP server used for authentication is https://www.forumsys.com/2022/05/10/online-ldap-test-server/. Please refer to this url for valid
users. 


# Building the project

mvn clean install

# Access the Login page

- Hit http://localhost:8080/ 

- user will be redirected to default login page 

- provide valid user e.g. gauss/password and after ldap identification user will be redirected to simple home page


### Check ldap connectivity from filetash

- Go to https://demo.filestash.app/login
- hostname: ldap://ldap.forumsys.com:389
- bind DN: cn=read-only-admin,dc=example,dc=com
- bind DN password: password
- Base DN: dc=example,dc=com