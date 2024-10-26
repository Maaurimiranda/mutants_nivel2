package com.example.demo.Service;

import com.example.demo.Entities.RegistroADN;
import com.example.demo.Repository.EstadisticasRepository;
import com.example.demo.Repository.RegistroADNRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class RegistroADNServiceTest {

    @Mock
    private RegistroADNRepository registroADNRepositorio;

    @Mock
    private EstadisticasRepository estadisticasRepositorio;

    @InjectMocks
    private RegistroADNService registroADNService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testIsMutant_whenAdnIsMutant() {
        // ADN mutante de ejemplo
        String[] adnMutante = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        // Ejecutar el método
        boolean esMutante = registroADNService.isMutant(adnMutante);

        // Verificar que el ADN fue registrado en la base de datos
        verify(registroADNRepositorio, times(1)).save(any(RegistroADN.class));

        // Verificar el resultado
        assertTrue(esMutante, "La secuencia de ADN debería ser mutante");
    }

    @Test
    void testIsMutant_whenAdnIsNotMutant() {
        // ADN no mutante de ejemplo
        String[] adnNoMutante = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };

        // Ejecutar el método
        boolean esMutante = registroADNService.isMutant(adnNoMutante);

        // Verificar que el ADN fue registrado en la base de datos
        verify(registroADNRepositorio, times(1)).save(any(RegistroADN.class));

        // Verificar el resultado
        assertFalse(esMutante, "La secuencia de ADN no debería ser mutante");
    }

    @Test
    void testIsMutant_whenAdnInvalid() {
        // ADN inválido
        String[] adnInvalido = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGA" // Esta fila no tiene la misma longitud
        };

        // Verificar que lanza la excepción correcta
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registroADNService.isMutant(adnInvalido);
        });

        assertEquals("403 FORBIDDEN \"La matriz de ADN debe ser cuadrada\"", exception.getMessage());
    }
}
