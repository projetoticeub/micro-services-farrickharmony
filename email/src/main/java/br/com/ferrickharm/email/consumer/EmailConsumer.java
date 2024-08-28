package br.com.ferrickharm.email.consumer;

import br.com.ferrickharm.email.dtos.EmailDTO;
import br.com.ferrickharm.email.models.Email;
import br.com.ferrickharm.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService service;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDTO emailDTO) {
        System.out.println(emailDTO);
        var email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        service.sendEmail(email);
    }

}
