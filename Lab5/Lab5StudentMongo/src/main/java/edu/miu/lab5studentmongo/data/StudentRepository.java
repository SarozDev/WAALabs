package edu.miu.lab5studentmongo.data;

import edu.miu.lab5studentmongo.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, Integer> {
    Student findByName(String name);
    Student findByPhoneNumber(String phoneNumber);

    @Query("{'address.city' :  70}")
    List<Student> findStudentByAddressCity(String city);

    Student findByEmail(String email);
}
