package miu.edu.waa.lab4.repo;

import miu.edu.waa.lab4.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
}
