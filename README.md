# retiro-servicio

Microservicio de gestión de retiros para la plataforma Karübag.

## Descripción
Gestiona los retiros de materiales reciclables de los clientes. Permite registrar, completar, marcar como fallido o ausente cada retiro. Se comunica con cliente-servicio para validar que el cliente existe.

## Tecnologías
- Java 21
- Spring Boot 3.5.14
- Spring Data JPA
- PostgreSQL (Neon)
- WebClient (Spring WebFlux)

## Puerto
`8086`

## Base de datos
`karubag_retiro`

## Comunicación con otros servicios
- `cliente-servicio` (:8084) — verifica que el cliente existe

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /api/retiros | Listar todos los retiros |
| GET | /api/retiros/cliente/{clienteId} | Listar por cliente |
| GET | /api/retiros/ruta/{rutaId} | Listar por ruta |
| GET | /api/retiros/estado/{estado} | Listar por estado |
| GET | /api/retiros/{id} | Obtener retiro por ID |
| POST | /api/retiros | Crear retiro |
| PUT | /api/retiros/{id} | Actualizar retiro |
| PUT | /api/retiros/{id}/completar | Marcar como completado |
| PUT | /api/retiros/{id}/fallido | Marcar como fallido |
| PUT | /api/retiros/{id}/ausente | Marcar cliente ausente |
| DELETE | /api/retiros/{id} | Eliminar retiro |

## Estados de retiro
`PENDIENTE`, `COMPLETADO`, `FALLIDO`, `AUSENTE`

## Cómo ejecutar
```bash
./mvnw spring-boot:run
```

## Variables de entorno
```
spring.datasource.url=jdbc:postgresql://<host>/karubag_retiro
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
```