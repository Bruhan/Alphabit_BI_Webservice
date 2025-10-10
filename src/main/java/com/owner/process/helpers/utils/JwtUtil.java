package com.owner.process.helpers.utils;

import com.owner.process.persistence.models.CustomerInfo;
import com.owner.process.persistence.models.HrEmpUserInfo;
import com.owner.process.persistence.models.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${spring.jwtutil.tokenValidity}")
    public long JWT_TOKEN_VALIDITY ;

    @Value("${spring.jwtutil.refreshTokenValidity}")
    public long JWT_REFRESH_TOKEN_VALIDITY;

    @Value("${spring.jwtutil.secretKey}")
    private String secretKey;

    public static String plt = "";
    public static String eid = "";
    public static String cid = "";
    public static String uid = "";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public void setOrgDetails(String plantName, String empNoId) {
        JwtUtil.plt = plantName;
        JwtUtil.eid = empNoId;
    }

    public void setOrgDetailsCust(String plantName, String custNo, String userID) {
        JwtUtil.plt = plantName;
        JwtUtil.cid = custNo;
        JwtUtil.uid = userID;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateAccessToken(UserInfo userDetails, Map<String, Object> claims) {
        setOrgDetails(userDetails.getDept(), userDetails.getUserId());
        return doGenerateToken(claims, userDetails.getUserId(), JWT_TOKEN_VALIDITY);
    }

    public String generateAccessToken(String userName, Map<String, Object> claims) {
        return doGenerateToken(claims, userName, JWT_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String userName, Map<String, Object> claims) {
        return doGenerateToken(claims, userName, JWT_REFRESH_TOKEN_VALIDITY);
    }

    public String generateAccessTokenCust(CustomerInfo userDetails, Map<String, Object> claims) {
        setOrgDetailsCust(userDetails.getPlant(), userDetails.getCustNo(),userDetails.getUserId());
        return doGenerateToken(claims, userDetails.getUserId(), JWT_TOKEN_VALIDITY);
    }

    public String generateAccessTokenCust(String userName, Map<String, Object> claims) {
        return doGenerateToken(claims, userName, JWT_TOKEN_VALIDITY);
    }

    public String generateRefreshTokenCust(String userName, Map<String, Object> claims) {
        return doGenerateToken(claims, userName, JWT_REFRESH_TOKEN_VALIDITY);
    }

    public String generateAccessTokenEmp(HrEmpUserInfo userDetails, Map<String, Object> claims) {
        setOrgDetails(userDetails.getPlant(), userDetails.getUserId());
        return doGenerateToken(claims, userDetails.getUserId(), JWT_TOKEN_VALIDITY);
    }

    public String generateAccessTokenEmp(HrEmpUserInfo userDetails, Map<String, Object> claims,String timezone) {
        setOrgDetails(userDetails.getPlant(), userDetails.getUserId());
        return doGenerateToken(claims, userDetails.getUserId(), JWT_TOKEN_VALIDITY);
    }
    public String generateAccessTokenEmp(String userName, Map<String, Object> claims) {
        return doGenerateToken(claims, userName, JWT_TOKEN_VALIDITY);
    }

    public String generateRefreshTokenEmp(String userName, Map<String, Object> claims) {
        return doGenerateToken(claims, userName, JWT_REFRESH_TOKEN_VALIDITY);
    }
    private String doGenerateToken(Map<String, Object> claims, String subject, Long token) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + token * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}