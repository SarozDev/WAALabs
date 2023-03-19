package edu.miu.lab5studentmongo;

import edu.miu.lab5studentmongo.data.StudentRepository;
import edu.miu.lab5studentmongo.entity.Address;
import edu.miu.lab5studentmongo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentCommandRunner implements CommandLineRunner {

    @Autowired
    private StudentRepository repo;

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student(1,"Ram","123454321","abc@abc.com",null);
        Address address = new Address("1000 N 4th St","Fairfield",52557);
        student.setAddress(address);
        repo.save(student);

        Student student1 = new Student(2,"Shyam","123454322","def@abc.com",null);
        Address address1 = new Address("1000 N 4th St1","Fairfield1",52557);
        student1.setAddress(address1);
        repo.save(student1);

        Student student2 = new Student(3,"Hari","123454323","ghi@abc.com",null);
        repo.save(student2);

        Student student3 = new Student(4,"Gopal","123454324","qrs@abc.com",null);
       repo.save(student3);

        Student student4 = new Student(5,"Murali","123454325","xyz@abc.com",null);
        repo.save(student4);

        //get students
        System.out.println(repo.findById(1).get());
        System.out.println(repo.findById(2).get());
        System.out.println("----List Of Students-----");
        System.out.println(repo.findAll());

        //update student
        student = repo.findById(1).get();
        student.setEmail("abc.def.com");
        repo.save(student);

        System.out.println("----Find By Phone");
        System.out.println(repo.findByName("Ram"));

        System.out.println("----Find By Email");
        System.out.println(repo.findByEmail("Ram"));

        System.out.println("---Find Student By Address city");
        List<Student> students = repo.findStudentByAddressCity("Fairfield");
        for(Student st : students){
            System.out.println(st);
        }
    }
}
