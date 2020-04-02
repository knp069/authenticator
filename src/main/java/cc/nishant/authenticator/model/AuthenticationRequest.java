package cc.nishant.authenticator.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class AuthenticationRequest {

    @NotNull
    private String phone;

    @NotNull
    private String password;
}
