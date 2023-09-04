package com.example.springldap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ldap.odm.annotations.*;

import javax.naming.Name;

@Entry(objectClasses = {"person", "top"})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class PersonEntry {

    @Id
    private Name dn;

    @Attribute(name = "cn")
//    @DnAttribute(value = "cn", index = 0)
    private String fullName;

    private String uid;

    @DnAttribute(value = "ou", index = 0)
    @Transient
    private String company;
}
