package org.example.authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.NTTInternalServerException;
import org.example.util.JsonMapper;

import java.security.Key;
import java.util.Base64;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Getter
@NoArgsConstructor
public class JwtService {

    public static final String JWT_PARSING_ERROR_MESSAGE = "Invalid JWT Token";
    private static final String SECRET_KEY = "2357641e7546f9f8f0b973f6b093788e663649bbd533bfd25e3db196255f23b2";
    private static final long EXPIRATION_TIME = 180_000; // 30 minutes

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(SECRET_KEY))
                .compact();
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static String getEmailFromJwt(String token) {
        String email;
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        try {
            String payload = new String(decoder.decode(chunks[1]));
            Map<String, String> payloadItems = JsonMapper.fromJson(payload);
            email = payloadItems.get("sub");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new NTTInternalServerException(JWT_PARSING_ERROR_MESSAGE);
        }
        return email;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsFunction) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsFunction.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}