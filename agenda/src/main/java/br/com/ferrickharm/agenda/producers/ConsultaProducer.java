package br.com.ferrickharm.agenda.producers;

import br.com.ferrickharm.agenda.dtos.email.EmailDTO;
import br.com.ferrickharm.agenda.models.Consulta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        email.setText("Sua consulta foi agendada para: " + consulta.getData());

        rabbitTemplate.convertAndSend(routingKey, email);
    }

}
