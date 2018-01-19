package ru.ilka.apartments.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ilka.apartments.dao.UserRepository;
import ru.ilka.apartments.entity.Apartment;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.exception.LogicException;

import java.util.ArrayList;
import java.util.List;


@Service
@CacheConfig(cacheNames = {"users"})
public class UserLogic {

    private static Logger logger = LogManager.getLogger(UserLogic.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Cacheable(value = "users")
    public User findById(int id) throws LogicException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new LogicException("No user with id = " + id);
        }
        return user;
    }

    @Cacheable(value = "users")
    public User findByLogin(String login) throws LogicException {
        User user = userRepository.findByLoginIgnoreCase(login);
        if (user == null) {
            throw new LogicException("No user with login = " + login);
        }
        return user;
    }

    @Cacheable(value = "users")
    public List<User> findAll() {
        logger.debug("findAll users");
        return userRepository.findAll();
    }

    public List<User> findAllNotBanned() {
        return userRepository.findByBanFalse();
    }

    public List<User> findAllBanned() {
        return userRepository.findByBanTrue();
    }

    @CacheEvict(value = "users", allEntries = true)
    public User save(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) throws LogicException {
        try {
            findByLogin(user.getLogin());
        } catch (LogicException e) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
        throw new LogicException("Sorry, login must be unique!");
    }

    @CacheEvict(value = "users", allEntries = true)
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws LogicException {
        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new LogicException("There is no user with id = " + id);
        }
    }

    public List<Apartment> getUserApartments(int userId) throws LogicException {
        List<Apartment> bookedApartments = null;
        try {
            User user = findById(userId);
            bookedApartments = new ArrayList<>(user.getApartments());
        } catch (LogicException e) {
            throw new LogicException("Can not get user apartments", e);
        }
        return bookedApartments;
    }
}
