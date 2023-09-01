# Spring LDAP

> [Docs](https://docs.spring.io/spring-ldap/docs/current/reference)
> [github sample](https://github.com/spring-projects/spring-ldap/tree/main/samples)  

## 1. Preface

- The Java Naming and Directory Interface(JNDI) is to LDAP programming what Java Database Connectivity(JDBC) is to SQL Programming
- There are several similarities between JDBC and JNDI/LDAP(Java LDAP); Despite being two completely different APIs with different pros and cons, they shara a number of less flattering characteristics
  - They require extensive plumbing code, even to perform the simplest of tasks
  - All resources need to be correctly closed, no matter what happens
  - Exception handling is difficult

### 2.2 Traditional Java LDAP versus `LdapTemplate`

- Consider a method that should search some storage for all persons: JDBC
  - Create a connection
  - Run a query by using statement
  - Loop over the result set
  - Retrieve the column we want
  - Add it to list
- Working against an LDAP database with JNDI
  - Create a context
  - Perform a search by using a search filter
  - Loop over the resulting naming enumeration
  - Retrieve the attribute we want
  - Add it to a list

## 3. Basic Usage

