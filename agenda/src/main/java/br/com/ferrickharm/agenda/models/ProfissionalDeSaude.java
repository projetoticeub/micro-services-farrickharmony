package br.com.ferrickharm.agenda.models;

import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosAtualizarProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosCadastroProfissionalDeSaudeDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "profissionais_de_saude")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProfissionalDeSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String registro;
    private String genero;
    private String cep;
    private String endereco;
    private boolean ativo;

    public ProfissionalDeSaude(DadosCadastroProfissionalDeSaudeDTO dados) {
        this.nomeCompleto = dados.nomeCompleto();
        this.cpf = dados.cpf();
        this.dataNascimento = dados.dataNascimento();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.registro = dados.registro();
        this.genero = dados.genero();
        this.cep = dados.cep();
        this.endereco = dados.endereco();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizarProfissionalDeSaudeDTO dados) {
        if(dados.nomeCompleto() != null) {
            this.nomeCompleto = dados.nomeCompleto();
        }
        if(dados.cpf() != null) {
            this.cpf = dados.cpf();
        }
        if(dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
        if(dados.email() != null) {
            this.email = dados.email();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if(dados.registro() != null) {
            this.registro = dados.registro();
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
