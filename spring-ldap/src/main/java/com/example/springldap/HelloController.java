package com.example.springldap;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final LdapTemplate ldap;

    @GetMapping
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName();
    }

    @GetMapping("/cn")
    public List<String> cn(Authentication authentication) {
        AttributesMapper<String> mapper = (attrs) -> attrs.get("cn").get().toString();
        return
                ldap.search("ou=people", "uid=" + authentication.getName(), mapper);
    }
}
