package com.example.demo.Controller;

import com.example.demo.Entities.Estadisticas;
import com.example.demo.Service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stats")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    // Metodo para obtener las estadísticas
    @GetMapping("")
    public ResponseEntity<?> getStatistics() {
        try {
            Estadisticas estadisticas = estadisticasService.getEstadisticas();
            return new ResponseEntity<>(estadisticas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}