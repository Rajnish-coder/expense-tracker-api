package com.zee.ExpenseTracker.service;

import com.zee.ExpenseTracker.entity.User;
import com.zee.ExpenseTracker.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(UserModel userModel);
    User findById(Long id);
    User updateUser(UserModel userModel,Long id);
    void deleteUser(Long id);
    User getLoggedInUser();
}
