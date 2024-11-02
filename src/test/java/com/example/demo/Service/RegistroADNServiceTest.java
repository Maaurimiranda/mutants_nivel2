package com.example.demo.Service;

import com.example.demo.Entities.RegistroADN;
import com.example.demo.Repository.EstadisticasRepository;
import com.example.demo.Repository.RegistroADNRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsMutant_whenAdnIsMutant() {
        String[] adnMutante = {
                "AAAAAA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        boolean esMutante = registroADNService.isMutant(adnMutante);

        ArgumentCaptor<RegistroADN> captor = ArgumentCaptor.forClass(RegistroADN.class);
        verify(registroADNRepositorio, times(1)).save(captor.capture());

        // Verifica el ADN guardado
        String adnGuardado = String.join(",", adnMutante);
        assertEquals(adnGuardado, captor.getValue().getAdn());
        assertTrue(esMutante, "La secuencia de ADN debería ser mutante");
    }


    @Test
    void testIsMutant_whenAdnIsMutant_verticalPattern() {
        String[] adnMutante = {
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        boolean esMutante = registroADNService.isMutant(adnMutante);

        verify(registroADNRepositorio, times(1)).save(any(RegistroADN.class));
        assertTrue(esMutante, "La secuencia de ADN debería ser mutante");
    }

    @Test
    void testIsMutant_whenAdnIsMutant_diagonalPattern() {
        String[] adnMutante = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCGCTA",
                "TCACTG"
        };

        boolean esMutante = registroADNService.isMutant(adnMutante);

        verify(registroADNRepositorio, times(1)).save(any(RegistroADN.class));
        assertTrue(esMutante, "La secuencia de ADN debería ser mutante");
    }

    @Test
    void testIsMutant_whenAdnIsNotMutant() {
        String[] adnNoMutante = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };

        boolean esMutante = registroADNService.isMutant(adnNoMutante);

        verify(registroADNRepositorio, times(1)).save(any(RegistroADN.class));
        assertFalse(esMutante, "La secuencia de ADN no debería ser mutante");
    }

    @Test
    void testIsMutant_whenAdnInvalid() {
        String[] adnInvalido = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGA"
        };

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registroADNService.isMutant(adnInvalido);
        });

        assertEquals("403 FORBIDDEN \"La matriz de ADN debe ser cuadrada\"", exception.getMessage());
    }

    @Test
    void testSaveErrorHandling() {
        String[] adnMutante = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        doThrow(new RuntimeException("Error al guardar")).when(registroADNRepositorio).save(any(RegistroADN.class));

        assertThrows(RuntimeException.class, () -> registroADNService.isMutant(adnMutante), "Debería lanzar una excepción de error al guardar");
    }
}
