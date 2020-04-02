package cc.nishant.authenticator.service;

import cc.nishant.authenticator.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService extends UserDetailsService {
    UserDetails loadByUserId(final String userId);

    UserDetails loadUserByUsername(final String phone);

    void create(final User user);
}
