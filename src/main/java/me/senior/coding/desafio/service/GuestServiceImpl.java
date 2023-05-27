package me.senior.coding.desafio.service;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.repository.GuestRepository;
import me.senior.coding.desafio.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static me.senior.coding.desafio.utils.ReserveUtils.*;

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
        if(guests == null || guests.size() == 0){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Por favor, digite um hospede corretamente!");
        }
        for (GuestModel guest:guests) {
            if (isCpf(guest.getDocument())){
                guest.setDocument(returnCpfMask(guest.getDocument()));
            }else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Por favor, digite um documento válido!");
            }
            if(!isPhone(guest.getPhone())){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Por favor, digite um telefone válido!");
            }
        }
        return guestRepository.saveAll(guests);
    }

    public GuestModel getGuestById(Long id) {
        return guestRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Hospede %s não encontrado!", id)));
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
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Digite um hospede válido!");
    }

    public void deleteGuest(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Digite um hospede válido!");
        }
        getGuestById(id);
        guestRepository.deleteById(id);
    }

    public List<GuestModel> getAllGuests() {
        return guestRepository.findAll();
    }

    public List<GuestModel> findGuestsByName(String name) {
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Digite um nome por favor!");
        }
        List<GuestModel> entity = guestRepository.findByName(name);

        if (entity.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Hospede %s não encontrado!", name));
        }
        return entity;
    }

    public List<GuestModel> findGuestsByDocument(String document) {
        if (document.isEmpty() || !isCpf(document)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Digite um documento válido por favor!");
        }
        String docMask = returnCpfMask(document);
        List<GuestModel> entity = guestRepository.findByDocument(docMask);

        if (entity.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Hospede com documento %s não encontrado!", document));
        }
        return entity;
    }

    public List<GuestModel> findGuestsByPhone(String phone) {
        if (phone.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Digite um telefone por favor!");
        }
        List<GuestModel> entity = guestRepository.findByPhone(phone);

        if (entity.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Hospede com celular %s não encontrado!", phone));
        }
        return entity;
    }

    public BigDecimal getTotalAmountSpent(String document) {
        if (document.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Digite um documento por favor!");
        }
        Optional<BigDecimal> totalAmount = reserveRepository.findTotalAmountByGuest(document);
        return totalAmount.orElse(BigDecimal.ZERO);
    }

    public BigDecimal getLastTotalAmountSpent(String document) {
        if (document.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Digite um documento por favor!");
        }
        List<BigDecimal> totalAmountByGuests = reserveRepository.findLastTotalAmountByGuest(document);
        BigDecimal totalAmountByGuest = totalAmountByGuests.stream().findFirst().orElse(BigDecimal.ZERO);
        return totalAmountByGuest;
    }
}
