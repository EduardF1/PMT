package com.edfis.ppmtool.services;

import com.edfis.ppmtool.domain.User;
import com.edfis.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
       newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
       return userRepository.save(newUser);
    }
}
