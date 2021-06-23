package com.readingisgood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.readingisgood.entity.Book;

public interface BookRepository extends MongoRepository<Book, String>
{
    public Book findByBookId(String bookId);
}   
