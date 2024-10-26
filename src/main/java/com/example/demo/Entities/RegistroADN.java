package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Registro_ADN")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroADN extends Base{

    @Column(length = 1000, nullable = false)
    private String adn;

    @Column(nullable = false)
    private boolean isMutant;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public RegistroADN(String adn, boolean isMutant) {
        this.adn = adn;
        this.isMutant = isMutant;
        this.timestamp = LocalDateTime.now();
    }
}
