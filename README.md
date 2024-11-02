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
  -- Esta base de datos se levanta en memoria con la siguiente URL [jdbc:h2:mem:testdb](http://localhost:8080/h2-console)
  -- Esta en modo servidor
- JPA + Hibernate (para la persistencia de datos)
- Docker (para despliegue y contenedorización)
- Render (para hospedaje de la API)
- JUnit 5 y Mockito (para pruebas unitarias)
- JPQL (para consultas en la base de datos)
- POSTMAN (para realizar peticiones HTTP)

## Estructura del Proyecto
- entities: Definición de las entidades del modelo de datos.
- services: Lógica de negocio, incluye el servicio de verificación de ADN (RegistroADNService).
- controllers: Controladores de los endpoints /mutant y /stats.
- repositories: Repositorios de datos con consultas JPQL para la persistencia

## Ejecucion del Proyecto 
- La url principal conectada a Render es:
-- https://mutans-nivel3-2.onrender.com
- En la aplicacion Postman se van a poder realizar las 2 siguientes peticiones
  -- POST - https://mutans-nivel3-2.onrender.com/mutant
  -- GET - https://mutans-nivel3-2.onrender.com/stats

## Ejecución de Pruebas
Para ejecutar las pruebas:

Para limpiar y construir el proyecto:

```./gradlew clean build```

Generar un informe de cobertura de código utilizando JaCoCoÑ

```./gradlew test jacocoTestReport```

##Diagramas de la Arquitecuta
Estaran detalladas en la propia entrega en el Campus.
