package com.ltp.gradesubmission.service.impl;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.repository.UserRepository;
import com.ltp.gradesubmission.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public String getUserNameById(Long id) {
        User user =  userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Could not find User with id=" + id));
        return user.getUsername();
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
                "Could not find User with username=" + username));
    }

    @Override
    public User saveUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new DataIntegrityViolationException("User with id=" + user.getId() + " already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("User with username=" + user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // only once for new user
        return userRepository.save(user);
    }

}
