package cl.karubag.retiro.repository;

import cl.karubag.retiro.model.Retiro;
import cl.karubag.retiro.model.EstadoRetiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RetiroRepository extends JpaRepository<Retiro, Long> {

    List<Retiro> findByClienteId(Long clienteId);

    List<Retiro> findByRutaId(Long rutaId);

    List<Retiro> findByEstado(EstadoRetiro estado);

    List<Retiro> findByClienteIdAndEstado(Long clienteId, EstadoRetiro estado);
}
