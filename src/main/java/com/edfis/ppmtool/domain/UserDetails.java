package com.edfis.ppmtool.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserDetails {
    public Collection<? extends GrantedAuthority> getAuthorities();
}
