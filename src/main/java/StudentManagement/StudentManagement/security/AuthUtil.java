package StudentManagement.StudentManagement.security;

import StudentManagement.StudentManagement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secretkey}")
  private   String secretKey;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //this method is used to create jwt token
    public String generateToken(User user){
    return    Jwts.builder().subject(user.getUsername())
                .claim("userId",user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+  1000*60*10))
                .signWith(getSecretKey())
            .compact();

    }
    //this method is used for extracting the username from the token for validation
    public String extractUserName(String token){
      Claims claims=
              Jwts.parser().verifyWith(getSecretKey())
                      .build().parseSignedClaims(token)
                      .getPayload();

      return claims.getSubject();
    }
}
