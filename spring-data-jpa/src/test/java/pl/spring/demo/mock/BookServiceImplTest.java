package pl.spring.demo.mock;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.Mapper;
import pl.spring.demo.service.impl.BookServiceImpl;
import pl.spring.demo.to.BookTo;

public class BookServiceImplTest {

	@InjectMocks
	private BookServiceImpl bookService;
	@Mock
	private BookDao bookDao;
	@Mock
	private Mapper mapper;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testShouldSaveBook() {
		// given
		BookEntity book = new BookEntity(null, "title", "author");
		Mockito.when(mapper.mapToBookTo(Mockito.any(BookEntity.class))).thenCallRealMethod();
		Mockito.when(mapper.mapToBookEntity(Mockito.any(BookTo.class))).thenCallRealMethod();
		Mockito.when(bookDao.save(Mockito.any(BookEntity.class))).thenReturn(new BookEntity(1L, "title", "author"));
		// when
		BookTo result = bookService.saveBook(mapper.mapToBookTo(book));
		// then
	//	Mockito.verify(bookDao).save(book);
		assertEquals(1L, result.getId().longValue());
	}
}
