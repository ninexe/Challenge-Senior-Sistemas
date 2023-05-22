package me.senior.coding.desafio.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EntityScan(basePackages = "me.senior.coding.desafio.model")
@Table(name = "reserves" )
public class ReserveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "reserve_id")
    private List<GuestModel> guests;
    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;
    @Column(name = "check_out_date")
    private LocalDateTime checkOutDate;
    @Column(name = "has_garage", nullable = false)
    private boolean hasGarage;
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
}
