package me.senior.coding.desafio.service;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.repository.GuestRepository;
import me.senior.coding.desafio.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService{

    private GuestRepository guestRepository;
    private ReserveRepository reserveRepository;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository, ReserveRepository reserveRepository) {
        this.guestRepository = guestRepository;
        this.reserveRepository = reserveRepository;
    }
    public List<GuestModel> createGuest(List<GuestModel> guests) {
        if(guests == null){
            throw new IllegalArgumentException("Por favor, digite um hospede!");
        }
        return guestRepository.saveAll(guests);
    }

    public GuestModel getGuestById(Long id) {
        return guestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hospede não encontrado!"));
    }

    public GuestModel updateGuest(Long id, GuestModel guest) {
        if (!(id <= 0)) {
            GuestModel existingGuest = guestRepository.findById(id).orElse(null);
            if (existingGuest != null) {
                if (guest.getName() != null) {
                    existingGuest.setName(guest.getName());
                }
                if (guest.getDocument() != null) {
                    existingGuest.setDocument(guest.getDocument());
                }
                if (guest.getPhone() != null) {
                    existingGuest.setPhone(guest.getPhone());
                }

                return guestRepository.save(existingGuest);
            }
        }
        throw new IllegalArgumentException("Digite um hospede válido!");
    }

    public void deleteGuest(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Hospede inválido!");
        }
        guestRepository.deleteById(id);
    }

    public List<GuestModel> getAllGuests() {
        return guestRepository.findAll();
    }

    public List<GuestModel> findGuestsByName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Hospede inválido!");
        }
        return guestRepository.findByName(name);
    }

    public List<GuestModel> findGuestsByDocument(String document) {
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Hospede inválido!");
        }
        return guestRepository.findByDocument(document);
    }

    public List<GuestModel> findGuestsByPhone(String phone) {
        if (phone.isEmpty()) {
            throw new IllegalArgumentException("Hospede inválido!");
        }
        return guestRepository.findByPhone(phone);
    }

    public BigDecimal getTotalAmountSpent(String document) {
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Hospede inválido!");
        }
        Optional<BigDecimal> totalAmount = reserveRepository.findTotalAmountByGuest(document);
        return totalAmount.orElse(BigDecimal.ZERO);
    }

    public BigDecimal getLastTotalAmountSpent(String document) {
        if (document.isEmpty()) {
            throw new IllegalArgumentException("Hospede inválido!");
        }
        List<BigDecimal> totalAmountByGuests = reserveRepository.findLastTotalAmountByGuest(document);
        BigDecimal totalAmountByGuest = totalAmountByGuests.stream().findFirst().orElse(BigDecimal.ZERO);
        return totalAmountByGuest;
    }
}
