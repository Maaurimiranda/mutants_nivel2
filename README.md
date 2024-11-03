# Mutant Detector API
Este proyecto es una API que permite verificar si una persona es mutante a partir de su secuencia de ADN. La aplicación identifica patrones de ADN específicos.

## Características
- Detección de ADN Mutante: Identifica si una persona es mutante según su secuencia de ADN.
- Almacenamiento en Base de Datos: Guarda las secuencias de ADN analizadas en la tabla RegistroADN.
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
- controllers: Controladores de el endpoint /mutant.
- repositories: Repositorios de datos con consultas JPQL para la persistencia

## Ejecucion del Proyecto 
- La url principal conectada a Render es:
-- https://mutants-nivel2-1.onrender.com
- En la aplicacion Postman se van a poder realizar l siguiente peticion
  -- POST - https://mutants-nivel2-1.onrender.com/mutant


