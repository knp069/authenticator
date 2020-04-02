package cc.nishant.authenticator.controller;

import cc.nishant.authenticator.model.*;
import cc.nishant.authenticator.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("api/auth/users")
public class AuthController {

    private AuthService authService;

    @PostMapping("login")
    public @ResponseBody
    ApiResponse<AuthenticationResponse> login(@RequestBody @Valid final ApiRequest<AuthenticationRequest> request,
                                              @RequestHeader final HttpHeaders headers) {
        return new ApiResponse<>(this.authService.login(request.getPayload(), headers));
    }

    @PostMapping("register")
    public @ResponseBody
    ApiResponse<AuthenticationResponse> register(@RequestBody final ApiRequest<RegisterRequest> request) {
        return new ApiResponse<>(this.authService.register(request.getPayload()));
    }

    @GetMapping("logout")
    public @ResponseBody
    ApiResponse<Boolean> logout(@RequestHeader final HttpHeaders headers) {
        return new ApiResponse<>(this.authService.logout(headers));
    }

    @GetMapping("authenticate")
    public @ResponseBody
    ApiResponse<AuthenticationResponse> authenticate(@RequestHeader final HttpHeaders headers) {
        return new ApiResponse<>(this.authService.authenticate(headers));
    }

    @GetMapping("generate-otp/{phone}")
    public @ResponseBody
    ApiResponse<OtpResponse> generateOtp(@PathVariable("phone") final String phone) {
        return new ApiResponse<>(this.authService.generateOtp(phone));
    }

    @PostMapping("validate-otp")
    public @ResponseBody
    ApiResponse<Boolean> validateOtp(@RequestBody final ApiRequest<OtpValidationRequest> request) {
        return new ApiResponse<>(this.authService.validateOtp(request.getPayload()));
    }


}
