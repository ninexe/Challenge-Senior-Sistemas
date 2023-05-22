package me.senior.coding.desafio.service;

import me.senior.coding.desafio.model.GuestModel;

import java.math.BigDecimal;
import java.util.List;

public interface GuestService {
    List<GuestModel> createGuest(List<GuestModel> guests);
    GuestModel getGuestById(Long id);
    List<GuestModel> findGuestsByName(String name);
    List<GuestModel> findGuestsByDocument(String document);
    List<GuestModel> findGuestsByPhone(String phone);
    GuestModel updateGuest(Long id, GuestModel guest);
    void deleteGuest(Long id);
    List<GuestModel> getAllGuests();
    BigDecimal getTotalAmountSpent(String document);
    BigDecimal getLastTotalAmountSpent(String document);
}
