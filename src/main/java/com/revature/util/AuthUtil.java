package com.revature.util;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.revature.entities.User;
import com.revature.models.Creds;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthUtil {
	
	public static final String SECRET = System.getenv("SECRET");
	
	public static String generateToken(User user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        long expiresIn = System.currentTimeMillis() + 3600000;
        Date expirationDate = new Date(expiresIn);

        // Sign token with API Key secret
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());

        // Build token and serialize it to a compact, URL-safe string
        String jwt = Jwts.builder().setIssuedAt(issuedAt).setSubject(user.getEmail()).setExpiration(expirationDate)
                .signWith(sa, signingKey).claim("role", user.getRole().getRoleName()).claim("id", user.getId()).compact();
        System.out.println(jwt);
        return jwt;
    }
	
	public static Creds parseJWT(String jwt) {
		Jws<Claims> jws;
		Creds cred = new Creds();
		
		SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, sa.getJcaName());
		
		try {
			jws = Jwts.parser()
					.setSigningKey(signingKey)
					.parseClaimsJws(jwt);
			cred.setEmail(jws.getBody().getSubject());
			cred.setRole(jws.getBody().get("role").toString());
			cred.setId(Integer.parseInt(jws.getBody().get("id").toString()));
			// we can safely trust the JWT
		} catch (JwtException ex) {
			return null;
		}
		return cred;
	}

}
