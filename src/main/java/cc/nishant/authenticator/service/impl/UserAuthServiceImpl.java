package cc.nishant.authenticator.service.impl;

import cc.nishant.authenticator.entity.User;
import cc.nishant.authenticator.repository.UserRepository;
import cc.nishant.authenticator.security.UserPrincipal;
import cc.nishant.authenticator.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class UserAuthServiceImpl implements UserAuthService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadByUserId(final String phone) {
        final User user = this.userRepository.findByPhone(phone);
        if (user == null) {
            log.error("User Not found with number " + phone);
        } else {
            return UserPrincipal.create(user);
        }
        return null;
    }

    public void create(final User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public UserDetails loadByUserId(final Long id) {
        final User user = this.userRepository.getOne(id);
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(final String phone) throws UsernameNotFoundException {
        return this.loadByUserId(phone);
    }
}