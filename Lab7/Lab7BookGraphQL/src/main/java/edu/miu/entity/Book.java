package edu.miu.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.descriptor.web.MessageDestinationRef;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class Book {
    @NotNull
    @Size(min = 1)
    private Integer isbn;

    @Length(min =3)
    private String author;

    @Length(min = 3)
    private String title;
    @NotNull
    private Double price;
}
