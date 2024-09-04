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

            if (profissionalDeSaude != null && profissionalDeSaude.getId() != null) {
                p = builder.and(p, builder.equal(root.get("profissionalDeSaude").get("id"), profissionalDeSaude.getId()));
            }

            if (paciente != null && paciente.getId() != null) {
                p = builder.and(p, builder.equal(root.get("paciente").get("id"), paciente.getId()));
            }

            if (data != null) {
                p = builder.and(p, builder.equal(root.get("data"), data));
            }

            return p;
        };
    }
}
