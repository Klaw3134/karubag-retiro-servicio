package cl.karubag.retiro.controller;

import cl.karubag.retiro.dto.RetiroDTO;
import cl.karubag.retiro.model.EstadoRetiro;
import cl.karubag.retiro.service.RetiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Retiros", description = "Gestion de retiros de materiales Karübag")
@RestController
@RequestMapping("/api/retiros")
public class RetiroController {

    private final RetiroService retiroService;

    public RetiroController(RetiroService retiroService) {
        this.retiroService = retiroService;
    }

    @Operation(summary = "Listar todos los retiros", description = "Retorna la lista completa de retiros")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<RetiroDTO>> listarTodos() {
        return ResponseEntity.ok(retiroService.listarTodos());
    }

    @Operation(summary = "Listar por cliente", description = "Retorna retiros de un cliente especifico")
    @ApiResponse(responseCode = "200", description = "Lista de retiros del cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<RetiroDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(retiroService.listarPorCliente(clienteId));
    }

    @Operation(summary = "Listar por ruta", description = "Retorna retiros de una ruta especifica")
    @ApiResponse(responseCode = "200", description = "Lista de retiros por ruta")
    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<List<RetiroDTO>> listarPorRuta(@PathVariable Long rutaId) {
        return ResponseEntity.ok(retiroService.listarPorRuta(rutaId));
    }

    @Operation(summary = "Listar por estado", description = "Retorna retiros filtrados por estado")
    @ApiResponse(responseCode = "200", description = "Lista de retiros por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RetiroDTO>> listarPorEstado(@PathVariable EstadoRetiro estado) {
        return ResponseEntity.ok(retiroService.listarPorEstado(estado));
    }

    @Operation(summary = "Obtener retiro por ID", description = "Busca un retiro por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retiro encontrado"),
        @ApiResponse(responseCode = "404", description = "Retiro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RetiroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(retiroService.obtenerPorId(id));
    }

    @Operation(summary = "Crear retiro", description = "Programa un nuevo retiro verificando cliente via WebClient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Retiro creado exitosamente",
            content = @Content(schema = @Schema(implementation = RetiroDTO.class),
            examples = @ExampleObject(value = "{\"clienteId\": 1, \"fechaProgramada\": \"2026-06-05\", \"estado\": \"PENDIENTE\", \"observacion\": \"Retiro semanal\"}"))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping
    public ResponseEntity<RetiroDTO> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del retiro a programar",
            required = true,
            content = @Content(examples = @ExampleObject(value = "{\"clienteId\": 1, \"fechaProgramada\": \"2026-06-05\", \"estado\": \"PENDIENTE\", \"observacion\": \"Retiro semanal\"}")))
        @Valid @RequestBody RetiroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retiroService.crear(dto));
    }

    @Operation(summary = "Actualizar retiro", description = "Actualiza los datos de un retiro")
    @ApiResponse(responseCode = "200", description = "Retiro actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<RetiroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RetiroDTO dto) {
        return ResponseEntity.ok(retiroService.actualizar(id, dto));
    }

    @Operation(summary = "Completar retiro", description = "Marca el retiro como completado y registra fecha de realizacion")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retiro completado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Retiro no encontrado")
    })
    @PutMapping("/{id}/completar")
    public ResponseEntity<RetiroDTO> completar(@PathVariable Long id) {
        return ResponseEntity.ok(retiroService.completar(id));
    }

    @Operation(summary = "Marcar retiro como fallido", description = "Marca el retiro como fallido")
    @ApiResponse(responseCode = "200", description = "Retiro marcado como fallido")
    @PutMapping("/{id}/fallido")
    public ResponseEntity<RetiroDTO> marcarFallido(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        return ResponseEntity.ok(retiroService.marcarFallido(id, observacion));
    }

    @Operation(summary = "Marcar cliente ausente", description = "Marca el cliente como ausente en el retiro")
    @ApiResponse(responseCode = "200", description = "Cliente marcado como ausente")
    @PutMapping("/{id}/ausente")
    public ResponseEntity<RetiroDTO> marcarAusente(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        return ResponseEntity.ok(retiroService.marcarAusente(id, observacion));
    }

    @Operation(summary = "Eliminar retiro", description = "Elimina un retiro por su ID")
    @ApiResponse(responseCode = "204", description = "Retiro eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        retiroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
