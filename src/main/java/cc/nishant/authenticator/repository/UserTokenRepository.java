package cc.nishant.authenticator.repository;

import cc.nishant.authenticator.entity.UserToken;
import cc.nishant.authenticator.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findByTokenAndStatus(final String token, final Status status);
}
