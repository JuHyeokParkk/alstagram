package sns.alstagram.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sns.alstagram.user.domain.User;
import sns.alstagram.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private Key secretKey;

    @Value("#{new Long('${token.valid-time}')}")
    private long tokenValidTime;

    private final UserService userService;
    @PostConstruct
    protected void initSecretKey() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateJwt(User user) {

        Date issuedDate = new Date();

        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoles());

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(issuedDate)
                .setClaims(claims)
                .setExpiration(new Date(issuedDate.getTime() + tokenValidTime))
                .signWith(secretKey)
                .compact();
    }

    public String findTokenFromCookie(HttpServletRequest request) {

        Cookie[] currentCookies = request.getCookies();

        if(currentCookies == null)
            return null;
        for (int i = 0; i < currentCookies.length; i++) {
            if(currentCookies[i].getName().equals("jwt")) {
                Cookie jwtCookie = currentCookies[i];
                String token = jwtCookie.getValue();

                return token;


            }
        }

        return null;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        User user = userService.loadUserByEmail(this.getEmailFromToken(token));

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String getEmailFromToken(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("email");
    }

    public boolean isValidToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw e;
        }


    }


}
