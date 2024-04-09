package com.zee.ExpenseTracker.controller;

import com.zee.ExpenseTracker.entity.User;
import com.zee.ExpenseTracker.model.JwtResponse;
import com.zee.ExpenseTracker.model.LoginModel;
import com.zee.ExpenseTracker.model.UserModel;
import com.zee.ExpenseTracker.security.CustomUserDetailsService;
import com.zee.ExpenseTracker.service.UserService;
import com.zee.ExpenseTracker.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid  @RequestBody UserModel userModel){
        return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginModel loginModel) throws Exception {
        authenticate(loginModel.getEmail(),loginModel.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginModel.getEmail());
        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<JwtResponse>(new JwtResponse(jwtToken),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }catch (DisabledException ex){
            throw new Exception("User disabled");
        }catch (BadCredentialsException ex){
            throw new Exception("Bad Credentials.");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id){
        return new ResponseEntity<User>(userService.findById(id),HttpStatus.OK);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserModel userModel,@PathVariable("id") Long id){
        return new ResponseEntity<User>(userService.updateUser(userModel,id),HttpStatus.OK);
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/users")
    public void deleteUser(@RequestParam("id") Long id){
        userService.deleteUser(id);
    }
}
