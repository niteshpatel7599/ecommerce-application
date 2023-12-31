package com.egate.ecommerce.service.imp;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * JWTService we will write all methods that can generate JWT token and check in jwt token.
 */
@Service
public class JWTServiceImpl {

	private String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * 1000 *60 *24))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	/*
	 * Method will extract the claims
	 */
	private <T> T extractClaims(String token, Function<Claims, T> claimsResolvers){
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSiginKey() {
		/*
		 * this method will return a signature key
		 */
		byte[] key = Decoders.BASE64.decode("T8tF39LvJ8mqHZY8VOPl");
		return Keys.hmacShaKeyFor(key);
	}
	
	public Boolean isTokenValid( String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token, Claims::getExpiration).before(new Date());
	}
}
