package br.com.ferrickharm.agenda.models;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAtualizarConsultaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_de_saude_id")
    @JsonIgnoreProperties
    private ProfissionalDeSaude profissionalDeSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @JsonIgnoreProperties
    private Paciente paciente;

    private LocalDateTime data;
    private String descricao;


    public void atualizarInformacoes(DadosAtualizarConsultaDTO dados) {
        if(dados.data() != null) {
            this.data = dados.data();
        }
    }
}
