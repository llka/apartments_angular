package ru.ilka.apartments.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ilka.apartments.entity.Apartment;
import ru.ilka.apartments.entity.User;
import ru.ilka.apartments.exception.LogicException;

import java.util.*;

@Service
public class BookingLogic {
    @Autowired
    private UserLogic userLogic;

    @Autowired
    private ApartmentLogic apartmentLogic;

    public void bookApartments(int userId, Apartment[] bookedApartments) throws LogicException {
        User user = userLogic.findById(userId);
        Set<Apartment> userApartments = user.getApartments();
        userApartments.addAll(new HashSet<>(Arrays.asList(bookedApartments)));
        user.setApartments(userApartments);
        userLogic.save(user);
    }

    public void bookApartment(int userId, Apartment apartment) throws LogicException {
        User user = userLogic.findById(userId);
        Set<Apartment> userApartments = user.getApartments();
        userApartments.add(apartment);
        user.setApartments(userApartments);
        userLogic.save(user);
    }

    public void bookApartment(int userId, int apartmentId) throws LogicException {
        User user = userLogic.findById(userId);
        Apartment apartment = apartmentLogic.findById(apartmentId);
        Set<Apartment> userApartments = user.getApartments();
        userApartments.add(apartment);
        user.setApartments(userApartments);
        userLogic.save(user);
    }

    public void freeApartment(int userId, Apartment apartment) throws LogicException {
        User user = userLogic.findById(userId);
        Set<Apartment> userApartments = user.getApartments();
        userApartments.remove(apartment);
        user.setApartments(userApartments);
        userLogic.save(user);
    }

    public void freeApartment(int userId, int apartmentId) throws LogicException {
        User user = userLogic.findById(userId);
        Apartment apartment = apartmentLogic.findById(apartmentId);
        Set<Apartment> userApartments = user.getApartments();
        userApartments.remove(apartment);
        user.setApartments(userApartments);
        userLogic.save(user);
    }

    public Object[][] getBookingInfo() {
        ArrayList<Apartment> apartments = (ArrayList<Apartment>) apartmentLogic.findAll();
        int length = 0;
        for (int i = 0; i < apartments.size(); i++) {
            length += apartments.get(i).getUsers().size();
        }
        Object[][] bookingInfo = new Object[length][2];
        int k = 0;
        for (int i = 0; i < apartments.size(); i++) {
            Apartment apartment = apartments.get(i);
            ArrayList<User> users = new ArrayList<>(apartment.getUsers());
            for (int j = 0; j < users.size(); j++) {
                bookingInfo[k][0] = apartment;
                bookingInfo[k++][1] = users.get(j);
            }
        }
        return bookingInfo;
    }
}
