package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Wishlist;
import com.example.ecommerce.repository.TokenRepository;
import com.example.ecommerce.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepo;

    public void saveAuthenticationToken(AuthenticationToken authenticationToken){
            tokenRepo.save(authenticationToken);
    }
    public AuthenticationToken getAuthenticationToken(User user){
        return tokenRepo.findByUser(user);

    }
    public User getUser(String token){
        final AuthenticationToken authenticationToken = tokenRepo.findByToken(token);
        if(Objects.isNull(authenticationToken)){
            return null;
        }
        return authenticationToken.getUser();
    }
    public void authenticate (String token) throws AuthenticationFailException {
        if(Objects.isNull(token)){
            throw  new AuthenticationFailException("token is not present");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("token is not valid");
        }

    }

}
