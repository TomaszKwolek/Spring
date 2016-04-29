package pl.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.Mapper;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

	@Autowired
	private Mapper mapper;
	
	@Autowired
    private BookDao bookDao;
	
	@Override
    @Cacheable("booksCache")
    public List<BookTo> findAllBooks() {
        return mapper.mapToTos(bookDao.findAll());
    }

	@Override
    public List<BookTo> findBooksByTitle(String title) {
        return mapper.mapToTos(bookDao.findBookByTitle(title));
    }

	@Override
    public List<BookTo> findBooksByAuthor(String author) {
        return mapper.mapToTos(bookDao.findBooksByAuthor(author));
    }

	@Override
    public BookTo saveBook(BookTo book) {
    	BookTo bookTo=null;
    	BookEntity entity = mapper.mapToBookEntity(book);
    	entity =  bookDao.save(entity);
    	bookTo =  mapper.mapToBookTo(entity);
        return bookTo;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}