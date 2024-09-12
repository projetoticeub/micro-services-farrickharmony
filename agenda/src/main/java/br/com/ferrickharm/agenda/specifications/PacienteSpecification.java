package br.com.ferrickharm.agenda.specifications;

import br.com.ferrickharm.agenda.models.Paciente;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PacienteSpecification {

    public static Specification<Paciente> parametros(String nomeCompleto, String cpf,
                                                     LocalDate dataNascimento, String telefone) {
        return (Root<Paciente> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Predicate p = builder.conjunction();

            if (nomeCompleto != null) {
                p = builder.and(p, builder.like(
                        builder.lower(root.get("nomeCompleto")), "%" + nomeCompleto.toLowerCase() + "%"));
            }

            if (cpf != null) {
                p = builder.and(p, builder.equal(root.get("cpf"), cpf));
            }

            if (dataNascimento != null) {
                p = builder.and(p, builder.equal(root.get("dataNascimento"), dataNascimento));
            }

            if (telefone != null) {
                p = builder.and(p, builder.like(root.get("telefone"), "%" + telefone + "%"));
            }

            return p;
        };
    }
}


