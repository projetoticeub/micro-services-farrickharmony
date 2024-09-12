package br.com.ferrickharm.agenda.specifications;

import br.com.ferrickharm.agenda.models.Consulta;
import br.com.ferrickharm.agenda.models.Paciente;
import br.com.ferrickharm.agenda.models.ProfissionalDeSaude;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ConsultaSpecification {

    public static Specification<Consulta> parametros(ProfissionalDeSaude profissionalDeSaude, Paciente paciente, LocalDateTime data) {
        return (Root<Consulta> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate p = builder.conjunction();

            if (profissionalDeSaude != null && profissionalDeSaude.getNomeCompleto() != null) {
                p = builder.and(p, builder.like(
                        builder.lower(root.get("profissionalDeSaude").get("nomeCompleto")),
                        "%" + profissionalDeSaude.getNomeCompleto().toLowerCase() + "%"));
            }
            if (profissionalDeSaude != null && profissionalDeSaude.getCpf() != null) {
                p = builder.and(p, builder.equal(root.get("profissionalDeSaude").get("cpf"), profissionalDeSaude.getCpf()));
            }

            if (paciente != null && paciente.getNomeCompleto() != null) {
                p = builder.and(p, builder.like(
                        builder.lower(root.get("paciente").get("nomeCompleto")),
                        "%" + paciente.getNomeCompleto().toLowerCase() + "%"));
            }

            if (paciente != null && paciente.getCpf() != null) {
                p = builder.and(p, builder.equal(root.get("paciente").get("cpf"), paciente.getCpf()));
            }

            if (data != null) {
                p = builder.and(p, builder.equal(root.get("data"), data));
            }

            return p;
        };
    }
}
