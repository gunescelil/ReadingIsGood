package com.readingisgood.service;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.readingisgood.dto.BookStockUpdateDto;
import com.readingisgood.dto.NewBookDto;
import com.readingisgood.entity.Book;
import com.readingisgood.exception.ExceptionModel;
import com.readingisgood.exception.NotFoundException;
import com.readingisgood.repository.BookRepository;

@Service
public class BookService
{

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;

    private final ReentrantLock lock = new ReentrantLock();

    private static final String BOOK_NOT_FOUND = "The book with Id: %s is not found";
    private static final String BOOK_STOCK_IS_NOT_ENOUGH_AS_REQUEST = "The stock of book with Id: %s is not enough as requested: %d. ";

    @Autowired
    public BookService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book saveNewBook(NewBookDto bookSaveDto)
    {
        try
        {
            lock.lock();
            LOG.info("NewBookDto will be written to DB: {}", bookSaveDto);
            return this.bookRepository.save(new Book(bookSaveDto));
        }
        finally
        {
            if (lock.isHeldByCurrentThread())
            {
                lock.unlock();
            }
        }
    }

    @Transactional
    public Book updateStockofBook(BookStockUpdateDto bookStockUpdateDto)
    {
        try
        {
            lock.lock();
            Book book = this.bookRepository.findByBookId(bookStockUpdateDto.getBookId());
            if (book != null)
            {
                book.setStockCount(bookStockUpdateDto.getStock());
                LOG.info("Book will be updated as: {}", book);
                return this.bookRepository.save(book);
            }
            else
            {
                throw new NotFoundException(new ExceptionModel(HttpStatus.NOT_FOUND,
                        String.format(BOOK_NOT_FOUND, bookStockUpdateDto.getBookId())));
            }
        }
        finally
        {
            if (lock.isHeldByCurrentThread())
            {
                lock.unlock();
            }
        }
    }

    @Transactional
    public void setBookForOrder(String bookId, int count)
    {
        try
        {
            lock.lock();
            Book book = this.bookRepository.findByBookId(bookId);
            if (book != null)
            {
                if (book.getStockCount() >= count)
                {
                    book.setStockCount(book.getStockCount() - count);
                    LOG.info("Book will be updated as: {}", book);
                    this.bookRepository.save(book);
                }
                else
                {
                    throw new NotFoundException(new ExceptionModel(HttpStatus.NOT_FOUND,
                            String.format(BOOK_STOCK_IS_NOT_ENOUGH_AS_REQUEST, bookId, count)
                                    + String.format("Only left %d", book.getStockCount())));
                }
            }
            else
            {
                throw new NotFoundException(
                        new ExceptionModel(HttpStatus.NOT_FOUND, String.format(BOOK_NOT_FOUND, bookId)));
            }
        }
        finally
        {
            if (lock.isHeldByCurrentThread())
            {
                lock.unlock();
            }
        }
    }

    public double getPriceOfBook(String bookId)
    {
        try
        {
            lock.lock();
            Book book = this.bookRepository.findByBookId(bookId);
            if (book != null)
            {
                return book.getPrice();
            }
            else
            {
                throw new NotFoundException(
                        new ExceptionModel(HttpStatus.NOT_FOUND, String.format(BOOK_NOT_FOUND, bookId)));
            }
        }
        finally
        {
            if (lock.isHeldByCurrentThread())
            {
                lock.unlock();
            }
        }
    }

    public Page<Book> getAllBooks(Pageable pageable)
    {
        return this.bookRepository.findAll(pageable);
    }

}
