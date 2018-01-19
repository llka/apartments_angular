package ru.ilka.apartments.logic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.ilka.apartments.dao.ApartmentRepository;
import ru.ilka.apartments.entity.Apartment;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.exception.LogicException;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"apartments"})
public class ApartmentLogic {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Cacheable(value = "apartments")
    public Apartment findById(int id) throws LogicException {
        Apartment apartment = apartmentRepository.findOne(id);
        if (apartment == null) {
            throw new LogicException("There is no apartment with id = " + id);
        }
        return apartmentRepository.findOne(id);
    }

    @Cacheable(value = "apartments")
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public List<Apartment> findByCostBetween(int minCost, int maxCost) throws LogicException {
        if (maxCost < minCost || maxCost < 0) {
            throw new LogicException("Check cost scopes");
        }
        return apartmentRepository.findByCostBetweenOrderByCost(minCost, maxCost);
    }

    public List<Apartment> findByCostLessThen(int maxCost) throws LogicException {
        if (maxCost < 0) {
            throw new LogicException("Check cost scopes");
        }
        return apartmentRepository.findByCostLessThanEqualOrderByCost(maxCost);
    }

    public List<Apartment> findByCostGreaterThen(int minCost) throws LogicException {
        if (minCost < 0) {
            throw new LogicException("Check cost scopes");
        }
        return apartmentRepository.findByCostGreaterThanEqualOrderByCost(minCost);
    }

    @CacheEvict(value = "apartments", allEntries = true)
    public Apartment save(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    @CacheEvict(value = "apartments", allEntries = true)
    public void deleteAll() {
        apartmentRepository.deleteAll();
    }

    @CacheEvict(value = "apartments", allEntries = true)
    public void delete(int id) throws LogicException {
        try {
            apartmentRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new LogicException("There is no apartment with id = " + id);
        }
    }

    public List<User> getApartmentUsers(int apartmentId) throws LogicException {
        List<User> users = null;
        try {
            Apartment apartment = findById(apartmentId);
            users = new ArrayList<>(apartment.getUsers());
        } catch (LogicException e) {
            throw new LogicException("Can not get apartment users", e);
        }
        return users;
    }

}
