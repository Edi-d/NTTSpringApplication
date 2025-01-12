package org.example.authentication.service;

import org.example.domain.entity.Owner;
import org.example.domain.entity.enums.OwnerRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomOwnerDetails implements UserDetails {

    private final String username;
    private final String password;
    private final OwnerRoleEnum role;

    public CustomOwnerDetails(Owner owner) {
        this.username = owner.getEmail();
        this.password = owner.getPassword();
        this.role = owner.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Override other necessary methods from UserDetails with default or appropriate implementations

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}