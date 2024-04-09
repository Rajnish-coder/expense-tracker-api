package com.zee.ExpenseTracker.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final long JWT_TOKEN_VALIDITY = 5*60*60;
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        String token =  Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
        return token;
    }

    public String getUsernameFromToken(String jwtToken){
        return getClaimsFromToken(jwtToken,Claims::getSubject);
    }
    private <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(jwtToken);
        if (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken))
            return true;
        else
            return false;
    }

    private boolean isTokenExpired(String jwtToken) {
        final Date expirationDate = getExpirationDateFromToken(jwtToken);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String jwtToken) {
        return getClaimsFromToken(jwtToken,Claims::getExpiration);
    }
}
