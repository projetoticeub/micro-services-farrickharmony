package br.com.ferrickharm.email.dtos;

public record EmailDTO(Long idConsulta,
                       Long idPaciente,
                       String emailTo,
                       String subject,
                       String text) {
}
