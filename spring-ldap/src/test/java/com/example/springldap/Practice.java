package com.example.springldap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.ldap.userdetails.Person;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

@SpringBootTest
class Practice {

    @Autowired
    LdapTemplate ldapTemplate;

    @Test
    void example1() {
        List<String> allPersonNames = ldapTemplate.search(
                LdapQueryBuilder.query()
                        .where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    @Override
                    public String mapFromAttributes(Attributes attributes) throws NamingException {
                        return (String) attributes.get("cn").get();
                    }
                }
        );
        System.out.println(allPersonNames);
    }

    @Test
    void example2() {
        List<Person> allPersonNames = ldapTemplate.search(
                LdapQueryBuilder.query()
                        .where("objectclass").is("person"),
                new PersonContextMapper()
        );
        System.out.println(allPersonNames);
    }

    /**
     * dn: uid=bob,ou=people,dc=springframework,dc=org 이지만
     * SecurityConfig 에 ldapContainer 설정할 때 default prefix 설정했음
     */
    @Test
    void example3() {
        Person person = ldapTemplate.lookup(
                "uid=bob,ou=people",
                new PersonContextMapper()
        );
        System.out.println(person);
    }

    static class PersonContextMapper extends AbstractContextMapper<Person> {

        @Override
        protected Person doMapFromContext(DirContextOperations ctx) {
            Person.Essence essence = new Person.Essence();

            essence.setCn(ctx.getStringAttributes("cn"));
            essence.setSn(ctx.getStringAttribute("sn"));
            essence.setUsername(ctx.getStringAttribute("uid"));
            essence.setDn(ctx.getDn());

            return (Person) essence.createUserDetails();
        }

    }

    /**
     * LDAP searches involve a number of parameters, including the following:
     * - Base LDAP path: Where in the LDAP tree should the search start.
     * - Search scope: How deep in the LDAP tree should the search go.
     * - Attributes to return.
     * - Search filter: The criteria to use when selecting elements within scope.
     */
    @Test
    void example4() {
        List<String> result = ldapTemplate.search(
                LdapQueryBuilder.query()
                        .base("ou=otherpeople")
                        .attributes("cn", "sn")
                        .where("objectclass").is("person")
                        .and("sn").is("Smeth"),
                new AttributesMapper<String>() {
                    @Override
                    public String mapFromAttributes(Attributes attributes) throws NamingException {
                        return (String) attributes.get("cn").get();
                    }
                }
        );
        System.out.println(result);
    }

    /**
     * The standard Java implementation of Distinguished Name (LdapName) performs well when it comes to parsing Distinguished Names.
     * However, in practical use, this implementation has a number of shortcomings:
     * - The LdapName implementation is mutable, which is badly suited for an object that represents identity.
     * - Despite its mutable nature, the API for dynamically building or modifying Distinguished Names by using LdapName is cumbersome. Extracting values of indexed or (particularly) named components is also a little bit awkward.
     * - Many of the operations on LdapName throw checked exceptions, requiring try-catch statements for situations where the error is typically fatal and cannot be repaired in a meaningful manner.
     *
     * To simplify working with Distinguished Names,
     * Spring LDAP provides an LdapNameBuilder, as well as a number of utility methods in LdapUtils that help when working with LdapName.
     */

    /**
     * Entity classes managed with the object mapping methods are required to be annotated with annotations from the org.springframework.ldap.odm.annotations package.
     * The available annotations are:
     * - @Entry: Class level annotation indicating the objectClass definitions to which the entity maps. (required)
     * - @Id: Indicates the entity DN. The field declaring this attribute must be a derivative of the javax.naming.Name class. (required)
     * - @Attribute: Indicates the mapping of a directory attribute to the object class field.
     * - @DnAttribute: Indicates the mapping of a DN attribute to the object class field.
     * - @Transient: Indicates the field is not persistent and should be ignored by the OdmManager.
     */

    @Test
    void example18() {
        List<PersonEntry> result = ldapTemplate.findAll(PersonEntry.class);
        System.out.println(result);
    }
}
