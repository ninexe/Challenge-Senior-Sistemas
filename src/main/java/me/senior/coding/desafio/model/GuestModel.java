package me.senior.coding.desafio.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@Data
@Entity
@EntityScan(basePackages = "me.senior.coding.desafio.model")
@Table(name = "guests" )
public class GuestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "document", nullable = false, length = 20)
    private String document;
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

}
