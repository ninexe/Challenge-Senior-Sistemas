package me.senior.coding.desafio.service;

import me.senior.coding.desafio.model.ReserveModel;

import java.util.List;

public interface ReserveService {
    ReserveModel createReserve(ReserveModel reserve);
    ReserveModel getReserveById(Long id);
    ReserveModel updateReserve(Long id, ReserveModel reserve);
    void deleteReserve(Long id);
    List<ReserveModel> getAllReserves();
    List<ReserveModel> findGuestsCheckin();
    List<ReserveModel> findGuestsCheckout();
}
