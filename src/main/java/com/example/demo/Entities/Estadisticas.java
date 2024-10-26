package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Estadisticas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estadisticas extends Base{

    @Column(nullable = false)
    private Long countMutants;

    @Column(nullable = false)
    private Long countNonMutants;

    // Nuevo campo para almacenar el ratio
    @Column(nullable = false)
    private double ratio;

    // Métodos para incrementar los contadores
    public void incrementMutants() {
        this.countMutants++;
    }

    public void incrementNonMutants() {
        this.countNonMutants++;
    }
}
