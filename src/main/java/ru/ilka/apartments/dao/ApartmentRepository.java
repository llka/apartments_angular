package ru.ilka.apartments.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ilka.apartments.entity.Apartment;

import java.util.List;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {

    @Override
    Apartment findOne(Integer id);

    List<Apartment> findAll();

    List<Apartment> findByCostBetweenOrderByCost(int costLow, int costHigh);

    List<Apartment> findByCostLessThanEqualOrderByCost(int maxCost);

    List<Apartment> findByCostGreaterThanEqualOrderByCost(int minCost);

    @Override
    <S extends Apartment> S save(S s);

    @Override
    void deleteAll();

    @Override
    void delete(Integer id);
}
