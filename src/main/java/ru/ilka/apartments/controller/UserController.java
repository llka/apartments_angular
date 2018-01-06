package ru.ilka.apartments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ilka.apartments.entity.Apartment;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.exception.ControllerException;
import ru.ilka.apartments.exception.LogicException;
import ru.ilka.apartments.logic.UserLogic;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);
    private static final String MEDIA_TYPE_JSON = "application/json";

    @Autowired
    private UserLogic userLogic;

    @GetMapping(produces = MEDIA_TYPE_JSON)
    public List<User> getAllUsers(@RequestParam(value = "ban", required = false) Boolean ban) {
        if (ban == null) {
            return userLogic.findAll();
        } else if (ban) {
            return userLogic.findAllBanned();
        } else {
            return userLogic.findAllNotBanned();
        }
    }

    @ResponseBody
    @GetMapping(value = "/{id:[0-9]+}", produces = MEDIA_TYPE_JSON)
    public User getUser(@PathVariable int id) throws ControllerException {
        try {
            return userLogic.findById(id);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @GetMapping(value = "/{login:[a-zA-Z]+\\w?}", produces = MEDIA_TYPE_JSON)
    public User getUserByLogin(@PathVariable String login) throws ControllerException {
        try {
            return userLogic.findByLogin(login);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @PostMapping(consumes = MEDIA_TYPE_JSON, produces = MEDIA_TYPE_JSON)
    public User createUser(@RequestBody User user) throws ControllerException {

        try {
            return userLogic.create(user);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @PutMapping(consumes = MEDIA_TYPE_JSON, produces = MEDIA_TYPE_JSON)
    public User updateUser(@RequestBody User user) throws ControllerException {
        try {
            return userLogic.save(user);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable int id) throws ControllerException {
        try {
            userLogic.delete(id);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userLogic.deleteAll();
    }

    @GetMapping(value = "/{id}/apartments", produces = MEDIA_TYPE_JSON)
    public List<Apartment> getUserApartments(@PathVariable int id) throws ControllerException {
        try {
            return userLogic.getUserApartments(id);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }
}
