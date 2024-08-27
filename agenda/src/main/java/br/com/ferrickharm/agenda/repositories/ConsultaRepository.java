package br.com.ferrickharm.agenda.repositories;

import br.com.ferrickharm.agenda.models.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByProfissionalDeSaudeIdAndData(Long id, LocalDateTime data);

    Boolean existsByPacienteIdAndDataBetween(Long id, LocalDateTime startDate, LocalDateTime endDate);
}
