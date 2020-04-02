package cc.nishant.authenticator.service;

import cc.nishant.authenticator.constant.Constants;
import cc.nishant.authenticator.entity.User;
import cc.nishant.authenticator.entity.UserOtp;
import cc.nishant.authenticator.entity.UserToken;
import cc.nishant.authenticator.enums.Status;
import cc.nishant.authenticator.model.*;
import cc.nishant.authenticator.security.OtpManager;
import cc.nishant.authenticator.security.TokenManager;
import cc.nishant.authenticator.security.UserPrincipal;
import cc.nishant.authenticator.security.model.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Value("${otp.expiry}")
    private Long expiry;
    private UserAuthService userAuthService;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private UserTokenService userTokenService;
    private OtpManager otpManager;
    private UserOtpService userOtpService;

    public AuthService(final UserAuthService userAuthService, final UserService userService, final AuthenticationManager authenticationManager, final TokenManager tokenManager, final UserTokenService userTokenService, final OtpManager otpManager, final UserOtpService userOtpService) {
        this.userAuthService = userAuthService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.userTokenService = userTokenService;
        this.otpManager = otpManager;
        this.userOtpService = userOtpService;
    }

    public AuthenticationResponse login(final AuthenticationRequest payload, final HttpHeaders headers) {

        final Authentication authentication = this.authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(payload.getPhone(), payload.getPassword()));

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserPrincipal userPrincipal = (UserPrincipal) this.userAuthService.loadUserByUsername(payload.getPhone());
            final Token token = this.tokenManager.generateToken(userPrincipal.getId());
            return this.loginResponse(userPrincipal, token);
        }
        return AuthenticationResponse.builder().user(null).accessToken("").build();
    }

    public AuthenticationResponse authenticate(final HttpHeaders headers) {
        try {
            final String authorization = headers.getFirst(Constants.AUTHORIZATION);
            if (Strings.isNotBlank(authorization) && this.validateAccessToken(authorization)) {
                final Long userId = this.tokenManager.getUserIdFromToken(authorization);
                final Optional<User> user = this.userService.getById(userId);
                if (user.isPresent() && userId != null) {
                    final UserPrincipal userPrincipal = (UserPrincipal) this.userAuthService.loadUserByUsername(user.get().getPhone());
                    return this.loginResponse(userPrincipal, Token.builder().token(authorization).build());
                }
                return AuthenticationResponse.builder().user(null).accessToken("").build();
            }
        } catch (final Exception ex) {
            return AuthenticationResponse.builder().user(null).accessToken("").build();
        }
        return AuthenticationResponse.builder().user(null).accessToken("").build();
    }

    private AuthenticationResponse loginResponse(final UserPrincipal userPrincipal, final Token token) {
        final Optional<User> user = this.userService.getById(userPrincipal.getId());
        final String accessToken = token.getToken();
        if (user.isPresent()) {
            final Optional<UserToken> userToken = this.userTokenService.findByToken(accessToken);
            if (!userToken.isPresent())
                this.userTokenService.create(UserToken.builder().user(user.get()).status(Status.ACTIVE).token(accessToken).build());
            return AuthenticationResponse.builder().user(user.get()).accessToken(accessToken).build();
        }
        return AuthenticationResponse.builder().user(null).accessToken("").build();
    }

    public AuthenticationResponse register(final RegisterRequest payload) {

        final String phone = payload.getPhone();
        final Optional<User> user = this.userService.getByPhoneNumber(phone);
        if (user.isPresent()) {
            log.error("User Already present");
            return null;
        }
        final User userObj = User.builder().
                phone(payload.getPhone()).
                password(payload.getPassword()).
                firstName(payload.getFirstName()).
                lastName(payload.getLastName()).
                role(payload.getRole()).
                build();
        this.userAuthService.create(userObj);
        final UserPrincipal userPrincipal = (UserPrincipal) this.userAuthService.loadUserByUsername(payload.getPhone());
        final Token token = this.tokenManager.generateToken(userPrincipal.getId());
        return this.loginResponse(userPrincipal, token);
    }

    public Boolean logout(final HttpHeaders headers) {
        final String authorization = headers.getFirst(Constants.AUTHORIZATION);

        if (Strings.isNotBlank(authorization)) {
            final Optional<UserToken> userToken = this.userTokenService.findByToken(authorization);
            if (userToken.isPresent()) {
                userToken.get().setStatus(Status.INACTIVE);
                this.userTokenService.update(userToken.get());
                return true;
            }
        }
        return false;
    }

    public boolean validateAccessToken(final String token) {
        final Optional<UserToken> userToken = this.userTokenService.findByToken(token);
        final boolean isValid = this.tokenManager.validateAccessToken(token);
        return userToken.isPresent() && userToken.get().getStatus() == Status.ACTIVE && isValid;

    }

    public OtpResponse generateOtp(final String phone) {
        final String otp = this.otpManager.generateOtp();
        final Optional<User> user = this.userService.getByPhoneNumber(phone);
        if (user.isPresent()) {
            this.userOtpService.create(UserOtp.builder().user(user.get()).otp(otp).status(Status.ACTIVE).build());
            return OtpResponse.builder().otp(otp).phone(phone).build();
        }
        return null;
    }

    public Boolean validateOtp(final OtpValidationRequest payload) {
        final Optional<User> user = this.userService.getByPhoneNumber(payload.getPhone());
        if (user.isPresent()) {
            final Optional<UserOtp> userOtp = this.userOtpService.findByUserAndOtp(user.get().getId(), payload.getOtp());
            if (userOtp.isPresent() && userOtp.get().isNotExpired(payload.getTime(), this.expiry)) {
                userOtp.get().setStatus(Status.INACTIVE);
                this.userOtpService.update(userOtp.get());
                return true;
            }
        }
        return false;
    }
}
