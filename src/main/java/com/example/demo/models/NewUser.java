package com.example.demo.models;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder

public class NewUser {

    private final String login;
    private final String password;
    private final String fullName;

    public NewUser(String login,String password,String fullName){
        this.login = login;
        this.password = password;
        this.fullName = fullName;
    }
}