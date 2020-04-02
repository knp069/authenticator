package cc.nishant.authenticator.controller;

import cc.nishant.authenticator.entity.User;
import cc.nishant.authenticator.model.ApiRequest;
import cc.nishant.authenticator.model.ApiResponse;
import cc.nishant.authenticator.model.UserUpdateRequest;
import cc.nishant.authenticator.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @GetMapping("{id}")
    public ApiResponse<User> getUserByUserId(@PathVariable("id") final Long id) {
        final Optional<User> user = this.userService.getById(id);
        return user.map(ApiResponse::new).orElseGet(ApiResponse::new);
    }

    @PostMapping("{id}")
    public ApiResponse<User> editUserByUserId(@PathVariable("id") final Long id, @RequestBody final ApiRequest<UserUpdateRequest> request) {
        final Optional<User> user = this.userService.editUserById(id, request.getPayload());
        return user.map(ApiResponse::new).orElseGet(ApiResponse::new);
    }
}
