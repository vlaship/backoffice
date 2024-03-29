package dev.vlaship.backoffice.security;

import dev.vlaship.backoffice.exception.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final List<String> whiteUrls;

    public JwtRequestFilter(
            UserDetailsService jwtUserDetailsService,
            JwtTokenUtil jwtTokenUtil,
            List<String> whiteUrls
    ) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.whiteUrls = whiteUrls;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
//        var skip = whiteUrls.stream()
//                .anyMatch(url -> request.getServletPath().contains(url));
//
//        if (skip) {
//            chain.doFilter(request, response);
//            return;
//        }

        var jwtToken = extractToken(request);

        try {
            if (jwtToken != null) {
                jwtTokenUtil.validateToken(jwtToken);
                var username = getUsername(jwtToken);
                var userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (JwtAuthenticationException | UsernameNotFoundException ex) {
            log.debug("error validate credentials: {}", ex.getMessage());
        }

        chain.doFilter(request, response);
    }

    @Nullable
    private String getUsername(@NonNull String jwtToken) {
        try {
            return jwtTokenUtil.extractUsername(jwtToken);
        } catch (IllegalArgumentException e) {
            logger.error("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired");
        }
        return null;
    }

    @Nullable
    private String extractToken(@NonNull HttpServletRequest request) {
        var bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
