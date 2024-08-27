package br.com.ferrickharm.agenda.models;

import br.com.ferrickharm.agenda.dtos.paciente.DadosAtualizarPacienteDTO;
import br.com.ferrickharm.agenda.dtos.paciente.DadosCadastroPacienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String genero;
    private String cep;
    private String endereco;
    private Boolean ativo;

    public Paciente(DadosCadastroPacienteDTO dados) {
        this.nomeCompleto = dados.nomeCompleto();
        this.cpf = dados.cpf();
        this.dataNascimento = dados.dataNascimento();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.genero = dados.genero();
        this.cep = dados.cep();
        this.endereco = dados.endereco();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizarPacienteDTO dados) {
        if(dados.nomeCompleto() != null) {
            this.nomeCompleto = dados.nomeCompleto();
        }
        if(dados.cpf() != null) {
            this.cpf = dados.cpf();
        }
        if(dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if(dados.email() != null) {
            this.email = dados.email();
        }
        if(dados.genero() != null) {
            this.genero = dados.genero();
        }
        if(dados.cep() != null) {
            this.cep = dados.cep();
        }
        if(dados.endereco() != null) {
            this.endereco = dados.endereco();
        }
    }

    public void deletar() {
        this.ativo = false;
    }
}
