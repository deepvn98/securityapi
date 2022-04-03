package com.takebasic.demospringsecurityapi.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
@Component
public class JwtTokenProvider implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final long serialVersionUID = 234234523523L;
    private static final long jwtExpiration = 86400;

    @Value("${jwt.secret}")
    private String secretKey;

    // Tạo ra jwt từ username qua form login
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration * 1000);
        // Tạo chuỗi json web token từ user tìm được từ form login
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // validation token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid format Token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty --> Message {}", e);
        }
        return false;
    }

    // Lấy username từ token đã có.
    public String getUerNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

}
