package cc.nishant.authenticator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class OtpValidationRequest {
    private String phone;

    private Instant time;

    private String otp;
}
