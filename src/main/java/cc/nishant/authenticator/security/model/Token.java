package cc.nishant.authenticator.security.model;

import cc.nishant.authenticator.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Token {
    private String token;

    private Long userId;

    private Instant expiry;

    private Status status;
}
