package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private/user")
public class UserController {

    @Autowired
    private UserInterface userService;

    @GetMapping({"/for-user"})
    @PreAuthorize("hasRole('USER')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }
}
