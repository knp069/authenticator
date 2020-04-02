package cc.nishant.authenticator.entity;

import cc.nishant.authenticator.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Table
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOtp extends Base {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String otp;

    @Enumerated(EnumType.STRING)
    private Status status;


    public boolean isNotExpired(final Instant time, final Long expiry) {
        return time.isBefore(this.getCreatedAt().plus(expiry, ChronoUnit.MILLIS));

    }
}
