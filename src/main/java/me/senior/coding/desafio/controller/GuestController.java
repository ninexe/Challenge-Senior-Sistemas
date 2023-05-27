package me.senior.coding.desafio.controller;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public List<GuestModel> createGuest(@RequestBody List<GuestModel> guest) {
        return guestService.createGuest(guest);
    }

    @GetMapping("/{id}")
    public GuestModel getGuestById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @GetMapping("/name")
    public List<GuestModel> getGuestsByName(@RequestParam String name) {
        return guestService.findGuestsByName(name);
    }

    @GetMapping("/document")
    public List<GuestModel> getGuestsByDocument(@RequestParam  String document) {
        return guestService.findGuestsByDocument(document);
    }

    @GetMapping("/phone")
    public List<GuestModel> getGuestsByPhone(@RequestParam String phone) {
        return guestService.findGuestsByPhone(phone);
    }

    @PutMapping("/{id}")
    public GuestModel updateGuest(@PathVariable Long id, @RequestBody GuestModel guest) {
        return guestService.updateGuest(id, guest);
    }

    @DeleteMapping("/{id}")
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }
    @GetMapping
    public List<GuestModel> getAllReserves() {
        return guestService.getAllGuests();
    }
    @GetMapping("/total-spent-amount")
    public ResponseEntity<BigDecimal> getTotalAmountSpent(@RequestParam("document") String document) {
        BigDecimal totalAmountSpent = guestService.getTotalAmountSpent(document);
        return ResponseEntity.ok(totalAmountSpent);
    }

    @GetMapping("/value-last-stay")
    public ResponseEntity<BigDecimal> getLastTotalAmountSpent(@RequestParam("document") String document) {
        BigDecimal lastTotalAmountSpent = guestService.getLastTotalAmountSpent(document);
        return ResponseEntity.ok(lastTotalAmountSpent);
    }
}
