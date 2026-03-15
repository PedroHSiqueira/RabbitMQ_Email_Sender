package dev.siqueira.user.service;

import com.rabbitmq.client.Return;
import dev.siqueira.user.entity.User;
import dev.siqueira.user.producer.UserProducer;
import dev.siqueira.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserProducer userProducer;
    private final UserRepository userRepository;

    public UserService(UserProducer userProducer, UserRepository userRepository) {
        this.userProducer = userProducer;
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(User user) {
        User userSaved = userRepository.save(user);
        userProducer.sendEventAfterCreate(userSaved);
        return userSaved;
    }

    @Transactional
    public void delete(User user) {
        userProducer.sendEventAfterDelete(user);
        userRepository.delete(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
