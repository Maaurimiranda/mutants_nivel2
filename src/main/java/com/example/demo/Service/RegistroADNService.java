package com.example.demo.Service;

import com.example.demo.Entities.Estadisticas;
import com.example.demo.Entities.RegistroADN;
import com.example.demo.Repository.EstadisticasRepository;
import com.example.demo.Repository.RegistroADNRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class RegistroADNService {
    private static final int SEQUENCE_LENGTH = 4;

    public RegistroADNService(EstadisticasRepository estadisticasRepositorio, RegistroADNRepository registroADNRepositorio) {
        this.estadisticasRepositorio = estadisticasRepositorio;
        this.registroADNRepositorio = registroADNRepositorio;
    }

    @Autowired
    private RegistroADNRepository registroADNRepositorio;

    @Autowired
    private EstadisticasRepository estadisticasRepositorio;

    @Transactional
    public boolean isMutant(String[] adn) {
        // Validar la cadena de ADN
        validateDna(adn);

        // Logica para determinar si es mutante
        boolean esMutante = checkMutant(adn);

        // Guardar el resultado en la base de datos
        registroADNRepositorio.save(new RegistroADN(String.join(",", adn), esMutante));

        // Actualizar las estadisticas
        updateStatistics(esMutante);

        return esMutante;
    }

    // Metodo para validar la cadena de ADN
    private void validateDna(String[] adn) {
        int tamaño = adn.length;

        if (tamaño < 4) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La matriz de ADN debe ser de al menos 4x4");
        }

        for (String fila : adn) {
            if (fila.length() != tamaño) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La matriz de ADN debe ser cuadrada");
            }
            for (char nucleotido : fila.toCharArray()) {
                if (nucleotido != 'A' && nucleotido != 'T' && nucleotido != 'C' && nucleotido != 'G') {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La cadena ingresada contiene caracteres no válidos");
                }
            }
        }
    }

    private boolean checkMutant(String[] adn) {
        int secuenciasEncontradas = 0;

        // Verificar secuencias horizontales
        for (String fila : adn) {
            if (searchHorizontal(fila)) {
                secuenciasEncontradas++;
                if (secuenciasEncontradas > 1) {
                    return true;
                }
            }
        }

        // Verificar secuencias verticales
        if (searchVertical(adn)) {
            secuenciasEncontradas++;
            if (secuenciasEncontradas > 1) {
                return true;
            }
        }

        // Verificar secuencias diagonales
        if (searchDiagonal(adn)) {
            secuenciasEncontradas++;
            if (secuenciasEncontradas > 1) {
                return true;
            }
        }

        return false;
    }

    private boolean searchHorizontal(String fila) {
        int contadorConsecutivo = 1;
        char caracterPrevio = fila.charAt(0);

        for (int i = 1; i < fila.length(); i++) {
            char caracterActual = fila.charAt(i);
            if (caracterActual == caracterPrevio) {
                contadorConsecutivo++;
                if (contadorConsecutivo == SEQUENCE_LENGTH) {
                    return true;
                }
            } else {
                contadorConsecutivo = 1;
            }
            caracterPrevio = caracterActual;
        }
        return false;
    }

    private boolean searchVertical(String[] adn) {
        int tamaño = adn.length;
        for (int col = 0; col < tamaño; col++) {
            int contadorConsecutivo = 1;
            char caracterPrevio = adn[0].charAt(col);

            for (int fila = 1; fila < tamaño; fila++) {
                char caracterActual = adn[fila].charAt(col);
                if (caracterActual == caracterPrevio) {
                    contadorConsecutivo++;
                    if (contadorConsecutivo == SEQUENCE_LENGTH) {
                        return true;
                    }
                } else {
                    contadorConsecutivo = 1;
                }
                caracterPrevio = caracterActual;
            }
        }
        return false;
    }

    private boolean searchDiagonal(String[] adn) {
        int tamaño = adn.length;

        // Diagonal de izquierda a derecha (superior izquierda a inferior derecha)
        for (int fila = 0; fila < tamaño - SEQUENCE_LENGTH + 1; fila++) {
            for (int col = 0; col < tamaño - SEQUENCE_LENGTH + 1; col++) {
                int contadorConsecutivo = 1;
                char caracterPrevio = adn[fila].charAt(col);

                for (int i = 1; i < SEQUENCE_LENGTH; i++) {
                    char caracterActual = adn[fila + i].charAt(col + i);
                    if (caracterActual == caracterPrevio) {
                        contadorConsecutivo++;
                        if (contadorConsecutivo == SEQUENCE_LENGTH) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        // Diagonal de derecha a izquierda (superior derecha a inferior izquierda)
        for (int fila = 0; fila < tamaño - SEQUENCE_LENGTH + 1; fila++) {
            for (int col = SEQUENCE_LENGTH - 1; col < tamaño; col++) {
                int contadorConsecutivo = 1;
                char caracterPrevio = adn[fila].charAt(col);

                for (int i = 1; i < SEQUENCE_LENGTH; i++) {
                    char caracterActual = adn[fila + i].charAt(col - i);
                    if (caracterActual == caracterPrevio) {
                        contadorConsecutivo++;
                        if (contadorConsecutivo == SEQUENCE_LENGTH) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return false;
    }

    private void updateStatistics(boolean esMutante) {
        Optional<Estadisticas> estadisticasOpt = estadisticasRepositorio.findById(1L);
        Estadisticas estadisticas;

        if (estadisticasOpt.isPresent()) {
            estadisticas = estadisticasOpt.get();
        } else {
            estadisticas = new Estadisticas(0L, 0L, 0.0);
        }

        if (esMutante) {
            estadisticas.incrementMutants();
        } else {
            estadisticas.incrementNonMutants();
        }

        estadisticasRepositorio.save(estadisticas);
    }
}
