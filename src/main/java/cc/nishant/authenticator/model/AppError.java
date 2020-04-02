package cc.nishant.authenticator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private String errorCode;

    private String message;

    private int statusCode;
}
