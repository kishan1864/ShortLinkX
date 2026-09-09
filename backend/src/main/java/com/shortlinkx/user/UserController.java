package com.shortlinkx.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public String getCurrentUser(
            Authentication authentication
    ) {

        return "Authenticated user: "
                + authentication.getName();
    }
}