package com.egate.ecommerce.service.imp;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.egate.ecommerce.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

/*
 * JWTService we will write all methods that can generate JWT token and check in jwt token.
 */
@Service
public class JWTServiceImpl implements JWTService {

	private String secret = "42FCB793F48068B76C936E03C90E77D5A079206825788311E6332E1D96F90899\r\n";

	public String generateToken(UserDetails userDetails) {
		Instant expirationTime = Instant.now().plus(Duration.ofDays(7));
		 ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // Use your actual time zone
		    ZonedDateTime localExpirationTime = expirationTime.atZone(zoneId);
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(Date.from(localExpirationTime.toInstant()))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
//	public String createToken(String username) {
//        // Set expiration time to 1 hour from now
//       
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(Date.from(expirationTime))
//                // Add other claims if necessary
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }
//	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() * 15360000))
//				.signWith(getSiginKey(), SignatureAlgorithm.HS256).compact();
//	}

	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	/*
	 * Method will extract the claims
	 */
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
	}

//	private Key getSiginKey() {
//		/*
//		 * This method will return a signature key using a secure random key
//		 */
//		byte[] keyBytes = new byte[32]; // 32 bytes = 256 bits
//
//		try {
//			SecureRandom secureRandom = SecureRandom.getInstanceStrong();
//			secureRandom.nextBytes(keyBytes);
//		} catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException("Failed to generate secure random key.", e);
//		}
//		return Keys.hmacShaKeyFor(keyBytes);
//	}

	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}
}
