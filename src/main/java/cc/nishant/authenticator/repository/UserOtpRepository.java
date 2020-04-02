package cc.nishant.authenticator.repository;

import cc.nishant.authenticator.entity.UserOtp;
import cc.nishant.authenticator.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findByUserIdAndOtpAndStatus(final Long userId, final String otp, Status status);
}
