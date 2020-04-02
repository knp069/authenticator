package cc.nishant.authenticator.entity;

import cc.nishant.authenticator.enums.Status;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserToken extends Base {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String token;

    @Enumerated(EnumType.STRING)
    private Status status;

}
