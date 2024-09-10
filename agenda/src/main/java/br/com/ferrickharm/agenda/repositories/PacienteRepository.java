package br.com.ferrickharm.agenda.repositories;

import br.com.ferrickharm.agenda.dtos.paciente.DadosListagemPacienteDTO;
import br.com.ferrickharm.agenda.models.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p WHERE p.cpf = :cpf")
    Optional<Paciente> findByCpf(@Param("cpf") String cpf);

    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    Page<Paciente> findAll(Specification<Paciente> spec, Pageable pageable);

    @Query("SELECT p FROM Paciente p WHERE p.nomeCompleto LIKE %:pacienteNome%")
    Optional<Paciente> findByName(@Param("pacienteNome") String pacienteNome);

}
