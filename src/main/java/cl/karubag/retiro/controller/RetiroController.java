package cl.karubag.retiro.controller;

import cl.karubag.retiro.dto.RetiroDTO;
import cl.karubag.retiro.model.EstadoRetiro;
import cl.karubag.retiro.service.RetiroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/retiros")
public class RetiroController {

    private final RetiroService retiroService;

    public RetiroController(RetiroService retiroService) {
        this.retiroService = retiroService;
    }

    @GetMapping
    public ResponseEntity<List<RetiroDTO>> listarTodos() {
        return ResponseEntity.ok(retiroService.listarTodos());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<RetiroDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(retiroService.listarPorCliente(clienteId));
    }

    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<List<RetiroDTO>> listarPorRuta(@PathVariable Long rutaId) {
        return ResponseEntity.ok(retiroService.listarPorRuta(rutaId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RetiroDTO>> listarPorEstado(@PathVariable EstadoRetiro estado) {
        return ResponseEntity.ok(retiroService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetiroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(retiroService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RetiroDTO> crear(@Valid @RequestBody RetiroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retiroService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetiroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RetiroDTO dto) {
        return ResponseEntity.ok(retiroService.actualizar(id, dto));
    }

    @PutMapping("/{id}/fallido")
    public ResponseEntity<RetiroDTO> marcarFallido(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        return ResponseEntity.ok(retiroService.marcarFallido(id, observacion));
    }

    @PutMapping("/{id}/ausente")
    public ResponseEntity<RetiroDTO> marcarAusente(@PathVariable Long id, @RequestParam(required = false) String observacion) {
        return ResponseEntity.ok(retiroService.marcarAusente(id, observacion));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<RetiroDTO> completar(@PathVariable Long id) {
        return ResponseEntity.ok(retiroService.completar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        retiroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
