package me.senior.coding.desafio;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.repository.GuestRepository;
import me.senior.coding.desafio.repository.ReserveRepository;
import me.senior.coding.desafio.service.GuestService;
import me.senior.coding.desafio.service.GuestServiceImpl;
import me.senior.coding.desafio.service.ReserveService;
import me.senior.coding.desafio.service.ReserveServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GuestTest {
    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService = new GuestServiceImpl(guestRepository,reserveRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createGuest_WithValidGuest_ShouldNotThrowException() {
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();
        // Set up the hospede object
        guest.setId(1L);
        guest.setName("Fernanda");
        guest.setPhone("8877");
        guest.setDocument("775");
        guests.add(guest);

        Assertions.assertDoesNotThrow(() -> {
            guestService.createGuest(guests);
        });
    }

    @Test
    void createGuest_WithNullGuest_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guestService.createGuest(null);
        });
    }

    @Test
    void updateGuest_WithValidGuest_ShouldNotThrowException() {
        GuestModel guest = new GuestModel();
        guest.setName("Fer");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guestService.updateGuest(1L, guest);
        });
    }

    @Test
    void updateGuest_WithNullGuest_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guestService.updateGuest(202L,null);
        });
    }

    @Test
    void updateGuest_WithInvalidId_ShouldThrowIllegalArgumentException() {
        GuestModel guest = new GuestModel();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guestService.updateGuest(202L, guest);
        });
    }

    @Test
    void deleteGuest_WithValidId_ShouldNotThrowException() {
        Long id = 1L;

        Assertions.assertDoesNotThrow(() -> {
            guestService.deleteGuest(id);
        });
    }

    @Test
    void deleteGuest_WithInvalidId_ShouldThrowIllegalArgumentException() {
        Long id = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guestService.deleteGuest(id);
        });
    }
}
