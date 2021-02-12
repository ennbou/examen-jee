package com.ennbou.authservicce.service;

import com.ennbou.authservicce.entities.AppRole;
import com.ennbou.authservicce.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}

