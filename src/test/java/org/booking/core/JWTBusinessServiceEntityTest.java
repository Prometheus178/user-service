package org.booking.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.booking.core.service.JWTService;
import org.junit.jupiter.api.Test;

import java.util.Date;

class JWTBusinessServiceEntityTest {
    JWTService jWTService = new JWTService();

    @Test
    void testGenerateToken() {
        String email = "email@email";
        //User userDetails = new User("name", email, Set.of(ADMIN), "password", "salt");
//String result = jWTService.generateToken(userDetails);
//assertNotNull(result);
      //  assertTrue(verifyToken(result, email));
    }


    @Test
    void testRefreshToken() {
        String email = "email@email";
     //   User userDetails = new User("name", email, Set.of(ADMIN), "password", "salt");
       // String token = jWTService.generateToken(userDetails);
        Date expectedIssuedTime = new Date();
   //     String result = jWTService.refreshToken(token, expectedIssuedTime);
     //   assertTrue(verifyToken(result, email));
    }

    public boolean verifyToken(String token, String sub) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(JWTService.SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            // Verify expiration
            Date expiration = claimsJws.getBody().getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                System.out.println("Token has expired");
                return false;
            }
            String subject = claimsJws.getBody().getSubject();
            return sub.equals(subject);
        } catch (Exception e) {
            System.out.println("Token verification failed: " + e.getMessage());
            return false;
        }
    }
}