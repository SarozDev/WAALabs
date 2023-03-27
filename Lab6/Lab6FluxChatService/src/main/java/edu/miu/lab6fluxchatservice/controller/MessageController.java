package edu.miu.lab6fluxchatservice.controller;

import edu.miu.lab6fluxchatservice.data.MessageRepository;
import edu.miu.lab6fluxchatservice.entity.Message;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@RestController
public class MessageController {

    private final MessageRepository repo;

    private int x = 0;

    public MessageController(MessageRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getChatMessages() {
        return repo.findByTextNotNull();
    }

    @GetMapping(value = "/message-room", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getChatMessagesByRoom(@RequestParam("room") String room) {
        return repo.findByRoom(room);
    }

    @Scheduled(fixedRate = 3000)
    public void postMessage() {
        LocalDateTime dateTime = LocalDateTime.now();
        repo.save(new Message(x + "", "message published at " + dateTime, "defaultRoom")).block();
        x++;
    }
}
