package com.readingisgood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.readingisgood.dto.BookStockUpdateDto;
import com.readingisgood.dto.NewBookDto;
import com.readingisgood.entity.Book;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.BookRepository;
import com.readingisgood.service.BookService;

class BookServiceTest
{
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void init()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavingNewBookWorks()
    {
        String bookName = "bookName";
        double price = 25.0;
        NewBookDto bookSaveDto = new NewBookDto();
        bookSaveDto.setBookName(bookName);
        bookSaveDto.setPrice(price);

        Book saved = new Book(bookSaveDto);
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(saved);

        Book actual = bookService.saveNewBook(bookSaveDto);

        assertEquals(bookSaveDto.getBookName(), actual.getBookName());
        assertEquals(bookSaveDto.getPrice(), actual.getPrice());
    }

    @Test
    void testUpdatingBookWorks()
    {
        String bookId = "bookId";
        int stock = 100;
        BookStockUpdateDto bookStockUpdateDto = new BookStockUpdateDto();
        bookStockUpdateDto.setBookId(bookId);
        bookStockUpdateDto.setStock(stock);

        Book saved = new Book();
        saved.setStockCount(stock);

        when(bookRepository.findByBookId(bookId)).thenReturn(saved);
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(saved);

        Book actual = bookService.updateStockofBook(bookStockUpdateDto);

        assertEquals(stock, actual.getStockCount());
    }

    @Test
    void testUpdatingBookThrowsNotFound()
    {
        String bookId = "bookId";
        int stock = 100;
        BookStockUpdateDto bookStockUpdateDto = new BookStockUpdateDto();
        bookStockUpdateDto.setBookId(bookId);
        bookStockUpdateDto.setStock(stock);

        Book saved = new Book();
        saved.setStockCount(stock);

        when(bookRepository.findByBookId(bookId)).thenReturn(null);

        String BOOK_NOT_FOUND = "The book with Id: %s is not found";
        String expectedMessage = String.format(BOOK_NOT_FOUND, bookStockUpdateDto.getBookId());
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
        {
            bookService.updateStockofBook(bookStockUpdateDto);
        });

        String actualMessage = exception.getMessage();
        assertEquals(exception.getExceptionModel().getErrorCode(), expectedHttpStatus);
        assertTrue(actualMessage.contains(expectedMessage));

        verify(bookRepository, never()).save(ArgumentMatchers.any(Book.class));

    }

}
