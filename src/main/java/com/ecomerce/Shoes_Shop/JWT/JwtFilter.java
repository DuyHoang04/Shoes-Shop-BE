package com.ecomerce.Shoes_Shop.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUntil jwtUntil;

    @Autowired
    private UserDetailsService userDetailsService;

    Claims claims = null;
    String userEmail = null;

    String role = null;

    @Override
    protected void doFilterInternal(
         @NonNull HttpServletRequest request,
         @NonNull HttpServletResponse response,
         @NonNull FilterChain filterChain)
            throws ServletException, IOException {

           final String authHeader = request.getHeader("authorization");
           final String jwt;

           if(authHeader == null || !authHeader.startsWith("Bearer ")) {
               filterChain.doFilter(request, response);
               return;
           }

           jwt = authHeader.substring(7);
           userEmail = jwtUntil.extractUserName(jwt);
           claims = jwtUntil.extractAllClaims(jwt);

           logger.info(userEmail);

           if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
               if(jwtUntil.validateToken(jwt, userDetails)) {
                   List<String> roles = userDetails.getAuthorities().stream()
                           .map(GrantedAuthority::getAuthority)
                           .collect(Collectors.toList());

                   role = roles.toString();

                   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                           userDetails,
                           null,
                           userDetails.getAuthorities()
                   );

                   logger.warn("authToken" + authToken);
                   authToken.setDetails(
                           new WebAuthenticationDetailsSource().buildDetails(request)
                   );
                   logger.warn("authToken" + authToken);

                   SecurityContextHolder.getContext().setAuthentication(authToken);
               }
               filterChain.doFilter(request, response);
           }
    }

    public boolean isAdmin() {
        return role.contains("ADMIN");
    }

    public boolean isUser() {
        return role.contains("USER");
//        return "USER".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {
        return userEmail;
    }
}
