package com.example.demo.Service;

import com.example.demo.Entities.Estadisticas;
import com.example.demo.Repository.RegistroADNRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstadisticasServiceTest {

    @InjectMocks
    private EstadisticasService estadisticasService;

    @Mock
    private RegistroADNRepository registroADNRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEstadisticasSuccess() throws Exception {
        // Simular el comportamiento del repositorio
        when(registroADNRepository.countMutants()).thenReturn(10L);
        when(registroADNRepository.countNonMutants()).thenReturn(5L);

        // Llamar al método
        Estadisticas estadisticas = estadisticasService.getEstadisticas();

        // Verificaciones
        assertNotNull(estadisticas);
        assertEquals(10L, estadisticas.getCountMutants());
        assertEquals(5L, estadisticas.getCountNonMutants());
        assertEquals(2.0, estadisticas.getRatio());
    }

    @Test
    void testGetEstadisticasWithZeroNonMutants() throws Exception {
        // Simular el comportamiento del repositorio
        when(registroADNRepository.countMutants()).thenReturn(10L);
        when(registroADNRepository.countNonMutants()).thenReturn(0L);

        // Llamar al método
        Estadisticas estadisticas = estadisticasService.getEstadisticas();

        // Verificaciones
        assertNotNull(estadisticas);
        assertEquals(10L, estadisticas.getCountMutants());
        assertEquals(0L, estadisticas.getCountNonMutants());
        assertEquals(0.0, estadisticas.getRatio()); // Verifica que el ratio es 0
    }

    @Test
    void testGetEstadisticasThrowsException() {
        // Simular que el repositorio lanza una excepción
        when(registroADNRepository.countMutants()).thenThrow(new RuntimeException("Database error"));

        // Llamar al método y verificar que se lanza la excepción
        Exception exception = assertThrows(Exception.class, () -> estadisticasService.getEstadisticas());
        assertEquals("Database error", exception.getMessage());
    }

    // Aquí es donde agregamos los nuevos tests

    @Test
    void testGetEstadisticasWithNegativeValues() throws Exception {
        // Simular el comportamiento del repositorio con un valor negativo
        when(registroADNRepository.countMutants()).thenReturn(-1L);
        when(registroADNRepository.countNonMutants()).thenReturn(5L);

        // Llamar al método
        Estadisticas estadisticas = estadisticasService.getEstadisticas();

        // Verificaciones
        assertNotNull(estadisticas);
        assertEquals(-1L, estadisticas.getCountMutants());
        assertEquals(5L, estadisticas.getCountNonMutants());
        assertEquals(-0.2, estadisticas.getRatio()); // Asegúrate de que la lógica permita esto
    }

    @Test
    void testGetEstadisticasMultipleCalls() throws Exception {
        // Simular el comportamiento del repositorio
        when(registroADNRepository.countMutants()).thenReturn(15L);
        when(registroADNRepository.countNonMutants()).thenReturn(3L);

        // Llamar al método
        Estadisticas estadisticas = estadisticasService.getEstadisticas();

        // Verificaciones
        assertNotNull(estadisticas);
        assertEquals(15L, estadisticas.getCountMutants());
        assertEquals(3L, estadisticas.getCountNonMutants());
        assertEquals(5.0, estadisticas.getRatio());

        // Verificar que los métodos del repositorio se llamaron una vez
        verify(registroADNRepository, times(1)).countMutants();
        verify(registroADNRepository, times(1)).countNonMutants();
    }
}
