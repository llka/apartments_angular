package ru.ilka.apartments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static Logger logger = LogManager.getLogger(LoginController.class);

    private static final String BANNED_USER_MSG = "Sorry, you are banned...";
    private static final String LOG_OUT_SUCCESS_MSG = "You've been logged out successfully.";
    private static final String LOG_IN_SUCCESS_MSG = " have been logged in successfully.";
    private static final String INVALID_PASS_MSG = "Invalid username or password!";
    private static final String ACCESS_DENIED_MSG = "Access denied, please, sign in";

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public String loginResult(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "access", required = false) String access,
                              @RequestParam(value = "ban", required = false) String ban) {

        String userLogin = "";
        String resultMessage = ACCESS_DENIED_MSG;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userLogin = authentication.getName();
        }

        if (error != null && "true".equals(error)) {
            return INVALID_PASS_MSG;
        }

        if (logout != null && "true".equals(logout)) {
            return LOG_OUT_SUCCESS_MSG;
        }

        if ("allow".equals(access)) {
            return "User " + userLogin + LOG_IN_SUCCESS_MSG;
        }

        if ("denied".equals(access)) {
            return ACCESS_DENIED_MSG;
        }

        if ("true".equals(ban)) {
            return BANNED_USER_MSG;
        }

        return resultMessage;
    }
}
