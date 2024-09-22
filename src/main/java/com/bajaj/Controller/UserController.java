package com.bajaj.Controller;

import com.bajaj.Model.UserRequest;
import com.bajaj.Model.UserResponse;
import com.bajaj.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse processData(@RequestBody UserRequest userRequest) {
        return userService.processUserRequest(userRequest);
    }

    @GetMapping
    public String getOperationCode() {
        return "1";
    }
}