package persons;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {
	@Id 
    private String id;
    private String name;
    private String job;
    

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Person(String id, String name, String job) {
		super();
		this.id = id;
		this.name = name;
		this.job = job;
	}
	public Person() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}
