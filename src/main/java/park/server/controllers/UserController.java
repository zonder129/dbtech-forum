package park.server.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import park.server.model.Error;
import park.server.model.User;
import park.server.model.UserUpdate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import park.server.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{nickname}/create")
    public ResponseEntity userCreate(@PathVariable("nickname") String nickname,
                                           @RequestBody User profile) {
        try {
            profile.setNickname(nickname);
            userService.createUser(nickname, profile);
        } catch (DuplicateKeyException exception) {
            List<User> usersThatExist = userService.getUserByNicknameOrEmail(nickname, profile.getEmail());
            return ResponseEntity.status(409).body(usersThatExist);
        }
        return ResponseEntity.status(201).body(profile);
    }

    @GetMapping("/{nickname}/profile")
    public ResponseEntity userGetOne(@PathVariable("nickname") String nickname) {
        final User userByRequest;
        try {
            userByRequest = userService.getUserByNickname(nickname);
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userByRequest);
    }

    @PostMapping("/{nickname}/profile")
    public ResponseEntity userUpdate(@PathVariable("nickname") String nickname,
                                           @Valid @RequestBody UserUpdate profile) {
        try {
            User userByRequest = userService.updateUser(nickname, profile);
            return ResponseEntity.status(HttpStatus.OK).body(userByRequest);
        } catch (DuplicateKeyException exception) {
            return ResponseEntity.status(409).body(new Error(exception.getMessage()));
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
    }

}
