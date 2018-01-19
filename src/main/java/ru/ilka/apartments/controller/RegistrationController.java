package ru.ilka.apartments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.exception.ControllerException;
import ru.ilka.apartments.exception.LogicException;
import ru.ilka.apartments.logic.UserLogic;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private static Logger logger = LogManager.getLogger(RegistrationController.class);
    private static final String MEDIA_TYPE_JSON = "application/json";

    @Autowired
    private UserLogic userLogic;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = MEDIA_TYPE_JSON, produces = MEDIA_TYPE_JSON)
    public void register(@RequestBody User user) {
        logger.debug("registration start");
        userLogic.save(user);
        logger.debug(user);
    }
}
