package ru.ilka.apartments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ilka.apartments.entity.Apartment;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.exception.ControllerException;
import ru.ilka.apartments.exception.LogicException;
import ru.ilka.apartments.logic.ApartmentLogic;

import java.util.List;


@RestController
@RequestMapping("/apartment")
public class ApartmentController {

    private static Logger logger = LogManager.getLogger(ApartmentController.class);
    private static final String MEDIA_TYPE_JSON = "application/json";

    @Autowired
    private ApartmentLogic apartmentLogic;

    @GetMapping(produces = MEDIA_TYPE_JSON)
    public List<Apartment> getAllApartments(@RequestParam(value = "minCost", required = false, defaultValue = "-1") int minCost,
                                            @RequestParam(value = "maxCost", required = false, defaultValue = "-1") int maxCost) throws ControllerException {
        if (minCost < 0 && maxCost < 0) {
            return apartmentLogic.findAll();
        } else if (minCost > 0 && maxCost < 0) {
            try {
                return apartmentLogic.findByCostGreaterThen(minCost);
            } catch (LogicException e) {
                throw new ControllerException(e);
            }
        } else if (minCost < 0 && maxCost > 0) {
            try {
                return apartmentLogic.findByCostLessThen(maxCost);
            } catch (LogicException e) {
                throw new ControllerException(e);
            }
        } else {
            try {
                return apartmentLogic.findByCostBetween(minCost, maxCost);
            } catch (LogicException e) {
                throw new ControllerException(e);
            }
        }
    }

    @DeleteMapping
    public void deleteAllApartments() {
        apartmentLogic.deleteAll();
    }

    @GetMapping(value = "/{id}", produces = MEDIA_TYPE_JSON)
    public Apartment getApartmentById(@PathVariable int id) throws ControllerException {
        try {
            return apartmentLogic.findById(id);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @PostMapping(consumes = MEDIA_TYPE_JSON, produces = MEDIA_TYPE_JSON)
    public Apartment saveApartment(@RequestBody Apartment apartment) {
        return apartmentLogic.save(apartment);
    }

    @PutMapping(consumes = MEDIA_TYPE_JSON, produces = MEDIA_TYPE_JSON)
    public Apartment updateApartment(@RequestBody Apartment apartment) {
        return apartmentLogic.save(apartment);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteApartment(@PathVariable int id) throws ControllerException {
        try {
            apartmentLogic.delete(id);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

    @GetMapping(value = "/{id}/users", produces = MEDIA_TYPE_JSON)
    public List<User> getApartmentUsers(@PathVariable int id) throws ControllerException {
        try {
            return apartmentLogic.getApartmentUsers(id);
        } catch (LogicException e) {
            throw new ControllerException(e);
        }
    }

}
