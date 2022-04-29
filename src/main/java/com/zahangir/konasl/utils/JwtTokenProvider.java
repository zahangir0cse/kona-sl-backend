package com.zahangir.konasl.utils;

import com.zahangir.konasl.dto.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    @Value("${jwt.expire.secret}")
    private String secretKey;

    @Value("${jwt.expire.hour}")
    private Long expireHour;

    public String generateToken(Authentication authentication, HttpServletRequest request){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Date issueDate = new Date();
        return Jwts.builder().setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(principal.getId()))
                .claim("username", principal.getUsername())
                .claim("ip", request.getRemoteAddr())
                .claim("roles", principal.getAuthorities())
                .setIssuedAt(issueDate)
                .setExpiration(DateUtils.getExpirationTime(expireHour))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    private Claims getAllClaim(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public Long getUserIdFromToken(String token){
        return Long.valueOf(getAllClaim(token).getSubject());
    }

    private String getLoginRequestIp(String token){
        return (String) getAllClaim(token).get("ip");
    }

    public Boolean isValidToken(String token, HttpServletRequest request){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            String tokenIp = getLoginRequestIp(token);
            return tokenIp.equals(request.getRemoteAddr());
        }catch (Exception e){
            return false;
        }
    }
}
