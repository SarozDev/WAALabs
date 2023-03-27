package edu.miu.controller;

import edu.miu.entity.Book;
import edu.miu.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @QueryMapping
    public Collection<Book> getAllBooks() throws Exception {
        return bookService.getAllBooks().values();
    }

    @QueryMapping
    public Optional<Book> getBook(@Argument int isbn) throws Exception {
        return Optional.ofNullable(bookService.findByIsbn(isbn));
    }

    @QueryMapping
    public List<Book> searchBooks(@Argument String author){
        return bookService.searchBooks(author);
    }


    @MutationMapping
    public Optional<Book> addBook(@Argument Book book) throws Exception {
        return Optional.ofNullable(bookService.addBook(book));
    }

    @MutationMapping
    public Optional<Book> updateBook(@Argument Book book) throws Exception {
        return Optional.ofNullable(bookService.updateBook(book));
    }

    @MutationMapping
    public String deleteBook(@Argument String isbn) throws Exception {
        return bookService.delete(Integer.valueOf(isbn));
    }
}
