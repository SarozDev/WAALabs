package miu.edu.lab3.controller;

import ch.qos.logback.core.joran.conditional.ElseAction;
import lombok.AllArgsConstructor;
import miu.edu.lab3.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private Map<Integer, Book> books;

    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBook(@PathVariable Integer isbn) {
        Book book = books.get(isbn);
        return book == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Book Found") :
                new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<Collection<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(books.values());
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Collection<Book>> searchBooks(@PathVariable String author) {
        return new ResponseEntity(books.values().stream()
                .filter(aut -> aut.getAuthor().equals(author)), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (!books.containsKey(book.getIsbn())) {
            books.put(book.getIsbn(), book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Isbn already exists");
    }

    @PutMapping("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        if (books.containsKey(book.getIsbn())) {
            books.put(book.getIsbn(), book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book does not exist");
    }

    @DeleteMapping("/deleteBook/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer isbn) {
        if (books.containsKey(isbn)) {
            books.remove(isbn);
            return ResponseEntity.status(HttpStatus.OK).body(isbn + "deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Isbn found to be deleted.");
    }
}
