package com.shopping_cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping_cart.dto.signin.SignInRequestDTO;
import com.shopping_cart.dto.signin.SignInResponseDTO;
import com.shopping_cart.dto.signup.SignUpRequestDTO;
import com.shopping_cart.dto.signup.SignUpResponseDTO;
import com.shopping_cart.exception.custom.user.PasswordNotMatchingException;
import com.shopping_cart.exception.custom.user.UserEmailExistsException;
import com.shopping_cart.exception.custom.user.UserMobileNoExistsException;
import com.shopping_cart.exception.custom.user.UserNotFoundException;
import com.shopping_cart.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("User controller")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    /* Success scenarios */
    @Test
    @DisplayName("User sign up: status 201: created")
    void onUserSignUp() throws Exception {

        // set response values in dto
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        signUpResponseDTO.setMobileNo("9028894921");
        signUpResponseDTO.setEmail("john11@gmail.com");
        signUpResponseDTO.setPassword("John@123");

        // mock service methods
        Mockito.when(userService.onUserSignUp(Mockito.any(SignUpRequestDTO.class))).thenReturn(signUpResponseDTO);


        // set request values in dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894921");
        signUpRequestDTO.setEmail("john11@gmail.com");
        signUpRequestDTO.setPassword("John@123");

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signUpRequestDTO);

        this.mockMvc.perform(post("/v1/api/signup").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mobileNo").value("9028894921"))
                .andExpect(jsonPath("$.email").value("john11@gmail.com"))
                .andExpect(jsonPath("$.password").value("John@123"));
    }

    @Test
    @DisplayName("User sign in by mobile no: status 200: success")
    void onUserSignInByMobileNo() throws Exception {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("9028894921");
        signInRequestDTO.setPassword("john");

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signInRequestDTO);

        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        signInResponseDTO.setId("66621515f570e748be3aabfb");
        signInResponseDTO.setMobileNo("9028894921");
        signInResponseDTO.setEmail("john@gmail.com");

        Mockito.when(userService.onUserSignIn(Mockito.any(SignInRequestDTO.class))).thenReturn(signInResponseDTO);

        this.mockMvc.perform(post("/v1/api/signin").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("66621515f570e748be3aabfb"))
                .andExpect(jsonPath("$.mobileNo").value("9028894921"))
                .andExpect(jsonPath("$.email").value("john@gmail.com"));
    }

    @Test
    @DisplayName("User sign in by email: status 200: success")
    void onUserSignInByEmail() throws Exception {

        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("john@gmail.com");
        signInRequestDTO.setPassword("john");

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signInRequestDTO);

        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        signInResponseDTO.setId("66621515f570e748be3aabfb");
        signInResponseDTO.setMobileNo("9028894921");
        signInResponseDTO.setEmail("john@gmail.com");

        Mockito.when(userService.onUserSignIn(Mockito.any(SignInRequestDTO.class))).thenReturn(signInResponseDTO);

        this.mockMvc.perform(post("/v1/api/signin").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("66621515f570e748be3aabfb"))
                .andExpect(jsonPath("$.mobileNo").value("9028894921"))
                .andExpect(jsonPath("$.email").value("john@gmail.com"));
    }

    /* Exception scenarios */
    @Test
    @DisplayName("User sign up: status 409: email exists")
    void onUserSignUpEmailExistException() throws Exception {

        // set values in request body dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894921");
        signUpRequestDTO.setEmail("john11@gmail.com");
        signUpRequestDTO.setPassword("John@123");

        // set values in response body dto
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        signUpResponseDTO.setMobileNo("9028894921");
        signUpResponseDTO.setEmail("john11@gmail.com");
        signUpResponseDTO.setPassword("John@123");

        // mock service method
        Mockito.when(userService.onUserSignUp(Mockito.any(SignUpRequestDTO.class))).thenThrow(new UserEmailExistsException("Provided email id already exists"));

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signUpRequestDTO);

        // perform api call and match response
        this.mockMvc.perform(post("/v1/api/signup").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(409))
                .andExpect(jsonPath("$.message").value("Provided email id already exists"));
    }

    @Test
    @DisplayName("User sign up: status 409: mobile no exists")
    void onUserSignUpMobileNoExistException() throws Exception {

        // set values in request body dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setMobileNo("9028894921");
        signUpRequestDTO.setEmail("john22@gmail.com");
        signUpRequestDTO.setPassword("John@123");

        // set values in response body dto
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        signUpResponseDTO.setMobileNo("9028894921");
        signUpResponseDTO.setEmail("john22@gmail.com");
        signUpResponseDTO.setPassword("John@123");

        // mock service method
        Mockito.when(userService.onUserSignUp(Mockito.any(SignUpRequestDTO.class))).thenThrow(new UserMobileNoExistsException("Provided mobile no already exists"));

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signUpRequestDTO);

        // perform api call and match response
        this.mockMvc.perform(post("/v1/api/signup").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(409))
                .andExpect(jsonPath("$.message").value("Provided mobile no already exists"));
    }

    @Test
    @DisplayName("User sign in by mobile no: status 404: user not found")
    void onUserSignInUserNotFoundException() throws Exception {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("9028894921");
        signInRequestDTO.setPassword("john");

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signInRequestDTO);

        Mockito.when(userService.onUserSignIn(Mockito.any(SignInRequestDTO.class))).thenThrow(new UserNotFoundException("User not found"));

        this.mockMvc.perform(post("/v1/api/signin").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    @DisplayName("User sign in by mobile no: status 404: user not found")
    void onUserSignInPasswordNotMatchingException() throws Exception {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setUsername("9028894921");
        signInRequestDTO.setPassword("john");

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signInRequestDTO);

        Mockito.when(userService.onUserSignIn(Mockito.any(SignInRequestDTO.class))).thenThrow(new PasswordNotMatchingException("Password not matching"));

        this.mockMvc.perform(post("/v1/api/signin").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.message").value("Password not matching"));
    }

    /* Request body validation */
    @Test
    @DisplayName("User sign up: status 400: bad request")
    void onUserSignUpRequestBodyValidation() throws Exception {

        List<String> errors = Arrays.asList("The Mobile number is required", "The email id is required", "The Password is required");

        // set request values in dto
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(signUpRequestDTO);

        // perform api call and match response
        this.mockMvc.perform(post("/v1/api/signup").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("The Mobile number is required")))
                .andExpect(jsonPath("$.errors", hasItem("The email id is required")))
                .andExpect(jsonPath("$.errors", hasItem("The Password is required")));
    }
        /*
    .andExpect(jsonPath("$.violations", hasSize(3)))
    .andExpect(jsonPath("$.violations", containsInAnyOrder(
            Map.of("field", "name", "message", "must not be empty"),
            Map.of("field", "email", "message", "must not be empty"),
            Map.of("field", "birthdate", "message", "must not be null")
          )
))
     */
}
