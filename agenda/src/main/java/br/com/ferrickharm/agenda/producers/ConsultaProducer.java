package br.com.ferrickharm.agenda.producers;

import br.com.ferrickharm.agenda.dtos.email.EmailDTO;
import br.com.ferrickharm.agenda.models.Consulta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ConsultaProducer {

    final RabbitTemplate rabbitTemplate;

    public ConsultaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value= "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(Consulta consulta) {
        var email = new EmailDTO();
        email.setIdConsulta(consulta.getId());
        email.setIdPaciente(consulta.getPaciente().getId());
        email.setEmailTo(consulta.getPaciente().getEmail());
        email.setSubject("Consulta Agendada");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        var data = consulta.getData().format(formatter);

        email.setText( "Prezado(a) paciente, segue abaixo os dados para seu agendamento: \n"
                + "Paciente: " + consulta.getPaciente().getNomeCompleto() + "\n"
                + "Local: Clínica FerrickHarmony QS 01, Lotes 01/17 \n"
                + "Data: " + data + "\n"
                + "Profissional: " + consulta.getProfissionalDeSaude().getNomeCompleto() + "\n"
                + "Lembre-se de chegar com 15 minutos de antecedência. ");

        rabbitTemplate.convertAndSend(routingKey, email);
    }

}

