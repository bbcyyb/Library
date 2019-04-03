package dev.kevinyu.service.restful.service.impl;

import dev.kevinyu.service.restful.model.UserVO;
import dev.kevinyu.service.restful.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersService _usersService;

    @Autowired
    public UserDetailsServiceImpl(UsersService usersService){
        this._usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserVO userVO = _usersService.getByName(name);
        return userVO;
    }
}
