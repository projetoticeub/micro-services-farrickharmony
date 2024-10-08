package br.com.ferrickharm.agenda.repositories;

import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosListagemProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.models.ProfissionalDeSaude;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfissionalDeSaudeRepository extends JpaRepository<ProfissionalDeSaude, Long> {

    Page<ProfissionalDeSaude> findAllByAtivoTrue(Pageable pageable);

    Optional<ProfissionalDeSaude> findByCpf(String cpf);

    @Query("""
            select p.ativo
            from ProfissionalDeSaude p
            where p.id= :id
            """)
    Boolean findAtivoById(@NotNull Long id);

    Page<ProfissionalDeSaude> findAll(Specification<ProfissionalDeSaude> spec, Pageable pageable);

    @Query("SELECT p FROM ProfissionalDeSaude p WHERE p.nomeCompleto LIKE %:profissionalDeSaudeNome%")
    Optional<ProfissionalDeSaude> findByName(@Param("profissionalDeSaudeNome") String profissionalDeSaudeNome);

}
