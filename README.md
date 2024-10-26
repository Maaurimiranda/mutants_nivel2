# Mutant Detector API
Este proyecto es una API que permite verificar si una persona es mutante a partir de su secuencia de ADN. La aplicación identifica patrones de ADN específicos y proporciona estadísticas de análisis de ADN mutante y no mutante.

## Características
- Detección de ADN Mutante: Identifica si una persona es mutante según su secuencia de ADN.
- Estadísticas de Verificaciones: Proporciona estadísticas de mutantes y no mutantes.
- Almacenamiento en Base de Datos: Guarda las secuencias de ADN analizadas en la tabla RegistroADN.
- Pruebas Automáticas: Cobertura de pruebas superior al 80% usando JUnit 5 y Mockito.
- Escalabilidad: Diseñado para soportar tráfico de consultas intensivo.

## Tecnologías Utilizadas
- Java 17
- Spring Boot (para REST API)
- H2 (para Base de Datos)
- JPA + Hibernate (para la persistencia de datos)
- Docker (para despliegue y contenedorización)
- Render (para hospedaje de la API)
- JUnit 5 y Mockito (para pruebas unitarias)
- JPQL (para consultas en la base de datos)

## Estructura del Proyecto
- entities: Definición de las entidades del modelo de datos.
- services: Lógica de negocio, incluye el servicio de verificación de ADN (RegistroADNService).
- controllers: Controladores de los endpoints /mutant y /stats.
- repositories: Repositorios de datos con consultas JPQL para la persistencia

## Ejecución de Pruebas
Para ejecutar las pruebas:

```bash
gradlew test

````

##Diagramas de la Arquitecuta
Estaran detalladas en el archivo "Arquitectura"
