package sns.alstagram.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {

            String currentToken = jwtTokenProvider.findTokenFromCookie(request);

            if (currentToken != null && jwtTokenProvider.isValidToken(currentToken)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(currentToken);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            chain.doFilter(request, response);

        } catch (Exception e) {

            Cookie accessInCookie = new Cookie("access", null);
            Cookie refreshInCookie = new Cookie("refresh", null);

            accessInCookie.setMaxAge(0);
            refreshInCookie.setMaxAge(0);

            accessInCookie.setPath("/");
            refreshInCookie.setPath("/");

            response.addCookie(accessInCookie);
            response.addCookie(refreshInCookie);


        }
    }
}
