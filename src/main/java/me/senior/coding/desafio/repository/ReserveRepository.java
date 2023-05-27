package me.senior.coding.desafio.repository;

import me.senior.coding.desafio.model.GuestModel;
import me.senior.coding.desafio.model.ReserveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveModel, Long> {

    @Query("SELECT h FROM ReserveModel h WHERE h.checkOutDate IS NOT NULL")
    List<ReserveModel> findGuestsCheckout();

    @Query("SELECT h FROM ReserveModel h WHERE h.checkOutDate IS NULL")
    List<ReserveModel> findGuestsCheckin();

    @Query("SELECT SUM(h.totalAmount) FROM ReserveModel h JOIN h.guests hp WHERE hp.document = :document")
    Optional<BigDecimal> findTotalAmountByGuest(@Param("document") String document);

    @Query("SELECT h.totalAmount FROM ReserveModel h JOIN h.guests hp WHERE hp.document = :document ORDER BY h.id DESC")
    List<BigDecimal> findLastTotalAmountByGuest(@Param("document") String document);

    @Query(value = "SELECT reserve_id FROM guests WHERE guests.id = :id", nativeQuery = true)
    Long findReserveIdForeigKey(@Param("id") Long id);
}
