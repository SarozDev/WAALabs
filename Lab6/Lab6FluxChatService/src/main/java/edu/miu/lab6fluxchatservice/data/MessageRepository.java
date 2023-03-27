package edu.miu.lab6fluxchatservice.data;

import edu.miu.lab6fluxchatservice.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, Integer> {

    @Tailable
    Flux<Message> findByTextNotNull();

    @Tailable
    Flux<Message> findByRoom(String room);
}
