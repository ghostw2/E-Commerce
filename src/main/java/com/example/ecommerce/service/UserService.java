package com.example.ecommerce.service;

import com.example.ecommerce.config.MessageStrings;
import com.example.ecommerce.dto.ResponseDto;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.dto.user.UserCreateDto;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.example.ecommerce.config.MessageStrings.USER_CREATED;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired

    TokenService authenticationService;


    @Transactional
    public ResponseDto signUp(SignUpDto singUpDto) throws CustomException{
        //cheks if user aleready exists
        if(!Objects.isNull(userRepository.findByEmail(singUpDto.getEmail()))){
            throw new CustomException("user already exists.");
        }
        //hash password
        String encryptedPassword = singUpDto.getPassword();

        try {
            encryptedPassword = hashPassword(singUpDto.getPassword());
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        User user = new User(singUpDto.getFirstName(),singUpDto.getLastName(),
                singUpDto.getEmail(),Role.user ,encryptedPassword);
        User createdUser;
        try {
            // save the user

            userRepository.save(user);
            // create the token
            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveAuthenticationToken(authenticationToken);
            // success in creating
            return new ResponseDto(USER_CREATED, USER_CREATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }

    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }
    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {
        // find user by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not present");
        }
        // hash the password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // compare the password in DB

        // if password match

        AuthenticationToken token = authenticationService.getAuthenticationToken(user);
        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }

        return new SignInResponseDto("sucess", token.getToken());

        // return response
    }

    public ResponseDto createUser(String token, UserCreateDto userCreateDto) throws CustomException, AuthenticationFailException {
        User creatingUser = authenticationService.getUser(token);
        if (!canCrudUser(creatingUser.getRole())) {
            // user can't create new user
            throw  new AuthenticationFailException(MessageStrings.USER_NOT_PERMITTED);
        }
        String encryptedPassword = userCreateDto.getPassword();
        try {
            encryptedPassword = hashPassword(userCreateDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        User user = new User(userCreateDto.getFirstName(), userCreateDto.getLastName(), userCreateDto.getEmail(), userCreateDto.getRole(), encryptedPassword );
        User createdUser;
        try {
            createdUser = userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            authenticationService.saveAuthenticationToken(authenticationToken);
            return new ResponseDto(USER_CREATED, USER_CREATED);
        } catch (Exception e) {
            // handle user creation fail error
            throw new CustomException(e.getMessage());
        }

    }

    boolean canCrudUser(Role role) {
        if (role == Role.admin || role == Role.manager) {
            return true;
        }
        return false;
    }

    boolean canCrudUser(User userUpdating, Long userIdBeingUpdated) {
        Role role = userUpdating.getRole();
        // admin and manager can crud any user
        if (role == Role.admin || role == Role.manager) {
            return true;
        }
        // user can update his own record, but not his role
        if (role == Role.user && userUpdating.getId() == userIdBeingUpdated) {
            return true;
        }
        return false;
    }
}