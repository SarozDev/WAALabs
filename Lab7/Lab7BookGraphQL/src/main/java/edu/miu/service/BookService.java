package edu.miu.service;

import edu.miu.entity.Book;
import edu.miu.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private BookRepo bookRepo;

    public Book addBook(Book book) throws Exception {
        if (getAllBooks().containsKey(book.getIsbn())) {
            throw new Exception("Isbn " + book.getIsbn() + " already exists");
        }

        bookRepo.saveUpdateBook(book);
        return book;
    }

    public Map<Integer, Book> getAllBooks() throws Exception {
        return bookRepo.findAll();
    }

    public Book updateBook(Book book) throws Exception {
        if (getAllBooks().containsKey(book.getIsbn())) {
            bookRepo.saveUpdateBook(book);
            return book;
        }
        throw new Exception("Book does not exist!");
    }

    public String delete(Integer isbn) throws Exception {
        if (getAllBooks().containsKey(isbn)) {
            bookRepo.delete(isbn);
            return "Book with isbn " + isbn + " deleted";
        }
        throw new Exception("No " + isbn + " found to be deleted");
    }

    public Book findByIsbn(Integer isbn) throws Exception{
        Book book = bookRepo.getBook(String.valueOf(isbn));
        if(book==null)
        {
            throw new Exception("Book does not exist!");
        }

        return book;
    }

    public Collection<Book> findByAuthor(String author) throws Exception {
        return getAllBooks().values().stream().filter(b->b.getAuthor().equals(author)).collect(Collectors.toList());
    }

    public List<Book> searchBooks(String author) {
        return bookRepo.searchBooks(author);
    }
}
