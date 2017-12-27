package ru.ilka.apartments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.ilka.apartments.entity.Apartment;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.logic.ApartmentLogic;
import ru.ilka.apartments.logic.UserLogic;

import javax.sql.DataSource;
import java.util.ArrayList;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private static Logger logger = LogManager.getLogger(Application.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private ApartmentLogic apartmentLogic;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        logger.info("1111 - " + bCryptPasswordEncoder.encode("1111"));

        logger.debug("users - findAll");
        ArrayList<User> users = (ArrayList<User>) userLogic.findAll();
        users.forEach(user -> logger.info(user));

        logger.debug("apartments - findAll");
        ArrayList<Apartment> apartments = (ArrayList<Apartment>) apartmentLogic.findAll();
        apartments.forEach(apartment -> logger.info(apartment));

        logger.debug("users apartments");
        users.forEach(user -> {
            logger.debug("--- " + user);
            user.getApartments().forEach(apartment -> logger.info(apartment));
        });

        logger.debug("apartments users");
        apartments.forEach(apartment -> {
            logger.debug("--- " + apartment);
            apartment.getUsers().forEach(user -> logger.info(user));
        });

        logger.debug("Done!");
    }
}
