package pl.spring.demo.dao;

import java.util.List;

import pl.spring.demo.entity.BookEntity;

public interface BookDao extends Dao<BookEntity> {

    List<BookEntity> findBookByTitle(String title);

    List<BookEntity> findBooksByAuthor(String author);
    
}