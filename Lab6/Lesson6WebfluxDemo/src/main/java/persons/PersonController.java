package persons;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;


@RestController
public class PersonController {
	@Autowired
    private PersonRepository personRepository;
	private int x = 10;

    @GetMapping(value="/persons", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> getAllPersons() {
        return personRepository.findByJob("Developer");
    }
    
	@Scheduled(fixedRate = 3000)
	private void savePerson() {
		System.out.println("add person Frank Brown"+x);
		personRepository.save(new Person(x + "", "Frank Brown" + x, "Developer")).block();
		x++;
	}

}
