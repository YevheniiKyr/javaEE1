package com.example.demo.validation;

import com.example.demo.exceptions.ConstraintViolationException;
import com.example.demo.exceptions.LoginExistsException;
import com.example.demo.models.NewUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserValidatorTest {
     // @SpyBean
    //  private UserValidator uv;
    //  @Autowired
     // private UserService us;
    //  @Autowired
     // private UserRepository ur;
      @Mock
      private UserRepository userRepository;
      //private UserRepository userRepository =Mockito.mock(UserRepository.class);

    @InjectMocks
    // private UserValidator uv = new UserValidator(userRepository);
       private UserValidator uv;
    // @InjectMocks
    //  private UserService us;
      @ParameterizedTest
      @CsvSource({
              // "Dima,ggff,Dimon2000",
              // "Antik,kkff,Anton2001",
              // "Aleg,ffff,Olezhka3000",
              "Alegol,hh,Olezhka3009",
              "Alegol2, ввв ,Olezhka3009",
              "Alegol3,hhrffrfrrf,Olezhka3009",
              "Alegol4,hk,Olezhka3009",
              "Alegol5,ddeeddede,Olezhka3009",
              "Alegol6,ddeedd,Olezhka3009",


      })
      //В консолі написано, якщо є помилка
      // є перевірка на довжину пароля та регекс
      void ValidateUserPassword(String login, String password, String fullName) {

            // Mockito.when(userRepository.isLoginExists(ArgumentMatchers.anyString())).thenReturn(Boolean.TRUE);
            // uv.validateNewUser(new NewUser("logeded","a","b"));
            try {
                 // us.createNewUser(new NewUser(login, password, fullName));
                  uv.validateNewUser(new NewUser(login, password, fullName));
                  //Mockito.verify(uv).validateNewUser(ArgumentMatchers.any());

                  System.out.println("You dont have errors in you object " + login);

            } catch (Exception ex) {
                  assertThatThrownBy(() -> uv.validateNewUser(new NewUser(login, password, fullName))).
                          isInstanceOf(ConstraintViolationException.class);
                  System.out.println(ex.getMessage() +" " + login);

            }


      }
      @Test

      void ValidateUserLogin() {

          //кидає помилку якщо такий логін існує
          //інакше все добре
          Mockito.when(userRepository.isLoginExists("login")).thenReturn(true);

          assertThatThrownBy(() -> uv.validateNewUser(
                  NewUser.builder().login("login").password("123").fullName("user").build()))
                  .isInstanceOf(LoginExistsException.class);
      }
      }