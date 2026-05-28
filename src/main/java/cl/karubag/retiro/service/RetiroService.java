package cl.karubag.retiro.service;

import cl.karubag.retiro.dto.RetiroDTO;
import cl.karubag.retiro.model.EstadoRetiro;
import cl.karubag.retiro.model.Retiro;
import cl.karubag.retiro.repository.RetiroRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetiroService {

    private final RetiroRepository retiroRepository;

    public RetiroService(RetiroRepository retiroRepository) {
        this.retiroRepository = retiroRepository;
    }

    public List<RetiroDTO> listarTodos() {
        return retiroRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RetiroDTO> listarPorCliente(Long clienteId) {
        return retiroRepository.findByClienteId(clienteId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RetiroDTO> listarPorRuta(Long rutaId) {
        return retiroRepository.findByRutaId(rutaId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RetiroDTO> listarPorEstado(EstadoRetiro estado) {
        return retiroRepository.findByEstado(estado)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RetiroDTO obtenerPorId(Long id) {
        Retiro retiro = retiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retiro no encontrado con id: " + id));
        return toDTO(retiro);
    }

    public RetiroDTO crear(RetiroDTO dto) {
        return toDTO(retiroRepository.save(toEntity(dto)));
    }

    public RetiroDTO actualizar(Long id, RetiroDTO dto) {
        Retiro retiro = retiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retiro no encontrado con id: " + id));
        retiro.setClienteId(dto.getClienteId());
        retiro.setRutaId(dto.getRutaId());
        retiro.setFechaProgramada(dto.getFechaProgramada());
        retiro.setFechaRealizada(dto.getFechaRealizada());
        retiro.setEstado(dto.getEstado());
        retiro.setObservacion(dto.getObservacion());
        return toDTO(retiroRepository.save(retiro));
    }

    public RetiroDTO marcarFallido(Long id, String observacion) {
        Retiro retiro = retiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retiro no encontrado con id: " + id));
        retiro.setEstado(EstadoRetiro.FALLIDO);
        retiro.setObservacion(observacion);
        return toDTO(retiroRepository.save(retiro));
    }

    public RetiroDTO marcarAusente(Long id, String observacion) {
        Retiro retiro = retiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retiro no encontrado con id: " + id));
        retiro.setEstado(EstadoRetiro.AUSENTE);
        retiro.setObservacion(observacion);
        return toDTO(retiroRepository.save(retiro));
    }

    public RetiroDTO completar(Long id) {
        Retiro retiro = retiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retiro no encontrado con id: " + id));
        retiro.setEstado(EstadoRetiro.COMPLETADO);
        retiro.setFechaRealizada(LocalDate.now());
        return toDTO(retiroRepository.save(retiro));
    }

    public void eliminar(Long id) {
        retiroRepository.deleteById(id);
    }

    private RetiroDTO toDTO(Retiro r) {
        RetiroDTO dto = new RetiroDTO();
        dto.setId(r.getId());
        dto.setClienteId(r.getClienteId());
        dto.setRutaId(r.getRutaId());
        dto.setFechaProgramada(r.getFechaProgramada());
        dto.setFechaRealizada(r.getFechaRealizada());
        dto.setEstado(r.getEstado());
        dto.setObservacion(r.getObservacion());
        return dto;
    }

    private Retiro toEntity(RetiroDTO dto) {
        Retiro r = new Retiro();
        r.setClienteId(dto.getClienteId());
        r.setRutaId(dto.getRutaId());
        r.setFechaProgramada(dto.getFechaProgramada());
        r.setFechaRealizada(dto.getFechaRealizada());
        r.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoRetiro.PENDIENTE);
        r.setObservacion(dto.getObservacion());
        return r;
    }
}
