package edu.miu.lab6fluxchatservice;

import ch.qos.logback.core.pattern.color.MagentaCompositeConverter;
import edu.miu.lab6fluxchatservice.entity.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableScheduling
public class Lab6FluxChatServiceApplication {

    @Autowired
    private ReactiveMongoTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(Lab6FluxChatServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            Flux<Message> messageFlux = WebClient.create("localhost:8080/messages")
                    .get()
                    .retrieve()
                    .bodyToFlux(Message.class);

            System.out.println("Get All Messages");
            messageFlux.subscribe(System.out::println);
        };
    }

    @PostConstruct
    public void init(){
        template.dropCollection("message").then(template.createCollection("message",
                CollectionOptions.empty().capped().size(2048).maxDocuments(10000))).block();
    }
}
