package com.shopping_cart.service;

import com.shopping_cart.dto.signin.SignInRequestDTO;
import com.shopping_cart.dto.signin.SignInResponseDTO;
import com.shopping_cart.dto.signup.SignUpRequestDTO;
import com.shopping_cart.dto.signup.SignUpResponseDTO;
import com.shopping_cart.exception.custom.common.InternalServerException;
import com.shopping_cart.exception.custom.user.AddNewUserException;
import com.shopping_cart.exception.custom.user.UserEmailExistsException;
import com.shopping_cart.exception.custom.user.UserMobileNoExistsException;
import com.shopping_cart.exception.custom.user.UserNotFoundException;
import com.shopping_cart.model.User;
import com.shopping_cart.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Sign in")
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /* Success scenarios*/
    @Test
    @DisplayName("User sign up : status 201 : created")
    void onUserSignUp() {

        String id = "6655fc68f5a6fe52093f1704";
        String passwordHash = "$2a$10$Wa87iGQ1tHRzeQtC2rZ9U.fX8.9KeWSM981a.1LznyxDdsQFprMB6";
        User user = new User();
        user.setId(id);
        user.setMobileNo("9028894922");
        user.setEmail("john2@gmail.com");
        user.setPassword(passwordHash);

        // mock repository call
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(null);
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenReturn(null);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // set request values in dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894922");
        signUpRequestDTO.setEmail("john2@gmail.com");
        signUpRequestDTO.setPassword("john");

        // call service method
        SignUpResponseDTO response = userService.onUserSignUp(signUpRequestDTO);

        // value matchers
        assertThat(response.getId()).isNotNull();
        assertEquals(id, response.getId());
        assertEquals(signUpRequestDTO.getEmail(), response.getEmail());
        assertEquals(signUpRequestDTO.getEmail(), response.getEmail());
        assertEquals(signUpRequestDTO.getMobileNo(), response.getMobileNo());
        assertThat(response.getPassword()).isNotNull();
        assertEquals(passwordHash, response.getPassword());

    }

    @Test
    @DisplayName("User sign in : status 200 : user log in with mobile no")
    void onUserSignInByMobileNo() {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("9028894924");
        signInRequestDTO.setPassword("john");

        User user = User.builder().id("6655fc68f5a6fe52093f1704").mobileNo("9822868686").email("john@gmail.com").password("$2a$10$Wa87iGQ1tHRzeQtC2rZ9U.fX8.9KeWSM981a.1LznyxDdsQFprMB6").build();

        // mock repository call
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenReturn(user);

        // call service method
        SignInResponseDTO response = userService.onUserSignIn(signInRequestDTO);

        // assertion
        assertThat(response.getId()).isNotNull();
        assertEquals(user.getMobileNo(), response.getMobileNo());
        assertEquals(user.getEmail(), response.getEmail());

    }

    @Test
    @DisplayName("User sign in : status 200 : user log in with email")
    void onUserSignInByEmail() {

        User user = User.builder().id("6655fc68f5a6fe52093f1704").mobileNo("9028894922").email("john@gmail.com").password("$2a$10$Wa87iGQ1tHRzeQtC2rZ9U.fX8.9KeWSM981a.1LznyxDdsQFprMB6").build();

        // mock repository call
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(user);

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();

        signInRequestDTO.setUsername("john@gmail.com");
        signInRequestDTO.setPassword("john");

        // call service method
        SignInResponseDTO response = userService.onUserSignIn(signInRequestDTO);

        // assertion
        assertThat(response.getId()).isNotNull();
        assertEquals(user.getMobileNo(), response.getMobileNo());
        assertEquals(user.getEmail(), response.getEmail());
    }

    /* Exception scenarios*/
    @Test
    @DisplayName("User sign up : status 409 : email exists exception")
    void onUserSignUpEmailExistsException() {

        String id = "6655fc68f5a6fe52093f1704";
        User user = new User();
        user.setId(id);
        user.setMobileNo("9028894922");
        user.setEmail("john2@gmail.com");
        user.setPassword("John@123");

        // mock repo methods
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(user);
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenReturn(null);

        // set request values in dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894922");
        signUpRequestDTO.setEmail("john2@gmail.com");
        signUpRequestDTO.setPassword("John@123");

        // call service method
        Exception exception = assertThrows(UserEmailExistsException.class, () ->
                userService.onUserSignUp(signUpRequestDTO));

        // assertion
        assertEquals("Provided email id already exists", exception.getMessage());
    }

    @Test
    @DisplayName("User sign up : status 409 : mobile no exists exception")
    void onUserSignUpMobileNoExistsException() {

        String id = "6655fc68f5a6fe52093f1704";
        User user = new User();
        user.setId(id);
        user.setMobileNo("9028894922");
        user.setEmail("john2@gmail.com");
        user.setPassword("John@123");

        // mock repo methods
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(null);
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenReturn(user);

        // set request values in dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894922");
        signUpRequestDTO.setEmail("john2@gmail.com");
        signUpRequestDTO.setPassword("John@123");

        // call service method
        Exception exception = assertThrows(UserMobileNoExistsException.class,
                () -> userService.onUserSignUp(signUpRequestDTO));

        // assertion
        assertEquals("Provided mobile no already exists", exception.getMessage());
    }

    @Test
    @DisplayName("User sign up : status 500 : internal server exception")
    void onUserSignUpInternalServerException() {

        // mock repo methods
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(null);
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenReturn(null);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(null);

        // set request values in dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894922");
        signUpRequestDTO.setEmail("john2@gmail.com");
        signUpRequestDTO.setPassword("John@123");

        // call service method
        Exception exception = assertThrows(AddNewUserException.class,
                () -> userService.onUserSignUp(signUpRequestDTO));

        // assertion
        assertEquals("Failed to add user", exception.getMessage());
    }

    @Test
    @DisplayName("User sign in : status 404 : user not found by mobile no")
    void onUserSignInByMobileNoNotFoundException() {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("9028894924");
        signInRequestDTO.setPassword("john");

        // mock repository call
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenReturn(null);

        // call service method
        Exception exception = assertThrows(UserNotFoundException.class,
                () -> userService.onUserSignIn(signInRequestDTO));

        // assertion
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("User sign in: status 404: user not found by mobile no")
    void onUserSignInByEmailNotFoundException() {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("john@gmail.com");
        signInRequestDTO.setPassword("john");

        // mock repository call
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(null);

        // call service method
        Exception exception = assertThrows(UserNotFoundException.class,
                () -> userService.onUserSignIn(signInRequestDTO));

        // assertion
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("User sign in: status 500: internal server exception in sign in by mobile no")
    void onUserSignInByMobileNoInternalServerException() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("9028894924");
        signInRequestDTO.setPassword("john");

        // mock repository call
        when(userRepository.findUserByMobileNo(Mockito.anyString())).thenThrow(new InternalServerException("Internal server error"));

        // call service method
        Exception exception = assertThrows(InternalServerException.class,
                () -> userService.onUserSignIn(signInRequestDTO));

        // assertion
        assertEquals("Internal server error", exception.getMessage());
    }

    @Test
    @DisplayName("User sign in: status 500: internal server exception in sign in by email")
    void onUserSignInByEmailInternalServerException() {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("john@gmail.com");
        signInRequestDTO.setPassword("john");

        // mock repository call
        when(userRepository.findUserByEmail(Mockito.anyString())).thenThrow(new InternalServerException("Internal server error"));

        // call service method
        Exception exception = assertThrows(InternalServerException.class,
                () -> userService.onUserSignIn(signInRequestDTO));

        // assertion
        assertEquals("Internal server error", exception.getMessage());
    }
}
