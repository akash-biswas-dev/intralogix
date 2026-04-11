package com.biswasakashdev.nexussphere.users.controller;


import com.biswasakashdev.nexussphere.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
