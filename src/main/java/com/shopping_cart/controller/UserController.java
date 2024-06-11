package com.shopping_cart.controller;

import com.shopping_cart.common.constant.ApiConstant;
import com.shopping_cart.dto.signup.SignUpRequestDTO;
import com.shopping_cart.dto.signup.SignUpResponseDTO;
import com.shopping_cart.dto.signin.SignInRequestDTO;
import com.shopping_cart.dto.signin.SignInResponseDTO;
import com.shopping_cart.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiConstant.Names.API_PATH, produces={MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    /*public UserController(UserService userService) {
        this.userService = userService;
    }*/

    @PostMapping(value = ApiConstant.Names.SIGNUP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignUpResponseDTO> SignUpHandler(@Validated @RequestBody SignUpRequestDTO signUpRequestDto, HttpServletResponse response) {
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        SignUpResponseDTO user = this.userService.onUserSignUp(signUpRequestDto);
        log.info("User created: {}", user);
        return new ResponseEntity(user, HttpStatus.CREATED);

        /*try {
            SignUpResponseDTO user = this.userService.onUserSignUp(signUpRequestDto);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }*/
    }

    @PostMapping(value = ApiConstant.Names.SIGNIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public SignInResponseDTO SignInHandler(@Validated @RequestBody SignInRequestDTO signInRequestDTO) {
        return this.userService.onUserSignIn(signInRequestDTO);
    }

}
