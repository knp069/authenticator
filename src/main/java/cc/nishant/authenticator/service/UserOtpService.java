package cc.nishant.authenticator.service;

import cc.nishant.authenticator.entity.UserOtp;
import cc.nishant.authenticator.enums.Status;
import cc.nishant.authenticator.repository.UserOtpRepository;

import java.util.Optional;

@org.springframework.stereotype.Service
public class UserOtpService extends Service<UserOtp, Long> {

    private UserOtpRepository userOtpRepository;

    public UserOtpService(final UserOtpRepository userOtpRepository) {
        super(userOtpRepository);
        this.userOtpRepository = userOtpRepository;
    }

    public Optional<UserOtp> findByUserAndOtp(final Long userId, final String otp) {
        return this.userOtpRepository.findByUserIdAndOtpAndStatus(userId, otp, Status.ACTIVE);
    }
}
