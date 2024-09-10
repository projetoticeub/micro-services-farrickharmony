package br.com.ferrickharm.agenda.repositories;

import br.com.ferrickharm.agenda.dtos.consulta.DadosListagemConsultaDTO;
import br.com.ferrickharm.agenda.models.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByProfissionalDeSaudeIdAndData(Long id, LocalDateTime data);

    Boolean existsByPacienteIdAndDataBetween(Long id, LocalDateTime startDate, LocalDateTime endDate);

    Page<DadosListagemConsultaDTO> findAll(Specification<Consulta> spec, Pageable pageable);

    @Query("""
    SELECT c
    FROM Consulta c
    WHERE cast(c.data as date) = :data
    """
    )
    Page<Consulta> findByData(@Param("data") LocalDate data, Pageable pageable);

}
