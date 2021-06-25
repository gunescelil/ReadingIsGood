package com.readingisgood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingisgood.dto.BookStockUpdateDto;
import com.readingisgood.dto.NewBookDto;
import com.readingisgood.entity.Book;
import com.readingisgood.exception.BadRequestException;
import com.readingisgood.exception.ExceptionModel;
import com.readingisgood.exception.NotAllowedOperationException;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.BookRepository;

@Service
public class BookService
{

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void saveNewBook(NewBookDto bookSaveDto)
    {
        this.bookRepository.save(new Book(bookSaveDto));
    }

    @Transactional
    public void updateStockofBook(BookStockUpdateDto bookStockUpdateDto)
    {
        Book book = this.bookRepository.findByBookId(bookStockUpdateDto.getBookId());
        if (book != null)
        {
            book.setStockCount(bookStockUpdateDto.getStock());
            this.bookRepository.save(book);
        }
        else
        {
            throw new NotFoundException(new ExceptionModel(HttpStatus.NOT_FOUND,
                    "The book with Id: " + bookStockUpdateDto.getBookId() + " is not found"));
        }
    }

    @Transactional
    public void setBookForOrder(String bookId, int count)
    {
        Book book = this.bookRepository.findByBookId(bookId);
        if (book != null)
        {
            if (book.getStockCount() >= count)
            {
                book.setStockCount(book.getStockCount() - count);
                this.bookRepository.save(book);
            }
            else
            {
                throw new BadRequestException(new ExceptionModel(HttpStatus.BAD_REQUEST,
                        "The stock is book with Id: " + bookId + " is not enough as requested: " + count));
            }
        }
        else
        {
            throw new NotFoundException(
                    new ExceptionModel(HttpStatus.NOT_FOUND, "The book with Id: " + bookId + " is not found"));
        }

    }

}
