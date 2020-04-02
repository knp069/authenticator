package cc.nishant.authenticator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T payload;

    private String message;

    private AppError error;

    private Boolean success;

    public ApiResponse(final T payload, final String message) {
        this.payload = payload;
        this.message = message;
        this.success = true;
    }

    public ApiResponse(final T payload) {
        this.payload = payload;
        this.success = true;
    }
}
