package com.example.ecommerce.service;

import com.example.ecommerce.dto.ResponseDto;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpDto;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired

    TokenService authenticationService;
    @Transactional
    public ResponseDto signUp(SignUpDto singUpDto){
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
                singUpDto.getEmail(), encryptedPassword);
        // save the user

        userRepository.save(user);
        // create the token
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveAuthenticationToken(authenticationToken);
        //return response
        ResponseDto responseDto = new ResponseDto("success", "user created succesfully");
        return responseDto;

    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }
    public SignInResponseDto signIn(SignInDto signInDto) {
        // find user by email
        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }
        // hash the password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
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
}