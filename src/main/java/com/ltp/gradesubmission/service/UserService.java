package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.User;

public interface UserService {
    String getUserNameById(Long id);
    User getUserByName(String username);
    User saveUser(User user);
}
