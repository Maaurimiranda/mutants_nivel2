package com.example.demo.Service;

import com.example.demo.Entities.Estadisticas;
import com.example.demo.Repository.RegistroADNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadisticasService {

    @Autowired
    private RegistroADNRepository registroADNRepository;

    public Estadisticas getEstadisticas() throws Exception {
        try {
            // Obtener los conteos directamente de la base de datos
            Long countMutants = registroADNRepository.countMutants();
            Long countNonMutants = registroADNRepository.countNonMutants();

            Estadisticas estadisticas = new Estadisticas(countMutants, countNonMutants, 0.0);

            // Calculo del ratio (evitar división por cero)
            double ratio = countNonMutants > 0 ? (double) countMutants / countNonMutants : 0;
            estadisticas.setRatio(ratio);

            return estadisticas;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // No necesitamos updateEstadisticas si contamos directamente desde la base de datos
}
