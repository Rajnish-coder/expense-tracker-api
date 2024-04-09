package com.zee.ExpenseTracker.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserModel {
    @NotBlank(message = "Name should not be empty.")
    private String name;
    private Long age = 0L;
    @NotBlank(message = "Password should not be empty")
    @Size(min = 5,message = "Password length should be at least 5")
    private String password;
    @NotNull(message = "Email should not be empty.")
    @Email(message = "Email should be valid")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
