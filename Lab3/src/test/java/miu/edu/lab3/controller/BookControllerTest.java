package miu.edu.lab3.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashMap;

import miu.edu.lab3.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class BookControllerTest {

    @Test
    void testGetBook() {
        ResponseEntity<?> actualBook = (new BookController(new HashMap<>())).getBook(1);
        assertEquals("No Book Found", actualBook.getBody());
        assertEquals(404, actualBook.getStatusCodeValue());
        assertTrue(actualBook.getHeaders().isEmpty());
    }

    @Test
    void testGetBook2() {
        HashMap<Integer, Book> integerBookMap = new HashMap<>();
        integerBookMap.put(1, new Book());
        ResponseEntity<?> actualBook = (new BookController(integerBookMap)).getBook(1);
        assertTrue(actualBook.hasBody());
        assertEquals(200, actualBook.getStatusCodeValue());
        assertTrue(actualBook.getHeaders().isEmpty());
    }


    @Test
    void testGetBook3() {
        HashMap<Integer, Book> integerBookMap = new HashMap<>();
        integerBookMap.put(1, mock(Book.class));
        ResponseEntity<?> actualBook = (new BookController(integerBookMap)).getBook(1);
        assertTrue(actualBook.hasBody());
        assertEquals(200, actualBook.getStatusCodeValue());
        assertTrue(actualBook.getHeaders().isEmpty());
    }


    @Test
    void testGetAllBooks() {
        ResponseEntity<Collection<Book>> actualAllBooks = (new BookController(new HashMap<>())).getAllBooks();
        assertTrue(actualAllBooks.hasBody());
        assertEquals(200, actualAllBooks.getStatusCodeValue());
        assertTrue(actualAllBooks.getHeaders().isEmpty());
    }


    @Test
    void testGetAllBooks2() {
        HashMap<Integer, Book> integerBookMap = new HashMap<>();
        integerBookMap.put(1, mock(Book.class));
        ResponseEntity<Collection<Book>> actualAllBooks = (new BookController(integerBookMap)).getAllBooks();
        assertTrue(actualAllBooks.hasBody());
        assertEquals(200, actualAllBooks.getStatusCodeValue());
        assertTrue(actualAllBooks.getHeaders().isEmpty());
    }


    @Test
    void testSearchBooks() {
        ResponseEntity<Collection<Book>> actualSearchBooksResult = (new BookController(new HashMap<>()))
                .searchBooks("JaneDoe");
        assertTrue(actualSearchBooksResult.hasBody());
        assertEquals(200, actualSearchBooksResult.getStatusCodeValue());
        assertTrue(actualSearchBooksResult.getHeaders().isEmpty());
    }


    @Test
    void testSearchBooks2() {
        HashMap<Integer, Book> integerBookMap = new HashMap<>();
        integerBookMap.put(1, mock(Book.class));
        ResponseEntity<Collection<Book>> actualSearchBooksResult = (new BookController(integerBookMap))
                .searchBooks("JaneDoe");
        assertTrue(actualSearchBooksResult.hasBody());
        assertEquals(200, actualSearchBooksResult.getStatusCodeValue());
        assertTrue(actualSearchBooksResult.getHeaders().isEmpty());
    }


    @Test
    void testAddBook() {
        BookController bookController = new BookController(new HashMap<>());
        ResponseEntity<?> actualAddBookResult = bookController.addBook(new Book());
        assertTrue(actualAddBookResult.hasBody());
        assertEquals(200, actualAddBookResult.getStatusCodeValue());
        assertTrue(actualAddBookResult.getHeaders().isEmpty());
    }


    @Test
    void testAddBook2() {
        BookController bookController = new BookController(new HashMap<>());
        bookController.addBook(new Book());
        ResponseEntity<?> actualAddBookResult = bookController.addBook(new Book());
        assertEquals("Isbn already exists", actualAddBookResult.getBody());
        assertEquals(403, actualAddBookResult.getStatusCodeValue());
        assertTrue(actualAddBookResult.getHeaders().isEmpty());
    }


    @Test
    void testAddBook4() {
        BookController bookController = new BookController(new HashMap<>());
        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn(1);
        ResponseEntity<?> actualAddBookResult = bookController.addBook(book);
        assertTrue(actualAddBookResult.hasBody());
        assertEquals(200, actualAddBookResult.getStatusCodeValue());
        assertTrue(actualAddBookResult.getHeaders().isEmpty());
        verify(book, atLeast(1)).getIsbn();
    }

    @Test
    void testUpdateBook() {
        BookController bookController = new BookController(new HashMap<>());
        ResponseEntity<?> actualUpdateBookResult = bookController.updateBook(new Book());
        assertEquals("Book does not exist", actualUpdateBookResult.getBody());
        assertEquals(404, actualUpdateBookResult.getStatusCodeValue());
        assertTrue(actualUpdateBookResult.getHeaders().isEmpty());
    }

    @Test
    void testUpdateBook2() {
        BookController bookController = new BookController(new HashMap<>());
        bookController.addBook(new Book());
        ResponseEntity<?> actualUpdateBookResult = bookController.updateBook(new Book());
        assertTrue(actualUpdateBookResult.hasBody());
        assertEquals(200, actualUpdateBookResult.getStatusCodeValue());
        assertTrue(actualUpdateBookResult.getHeaders().isEmpty());
    }

    @Test
    void testUpdateBook4() {
        BookController bookController = new BookController(new HashMap<>());
        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn(1);
        ResponseEntity<?> actualUpdateBookResult = bookController.updateBook(book);
        assertEquals("Book does not exist", actualUpdateBookResult.getBody());
        assertEquals(404, actualUpdateBookResult.getStatusCodeValue());
        assertTrue(actualUpdateBookResult.getHeaders().isEmpty());
        verify(book).getIsbn();
    }

    @Test
    void testDeleteBook() {
        ResponseEntity<?> actualDeleteBookResult = (new BookController(new HashMap<>())).deleteBook(1);
        assertEquals("No Isbn found to be deleted.", actualDeleteBookResult.getBody());
        assertEquals(404, actualDeleteBookResult.getStatusCodeValue());
        assertTrue(actualDeleteBookResult.getHeaders().isEmpty());
    }

    @Test
    void testDeleteBook2() {
        HashMap<Integer, Book> integerBookMap = new HashMap<>();
        integerBookMap.put(1, new Book());
        ResponseEntity<?> actualDeleteBookResult = (new BookController(integerBookMap)).deleteBook(1);
        assertEquals("1deleted", actualDeleteBookResult.getBody());
        assertEquals(200, actualDeleteBookResult.getStatusCodeValue());
        assertTrue(actualDeleteBookResult.getHeaders().isEmpty());
    }

    @Test
    void testDeleteBook3() {
        HashMap<Integer, Book> integerBookMap = new HashMap<>();
        integerBookMap.put(1, mock(Book.class));
        ResponseEntity<?> actualDeleteBookResult = (new BookController(integerBookMap)).deleteBook(1);
        assertEquals("1deleted", actualDeleteBookResult.getBody());
        assertEquals(200, actualDeleteBookResult.getStatusCodeValue());
        assertTrue(actualDeleteBookResult.getHeaders().isEmpty());
    }
}

