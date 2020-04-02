package cc.nishant.authenticator.security;

import cc.nishant.authenticator.entity.User;
import cc.nishant.authenticator.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@Getter
public class UserPrincipal implements UserDetails {

    private Long id;

    private String name;

    private String phone;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private Status status;


    public static UserPrincipal create(final User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() != null) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        }

        return new UserPrincipal(
                user.getId(),
                user.getFirstName().concat(" ").concat(user.getLastName()),
                user.getPhone(),
                user.getPassword(),
                authorities,
                user.getStatus());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == Status.ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == Status.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == Status.ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return this.status == Status.ACTIVE;
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
