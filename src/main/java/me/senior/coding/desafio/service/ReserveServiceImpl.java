package me.senior.coding.desafio.service;

import me.senior.coding.desafio.model.ReserveModel;
import me.senior.coding.desafio.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService{

    private ReserveRepository reserveRepository;
    private GuestService guestService;

    @Autowired
    public ReserveServiceImpl(ReserveRepository reserveRepository, GuestService guestService) {
        this.reserveRepository = reserveRepository;
        this.guestService = guestService;
    }

    public ReserveModel createReserve(final ReserveModel reserve) {
        if(reserve != null || !(reserve.getId() <= 0)){
            ReserveModel entity = new ReserveModel();
            if(reserve.getGuests() == null){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Por favor, digite um hospede!");
            }
            if (reserve.getCheckInDate() == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Por favor, digite uma data de entrada!");
            }
            var guests = guestService.createGuest(reserve.getGuests());
            entity.setGuests(guests);
            entity.setCheckInDate(reserve.getCheckInDate());
            entity.setCheckOutDate(reserve.getCheckOutDate());
            entity.setHasGarage(reserve.isHasGarage());
            entity.setTotalAmount(BigDecimal.ZERO);
            if(entity.getCheckOutDate() != null){
                entity.setTotalAmount(calculateTotalAmount(entity.getCheckInDate(),entity.getCheckOutDate()));
            }
            return reserveRepository.save(entity);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Por favor, digite uma reserva corretamente!");
    }

    public ReserveModel getReserveById(Long id) {
        return reserveRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Reserva %s não encontrada!", id)));
    }

    public ReserveModel updateReserve(Long id, ReserveModel reserve) {
        if(reserve != null || !(reserve.getId() <= 0)){
            ReserveModel existingReserve = reserveRepository.findById(id).orElse(null);
            if (existingReserve != null) {
                if(reserve.getGuests() != null){
                    existingReserve.setGuests(reserve.getGuests());
                }
                if (reserve.getCheckInDate() != null) {
                    existingReserve.setCheckInDate(reserve.getCheckInDate());
                }
                if (reserve.getCheckOutDate() != null) {
                    existingReserve.setCheckOutDate(reserve.getCheckOutDate());
                }
                existingReserve.setHasGarage(reserve.isHasGarage());
                existingReserve.setTotalAmount(BigDecimal.ZERO);
                if(existingReserve.getCheckOutDate() != null){
                    existingReserve.setTotalAmount(calculateTotalAmount(existingReserve.getCheckInDate(),existingReserve.getCheckOutDate()));
                }
                return reserveRepository.save(existingReserve);
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Digite uma reserva válida!");
    }

    public void deleteReserve(Long id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Reserva %s não encontrado!", id));
        }
        getReserveById(id);
        reserveRepository.deleteById(id);
    }

    public List<ReserveModel> getAllReserves() {
        return reserveRepository.findAll();
    }

    public List<ReserveModel> findGuestsCheckin() {
        return reserveRepository.findGuestsCheckin();
    }

    public List<ReserveModel> findGuestsCheckout() {
        return reserveRepository.findGuestsCheckout();
    }

    private BigDecimal calculateTotalAmount(LocalDateTime DateEntry, LocalDateTime departureDate) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        LocalDateTime currentDate = DateEntry;

        while (currentDate.isBefore(departureDate)) {
            BigDecimal dailyValue;
            if (isWeekend(currentDate)) {
                dailyValue = new BigDecimal("150.00");
            } else {
                dailyValue = new BigDecimal("120.00");
            }

            BigDecimal reserveValue = calculateReserveValue(dailyValue);
            BigDecimal garageValue = calculateGarageValue(currentDate);

            totalAmount = totalAmount.add(reserveValue).add(garageValue);

            currentDate = currentDate.plusDays(1);
        }
        if (departureDate.getHour() >= 16 && departureDate.getMinute() >= 30) {
            BigDecimal dailyValue;
            if (isWeekend(currentDate)) {
                dailyValue = new BigDecimal("150.00");
            } else {
                dailyValue = new BigDecimal("120.00");
            }
            BigDecimal reserveValue = calculateReserveValue(dailyValue);
            totalAmount = totalAmount.add(reserveValue);
        }
        return totalAmount;
    }

    private boolean isWeekend(LocalDateTime data) {
        DayOfWeek dayOfWeek = data.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private BigDecimal calculateReserveValue(BigDecimal dailyValue) {
        BigDecimal reserveValue = BigDecimal.ZERO;
        reserveValue = reserveValue.add(dailyValue);
        return reserveValue;
    }

    private BigDecimal calculateGarageValue(LocalDateTime data) {
        BigDecimal dailyGarageValue;
        if (isWeekend(data)) {
            dailyGarageValue = new BigDecimal("20.00");
        } else {
            dailyGarageValue = new BigDecimal("15.00");
        }

        BigDecimal garageValue = BigDecimal.ZERO;
        garageValue = garageValue.add(dailyGarageValue);

        return garageValue;
    }

}
