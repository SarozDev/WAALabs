package edu.miu.repo;

import edu.miu.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookRepo {
    private Map<Integer, Book> books = new HashMap<>();

    public void saveUpdateBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void delete(Integer isbn) {
        books.remove(isbn);
    }

    public Map<Integer, Book> findAll() {
        return books;
    }

    public Book getBook(String isbn){
        if(books.containsKey(isbn)){
            return books.get(isbn);
        }

        return null;
    }

    public List<Book> searchBooks(String author) {
        return books.values().stream()
                .filter(book->book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
}
