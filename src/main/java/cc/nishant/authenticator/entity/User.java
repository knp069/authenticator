package cc.nishant.authenticator.entity;


import cc.nishant.authenticator.enums.IdentifierType;
import cc.nishant.authenticator.enums.Role;
import cc.nishant.authenticator.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User extends Base {

    private String firstName;

    private String lastName;

    private String identifier;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private IdentifierType identifierType = IdentifierType.AADHAR;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.DEFAULT;

    @JsonIgnore
    private String password;

    private String email;

    private String phone;

    private String secondaryPhone;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

}
