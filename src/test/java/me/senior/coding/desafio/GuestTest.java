package me.senior.coding.desafio;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.repository.GuestRepository;
import me.senior.coding.desafio.repository.ReserveRepository;
import me.senior.coding.desafio.service.GuestService;
import me.senior.coding.desafio.service.GuestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GuestTest {
    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestService guestService = new GuestServiceImpl(guestRepository,reserveRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createGuest_WithValidGuest_ShouldNotThrowException() {
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();
        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        Assertions.assertDoesNotThrow(() -> {
            guestService.createGuest(guests);
        });

        // Delete Guest
        guestService.deleteGuest(guests.get(0).getId());
    }

    @Test
    void createGuest_WithNullGuest_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            guestService.createGuest(null);
        });
    }

    @Test
    void updateGuest_WithValidGuest_ShouldNotThrowException() {
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();
        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);
        guestService.createGuest(guests);
        // Update Guest
        guest.setName("Fer");
        Assertions.assertDoesNotThrow(() -> {
            guestService.updateGuest(guests.get(0).getId(), guest);
        });

        // Delete Guest
        guestService.deleteGuest(guests.get(0).getId());
    }

    @Test
    void updateGuest_WithNullGuest_ShouldThrowIllegalArgumentException() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            guestService.updateGuest(-1L,null);
        });
    }

    @Test
    void updateGuest_WithInvalidId_ShouldThrowIllegalArgumentException() {
        GuestModel guest = null;
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            guestService.updateGuest(1L, guest);
        });
    }

    @Test
    void deleteGuest_WithValidId_ShouldNotThrowException() {
        List<GuestModel> guests = new ArrayList<>();
        GuestModel guest = new GuestModel();
        // Set up Guest
        guest.setName("Fernanda");
        guest.setPhone("47992621335");
        guest.setDocument("24605562338");
        guests.add(guest);

        guestService.createGuest(guests);

        Assertions.assertDoesNotThrow(() -> {
            guestService.deleteGuest(guests.get(0).getId());
        });
    }

    @Test
    void deleteGuest_WithInvalidId_ShouldThrowIllegalArgumentException() {
        Long id = null;

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            guestService.deleteGuest(id);
        });
    }
}
