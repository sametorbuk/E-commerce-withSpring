package com.teknotik.ecommmerce_backend.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        String token = extractToken(request);
        System.out.println("Extracted Token: " + token);


        if (token != null && validateToken(token)) {

            Claims claims = getClaimsFromToken(token);
            System.out.println("Claims: " + claims);


            String username = claims.getSubject();
            System.out.println("Username from token: " + username);


            List<String> roles = (List<String>) claims.get("roles");
            System.out.println("Roles from token: " + roles);


            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());


            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);


            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Authentication set in SecurityContext: " + auth);
        } else {
            System.out.println("Token is invalid or missing");
        }


        chain.doFilter(request, response);
    }


    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7).trim();
        }
        return null;
    }


    private boolean validateToken(String token) {
        System.out.println("Validating token: " + token);
        return true;
    }


    private Claims getClaimsFromToken(String token) {

        System.out.println("Parsing claims from token: " + token);
        token = token.trim();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
