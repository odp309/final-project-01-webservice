package com.bni.finalproject01webservice.controller.private_api.v1;

import com.bni.finalproject01webservice.interfaces.UserInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/private/user")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class UserController {

    @Autowired
    private UserInterface userService;

    @GetMapping({"/for-user"})
    @PreAuthorize("hasRole('USER')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }
}
