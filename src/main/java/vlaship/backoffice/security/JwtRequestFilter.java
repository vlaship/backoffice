package vlaship.backoffice.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final HandlerExceptionResolver resolver;

    public JwtRequestFilter(
            UserDetailsService jwtUserDetailsService,
            JwtTokenUtil jwtTokenUtil,
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver
    ) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) {

        var jwtToken = extractToken(request);

        try {
            if (jwtToken != null && jwtTokenUtil.validateToken(jwtToken)) {
                var username = getUsername(jwtToken);

                var userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
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
        var bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
