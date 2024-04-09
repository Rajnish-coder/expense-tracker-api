package com.zee.ExpenseTracker.service.impl;

import com.zee.ExpenseTracker.entity.User;
import com.zee.ExpenseTracker.exception.ItemAlreadyExistException;
import com.zee.ExpenseTracker.exception.ResourceNotFoundException;
import com.zee.ExpenseTracker.model.UserModel;
import com.zee.ExpenseTracker.repository.UserRepository;
import com.zee.ExpenseTracker.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder biCryptEncoder;
    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemAlreadyExistException("Email already exists.");
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        user.setPassword(biCryptEncoder.encode(userModel.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User does not exists"));
    }

    @Override
    public User updateUser(UserModel userModel, Long id) {
        User existingUser = findById(id);
        existingUser.setName(userModel.getName()!=null?userModel.getName():existingUser.getName());
        existingUser.setAge(userModel.getAge()!=null?userModel.getAge():existingUser.getAge());
        existingUser.setEmail(userModel.getEmail()!=null?userModel.getEmail():existingUser.getEmail());
        userRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found."));
    }
}
