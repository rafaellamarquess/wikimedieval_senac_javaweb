package com.wiki.medieval.config;

import com.wiki.medieval.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UserPrincipalImpl implements UserDetails {

    private final UserModel userModel;

    public UserPrincipalImpl(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.userModel.getSenha();
    }

    @Override
    public String getUsername() {
        return this.userModel.getEmail();
    }
}
