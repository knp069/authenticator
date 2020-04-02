package cc.nishant.authenticator.service;

import cc.nishant.authenticator.entity.UserToken;
import cc.nishant.authenticator.enums.Status;
import cc.nishant.authenticator.repository.UserTokenRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class UserTokenService extends Service<UserToken, Long> {

    final UserTokenRepository userTokenRepository;

    public UserTokenService(final UserTokenRepository userTokenRepository) {
        super(userTokenRepository);
        this.userTokenRepository = userTokenRepository;
    }

    public Optional<UserToken> findByToken(final String token) {
        return Optional.ofNullable(this.userTokenRepository.findByTokenAndStatus(token, Status.ACTIVE));
    }
}
