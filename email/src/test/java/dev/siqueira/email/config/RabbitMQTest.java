package dev.siqueira.email.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RabbitMQ.class)
class RabbitMQConfigTest {

    @Autowired
    private Queue queue;

    @Autowired
    private JacksonJsonMessageConverter messageConverter;

    @Autowired
    private RabbitMQ config;

    @Test
    @DisplayName("Create Queue Bean with success")
    void queueCreatedSuccessfully() {
        assertNotNull(queue);
        assertEquals("email-queue", queue.getName());
        assertTrue(queue.isDurable());
    }
}