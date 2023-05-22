package me.senior.coding.desafio;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.model.ReserveModel;
import me.senior.coding.desafio.repository.GuestRepository;
import me.senior.coding.desafio.repository.ReserveRepository;
import me.senior.coding.desafio.service.GuestService;
import me.senior.coding.desafio.service.ReserveService;
import me.senior.coding.desafio.service.ReserveServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ReserveTest {
    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestService guestService;

    @InjectMocks
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

        guest.setId(1L);
        guest.setName("Fernanda");
        guest.setPhone("8877");
        guest.setDocument("775");
        guests.add(guest);

        reserve.setId(1L);
        reserve.setGuests(guests);
        reserve.setHasGarage(true);
        reserve.setTotalAmount(new BigDecimal(300));
        reserve.setCheckInDate(LocalDateTime.now());
        reserve.setCheckOutDate(null);

        Assertions.assertDoesNotThrow(() -> {
            reserveService.createReserve(reserve);
        });
    }

    @Test
    void checkIn_WithNullHospedagem_ShouldThrowIllegalArgumentException() {

        ReserveModel reserveModel = new ReserveModel();
        reserveModel.setId(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reserveService.createReserve(reserveModel);
        });
    }

    @Test
    void checkIn_WithInvalidHospedeId_ShouldThrowIllegalArgumentException() {
        ReserveModel reserve = new ReserveModel();
        reserve.setId(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reserveService.createReserve(reserve);
        });
    }

    @Test
    void checkIn_WithNullCheckInDate_ShouldThrowIllegalArgumentException() {
        ReserveModel reserve = new ReserveModel();
        reserve.setId(1L);
        reserve.setCheckInDate(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reserveService.createReserve(reserve);
        });
    }
}
