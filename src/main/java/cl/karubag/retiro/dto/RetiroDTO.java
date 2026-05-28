package cl.karubag.retiro.dto;

import cl.karubag.retiro.model.EstadoRetiro;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RetiroDTO {

    private Long id;

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    private Long rutaId;

    @NotNull(message = "La fecha programada es obligatoria")
    private LocalDate fechaProgramada;

    private LocalDate fechaRealizada;

    private EstadoRetiro estado;

    private String observacion;

    public RetiroDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getRutaId() { return rutaId; }
    public void setRutaId(Long rutaId) { this.rutaId = rutaId; }
    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(LocalDate fechaProgramada) { this.fechaProgramada = fechaProgramada; }
    public LocalDate getFechaRealizada() { return fechaRealizada; }
    public void setFechaRealizada(LocalDate fechaRealizada) { this.fechaRealizada = fechaRealizada; }
    public EstadoRetiro getEstado() { return estado; }
    public void setEstado(EstadoRetiro estado) { this.estado = estado; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
