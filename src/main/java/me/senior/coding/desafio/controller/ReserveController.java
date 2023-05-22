package me.senior.coding.desafio.controller;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.model.ReserveModel;
import me.senior.coding.desafio.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @PostMapping
    public ReserveModel createReserve(@RequestBody ReserveModel reserve) {
        return reserveService.createReserve(reserve);
    }

    @GetMapping("/{id}")
    public ReserveModel getReserveById(@PathVariable Long id) {
        return reserveService.getReserveById(id);
    }

    @PutMapping("/{id}")
    public ReserveModel updateReserve(@PathVariable Long id, @RequestBody ReserveModel reserve) {
        return reserveService.updateReserve(id, reserve);
    }

    @DeleteMapping("/{id}")
    public void deleteReserve(@PathVariable Long id) {
        reserveService.deleteReserve(id);
    }

    @GetMapping
    public List<ReserveModel> getAllReserves() {
        return reserveService.getAllReserves();
    }

    @GetMapping("/list-guest-check-in")
    public List<ReserveModel> findAllGuestCheckIn() {
        return reserveService.findGuestsCheckin();
    }
    @GetMapping("/list-guest-check-out")
    public List<ReserveModel> findAllGuestCheckOut() {
        return reserveService.findGuestsCheckout();
    }
}
