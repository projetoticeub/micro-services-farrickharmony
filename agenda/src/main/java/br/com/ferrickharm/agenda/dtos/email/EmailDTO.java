package br.com.ferrickharm.agenda.dtos.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO  {

    private Long idConsulta;
    private Long idPaciente;
    private String emailTo;
    private String subject;
    private String text;


}
