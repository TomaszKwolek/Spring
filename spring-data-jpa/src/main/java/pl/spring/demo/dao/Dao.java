package pl.spring.demo.dao;

import java.util.List;

import pl.spring.demo.to.BookTo;

public interface Dao<T> {

    List<BookTo> findAll();

    BookTo save(BookTo book);
	
}
