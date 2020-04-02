package cc.nishant.authenticator.service;

import cc.nishant.authenticator.entity.User;
import cc.nishant.authenticator.model.UserUpdateRequest;
import cc.nishant.authenticator.repository.UserRepository;

import java.util.Optional;

@org.springframework.stereotype.Service("user-service")
public class UserService extends Service<User, Long> {

    private UserRepository userRepository;


    public UserService(final UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }


    public Optional<User> getByPhoneNumber(final String phone) {
        return Optional.ofNullable(this.userRepository.findByPhone(phone));
    }

    public Optional<User> getById(final Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> editUserById(final Long id, final UserUpdateRequest payload) {
        return Optional.empty();
    }
}
