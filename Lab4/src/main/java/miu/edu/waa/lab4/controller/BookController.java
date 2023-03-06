package miu.edu.waa.lab4.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import miu.edu.waa.lab4.entity.Book;
import miu.edu.waa.lab4.service.BookService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBooks() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks().values());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable int isbn) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid Book book) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.addBook(book));
        }
        catch(Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody @Valid Book book){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(book));
        }
        catch(Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable int isbn){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.delete(isbn));
        }
        catch(Exception exception)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
}
