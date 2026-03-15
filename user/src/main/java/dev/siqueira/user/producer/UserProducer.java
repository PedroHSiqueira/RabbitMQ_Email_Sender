package dev.siqueira.user.producer;

import dev.siqueira.user.dtos.EmailDto;
import dev.siqueira.user.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final String routingKey = "email-queue";

    public void sendEventAfterCreate(User user) {
        EmailDto email = new EmailDto(
                user.getUserId(),
                user.getEmail(),
                "Bem vindo ao serviço de mensageria usando RabbitMQ!",
                "Olá " + user.getName() + ", Ficamos muito felizes de ter você conosco para este serviço de mensageria, o mesmo foi desenvolvido para estudos sobre RabbitMQ, Ass: Pedro Siqueira"

        );

        rabbitTemplate.convertAndSend(
                "",
                routingKey,
                email
        );
    }

    public void sendEventAfterDelete(User user) {
        EmailDto email = new EmailDto(
                user.getUserId(),
                user.getEmail(),
                "Remoção de Usuário",
                "O usuário: " + user.getName() + " Foi removido do sistema!");

        rabbitTemplate.convertAndSend(
                "",
                routingKey,
                email
        );
    }
}
