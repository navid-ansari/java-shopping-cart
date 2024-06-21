package com.shopping_cart.service;

import com.shopping_cart.dto.signup.SignUpRequestDTO;
import com.shopping_cart.dto.signup.SignUpResponseDTO;
import com.shopping_cart.dto.signin.SignInRequestDTO;
import com.shopping_cart.dto.signin.SignInResponseDTO;
import com.shopping_cart.exception.custom.common.InternalServerException;
import com.shopping_cart.exception.custom.user.*;
import com.shopping_cart.model.Login;
import com.shopping_cart.model.User;
import com.shopping_cart.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public SignUpResponseDTO onUserSignUp(SignUpRequestDTO signUpRequestDto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        signUpRequestDto.setPassword(hashedPassword);
        User user = convertDtoToEntity(signUpRequestDto);

        try {
            User userEmailExists = this.userRepository.findUserByEmail(user.getEmail());
            if (userEmailExists != null) { // user email exists
                throw new UserEmailExistsException("Provided email id already exists");
            }
        } catch (UserEmailExistsException e) {
            throw e;
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error");
        }

        try {
            User userMobileNoExists = this.userRepository.findUserByMobileNo(user.getMobileNo());
            if (userMobileNoExists != null) { // user mobile no exists
                throw new UserMobileNoExistsException("Provided mobile no already exists");
            }
        } catch (UserMobileNoExistsException e) {
            throw e;
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error");
        }

        try {
            User response = this.userRepository.save(user);
            if (response != null && response.getId() != null) {
                return convertEntityToDto(response);
            } else {
                throw new AddNewUserException("Failed to add user");
            }
        } catch (AddNewUserException e) {
            throw e;
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal server error");
        }


        /*User userEmailExists = this.userRepository.findUserByEmail(user.getEmail());
        if (userEmailExists != null) { // user email exists
            throw new UserEmailExistsException("Provided email id already exists");
        }*/

        /*User userMobileNoExists = this.userRepository.findUserByMobileNo(user.getMobileNo());
        if (userMobileNoExists != null) { // user mobile no exists
            throw new UserMobileNoExistsException("Provided mobile no already exists");
        }*/

        /*User response = this.userRepository.save(user);
        if (response != null && response.getId() != null) {
            return convertEntityToDto(response);
        } else {
            throw new AddNewUserException("Failed to add user");
        }*/
    }

    public SignInResponseDTO onUserSignIn(SignInRequestDTO signInRequestDTO) {

        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        Login login = new Login();

        boolean isUserNameIsMobileNo = signInRequestDTO.getUsername().matches("[0-9]+");

        User getUserData;

        if (isUserNameIsMobileNo) {
            login.setMobileNo(signInRequestDTO.getUsername());
            //getUserData = this.userRepository.findUserByMobileNo(login.getMobileNo());
            try {
                getUserData = this.userRepository.findUserByMobileNo(login.getMobileNo());
            } catch (Exception e) {
                throw new InternalServerException("Internal server error");
            }
        } else {
            login.setEmail(signInRequestDTO.getUsername());
            //getUserData = this.userRepository.findUserByEmail(login.getEmail());
            try {
                getUserData = this.userRepository.findUserByEmail(login.getEmail());
            } catch (Exception e) {
                throw new InternalServerException("Internal server error");
            }

        }
        
        if (getUserData == null) {
            throw new UserNotFoundException("User not found");
        }

        login.setPassword(signInRequestDTO.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = encoder.matches(login.getPassword(), getUserData.getPassword());

        if (isPasswordMatch) {
            signInResponseDTO.setId(getUserData.getId());
            signInResponseDTO.setMobileNo(getUserData.getMobileNo());
            signInResponseDTO.setEmail(getUserData.getEmail());
            signInResponseDTO.setEmail(getUserData.getEmail());
        } else {
            throw new PasswordNotMatchingException("Password not matching");
        }
        return signInResponseDTO;
    }

    // dto mapper manually
    /*public SignUpResponseDTO convertEntityToDto(User response) {
        SignUpResponseDTO userDtoResponse = new SignUpResponseDTO();
        userDtoResponse.setId(response.getId());
        userDtoResponse.setEmail(response.getEmail());
        userDtoResponse.setMobileNo(response.getMobileNo());
        userDtoResponse.setPassword(response.getPassword());
        return userDtoResponse;
    }

    public User convertDtoToEntity(SignUpRequestDTO signUpRequestDto){
        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setMobileNo(signUpRequestDto.getMobileNo());
        user.setPassword(signUpRequestDto.getPassword());
        return user;
    }*/

    /*sign up dto mapper*/
    // dto using model mapper
    public User convertDtoToEntity(SignUpRequestDTO signUpRequestDto) {
        User user = new User();
        ModelMapper modelMapper = new ModelMapper();
        user = modelMapper.map(signUpRequestDto, User.class);
        return user;
    }

    public SignUpResponseDTO convertEntityToDto(User response) {
        SignUpResponseDTO userDtoResponse = new SignUpResponseDTO();
        ModelMapper modelMapper = new ModelMapper();
        userDtoResponse = modelMapper.map(response, SignUpResponseDTO.class);
        return userDtoResponse;
    }

    /* sign in dto mapper */
    public Login convertLoginDtoToEntity(SignInRequestDTO signInRequestDTO) {
        Login login = new Login();
        ModelMapper modelMapper = new ModelMapper();
        login = modelMapper.map(signInRequestDTO, Login.class);
        return login;
    }

    public SignInResponseDTO convertLoginEntityToDto(User user) {
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        ModelMapper modelMapper = new ModelMapper();
        signInResponseDTO = modelMapper.map(user, SignInResponseDTO.class);
        return signInResponseDTO;
    }
}
