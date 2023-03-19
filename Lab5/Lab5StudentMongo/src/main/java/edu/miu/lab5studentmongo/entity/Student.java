package edu.miu.lab5studentmongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Student {
    @Id
    private Integer studentId;

    private String name;
    private String phoneNumber;
    private String email;
    private Address address;

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
