package org.example.authentication.filters;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication.service.CustomOwnerDetails;
import org.example.authentication.util.JwtService;
import org.example.domain.entity.Owner;
import org.example.exception.OwnerInvalidCredentialsException;
import org.example.service.OwnerService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.example.authentication.util.JwtService.JWT_PARSING_ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final OwnerService ownerService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        Optional<String> jwtToken = getTokenFromHeader(request);

        if (jwtToken.isPresent() && !jwtToken.get().isBlank()) {
            try {
                String email = jwtService.getSubjectFromToken(jwtToken.get());
                Owner owner = ownerService.getOwnerByEmail(email)
                        .orElseThrow(() -> new OwnerInvalidCredentialsException(JWT_PARSING_ERROR_MESSAGE));

                UserDetails userDetails = new CustomOwnerDetails(owner);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, buildAuthorities(owner));

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                log.error("JWT authentication failed: {}", e.getMessage());
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromHeader(HttpServletRequest request) {
        final String header = request.getHeader(AUTH_HEADER_NAME);
        return (header == null || !header.startsWith("Bearer ")) ? Optional.empty() : Optional.of(header.substring(7));
    }

    private List<GrantedAuthority> buildAuthorities(Owner owner) {
        return Collections.singletonList(new SimpleGrantedAuthority(owner.getRole().name()));
    }
}

