package me.senior.coding.desafio;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.model.ReserveModel;
import me.senior.coding.desafio.repository.ReserveRepository;
import me.senior.coding.desafio.service.GuestService;
import me.senior.coding.desafio.service.ReserveService;
import me.senior.coding.desafio.service.ReserveServiceImpl;

import nonapi.io.github.classgraph.utils.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReserveTest {
    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private GuestService guestService;

    @Autowired
    private ReserveService reserveService = new ReserveServiceImpl(reserveRepository,guestService);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIn_WithValidHospedagem_ShouldNotThrowException() {
        ReserveModel reserve = new ReserveModel();
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();

        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        // Set up Reserve
        reserve.setGuests(guests);
        reserve.setHasGarage(true);
        reserve.setTotalAmount(new BigDecimal(1100));
        reserve.setCheckInDate(LocalDateTime.now());
        reserve.setCheckOutDate(null);

        Assertions.assertDoesNotThrow(() -> {
            reserveService.createReserve(reserve);
        });

        // Set up Delete
        reserveService.deleteReserve(reserveRepository.findReserveIdForeigKey(guest.getId()));
        guestService.deleteGuest(guest.getId());
    }

    @Test
    void checkIn_WithNullHospedagem_ShouldThrowIllegalArgumentException() {

        ReserveModel reserveModel = null;

        Assertions.assertThrows(NullPointerException.class, () -> {
            reserveService.createReserve(reserveModel);
        });
    }

    @Test
    void checkIn_WithNullCheckInDate_ShouldThrowIllegalArgumentException() {
        ReserveModel reserve = new ReserveModel();
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();

        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        // Set up Reserve
        reserve.setGuests(guests);
        reserve.setHasGarage(true);
        reserve.setTotalAmount(new BigDecimal(1100));
        reserve.setCheckOutDate(null);
        reserve.setCheckInDate(null);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            reserveService.createReserve(reserve);
        });
    }

    @Test
    void checkIn_WithNullGuest_ShouldThrowIllegalArgumentException() {
        ReserveModel reserve = new ReserveModel();
        List<GuestModel> guests = null;
        reserve.setGuests(guests);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            reserveService.createReserve(reserve);
        });
    }
    @Test
    void updateCheckIn_WithNullCheckIn_ShouldThrowIllegalArgumentException() {
        ReserveModel reserve = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            reserveService.updateReserve(1L,reserve);
        });
    }
    @Test
    void updateCheckIn_WithValid_ShouldNotThrowException() {
        ReserveModel reserve = new ReserveModel();
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();

        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        // Set up Reserve
        reserve.setGuests(guests);
        reserve.setHasGarage(true);
        reserve.setTotalAmount(new BigDecimal(1100));
        reserve.setCheckInDate(LocalDateTime.now());
        reserve.setCheckOutDate(null);
        reserveService.createReserve(reserve);
        // Update
        reserve.setHasGarage(false);
        Assertions.assertDoesNotThrow(() -> {
            reserveService.updateReserve(reserveRepository.findReserveIdForeigKey(guest.getId()),reserve);
        });

        //Delete
        reserveService.deleteReserve(reserveRepository.findReserveIdForeigKey(guest.getId()));
        guestService.deleteGuest(guest.getId());
    }

    @Test
    void updateCheckIn_WithValidCalcValue_ShouldNotThrowException() {
        ReserveModel reserve = new ReserveModel();
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();

        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        // Set up Reserve
        reserve.setGuests(guests);
        reserve.setHasGarage(true);
        reserve.setTotalAmount(BigDecimal.ZERO);
        reserve.setCheckInDate(LocalDateTime.of(2023, 5, 1, 9,35,43));
        reserve.setCheckOutDate(null);
        reserveService.createReserve(reserve);
        // Update
        reserve.setCheckOutDate(LocalDateTime.of(2023, 5, 8, 16,35,43));
        ReserveModel reserveCalc = reserveService.updateReserve(reserveRepository.findReserveIdForeigKey(guest.getId()),reserve);

        BigDecimal value = new BigDecimal(1270.00);
        assertEquals(value.setScale(2, RoundingMode.HALF_UP), reserveCalc.getTotalAmount());

        //Delete
        reserveService.deleteReserve(reserveRepository.findReserveIdForeigKey(guest.getId()));
        guestService.deleteGuest(guest.getId());
    }

    @Test
    void deleteCheckIn_WithValidId_ShouldNotThrowException() {
        ReserveModel reserve = new ReserveModel();
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();

        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        // Set up Reserve
        reserve.setGuests(guests);
        reserve.setHasGarage(true);
        reserve.setTotalAmount(new BigDecimal(1100));
        reserve.setCheckInDate(LocalDateTime.now());
        reserve.setCheckOutDate(null);

        reserveService.createReserve(reserve);

        Assertions.assertDoesNotThrow(() -> {
            reserveService.deleteReserve(reserveRepository.findReserveIdForeigKey(guest.getId()));
            guestService.deleteGuest(guest.getId());
        });
    }

    @Test
    void deleteCheckIn_WithInvalidId_ShouldThrowIllegalArgumentException() {
        Long id = null;

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            reserveService.deleteReserve(id);
        });
    }
}
