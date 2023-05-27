package me.senior.coding.desafio.repository;

import me.senior.coding.desafio.model.GuestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GuestRepository extends JpaRepository<GuestModel, Long> {
    @Query("SELECT h FROM GuestModel h WHERE LOWER(h.name) LIKE LOWER(concat('%', :name, '%'))")
    List<GuestModel> findByName(@Param("name") String name);

    @Query("SELECT h FROM GuestModel h WHERE LOWER(h.document) = LOWER(:document)")
    List<GuestModel> findByDocument(@Param("document") String document);

    @Query("SELECT h FROM GuestModel h WHERE LOWER(h.phone) = LOWER(:phone)")
    List<GuestModel> findByPhone(@Param("phone") String phone);
}
