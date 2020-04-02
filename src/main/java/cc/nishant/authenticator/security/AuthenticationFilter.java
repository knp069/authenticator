package cc.nishant.authenticator.security;

import cc.nishant.authenticator.constant.Constants;
import cc.nishant.authenticator.entity.User;
import cc.nishant.authenticator.service.UserAuthService;
import cc.nishant.authenticator.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain) throws ServletException, IOException {
        final String apiKey = httpServletRequest.getHeader(Constants.API_KEY);
        final String platform = httpServletRequest.getHeader(Constants.PLATFORM);
        final String authorization = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        try {
            if (Strings.isNotBlank(authorization)) {
                if (this.tokenManager.validateAccessToken(authorization)) {
                    final Long userId = this.tokenManager.getUserIdFromToken(authorization);
                    final Optional<User> user = this.userService.getById(userId);
                    if (user.isPresent()) {
                        final UserDetails userDetails = this.userAuthService.loadUserByUsername(user.get().getPhone());
                        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (final Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
