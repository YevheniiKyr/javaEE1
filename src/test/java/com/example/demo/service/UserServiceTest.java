package com.example.demo.service;

import com.example.demo.exceptions.LoginExistsException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.models.NewUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {


    @SpyBean
    private UserValidator uv;
    @Autowired
    private UserService us;
    @Autowired
    private UserRepository ur;

    @SpyBean
    private UserRepository urSpy;

    String createdUserName = "login2edwedefrfe";
    String notCreatedUserName = "not_login2edwedefrferffr";

    @Test
    void createUser(){

        assertThatThrownBy(() -> us.createNewUser(new NewUser(createdUserName,"passw","name"))).
                isInstanceOf(LoginExistsException.class);
        // Mockito.verify(ur).saveNewUser( );
        assertThat(ur.isLoginExists(createdUserName));
        Mockito.verify(uv).validateNewUser(ArgumentMatchers.any());

    }

    @ParameterizedTest
    @CsvSource({
            "Dima,ggff,Dimon2000",
            "Antik,kkff,Anton2001",
            "Aleg,ffff,Olezhka3000"
    })
    void createUser(String login, String password, String fullName){

     //   assertThatThrownBy(() -> us.createNewUser(new NewUser(createdUserName,"passw","name"))).
          //     isInstanceOf(LoginExistsException.class);
         us.createNewUser(new NewUser(login,password,fullName));

        // Mockito.verify(ur).saveNewUser( );
        assertThat(ur.isLoginExists(login));
        Mockito.verify(uv).validateNewUser(ArgumentMatchers.any());

    }

    @Test
    void getUserByLogin(){

        us.createNewUser(new NewUser(createdUserName,"passw","name"));
        us.getUserByLogin(createdUserName);
        Mockito.verify(uv).validateNewUser(ArgumentMatchers.any());
        Mockito.verify(urSpy).isLoginExists(ArgumentMatchers.anyString());

        // assertThat((ur.isLoginExists(notCreatedUserName)));
        assertThatThrownBy(() -> us.getUserByLogin(notCreatedUserName)).isInstanceOf(UserNotFoundException.class);

    }

}