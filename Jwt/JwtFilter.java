package com.car.dekho.car.dekho.Jwt;

import com.car.dekho.car.dekho.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userServiceimp;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("➡️ Entered JwtFilter for path: " + path);

        if (path.equals("/register") || path.equals("/login") || path.equals("/admin/register")
                || path.equals("/account-verification") || path.equals("/otp-request")
                || path.equals("/reset-password")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            System.out.println("🟢 Raw JWT Token: " + token);
            try {
                username = jwtUtil.extractUsernameFromToken(token);
            } catch (Exception e) {
                System.out.println("❌ Failed to extract username: " + e.getMessage());
            }
        }

        System.out.println("🔹 Extracted username from token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userServiceimp.loadUserByUsername(username);

            if (jwtUtil.isvalid(token)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("✅ Authenticated user: " + username);
                System.out.println("✅ Authorities: " + userDetails.getAuthorities());
            } else {
                System.out.println("❌ Token invalid or expired!");
            }
        }

        filterChain.doFilter(request, response);
    }
}

