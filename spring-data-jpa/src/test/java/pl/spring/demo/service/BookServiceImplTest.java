package pl.spring.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.mapper.Mapper;
import pl.spring.demo.to.BookTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookServiceImplTest {

	@Autowired
	private Mapper maper;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private BookService bookService;


	@Before
	public void setUp() {
		cacheManager.getCache("booksCache").clear();
	}

	@Test
	public void testShouldFindAllBooks() {
		// when
		List<BookTo> allBooks = bookService.findAllBooks();
		// then
		assertNotNull(allBooks);
		assertFalse(allBooks.isEmpty());
		assertEquals(6, allBooks.size());
	}

	@Test
	// @Ignore
	public void testShouldFindAllBooksByTitle() {
		// given
		final String title = "Opium w rosole";
		// when
		List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
		// then
		assertNotNull(booksByTitle);
		assertFalse(booksByTitle.isEmpty());
	}

	@Test(expected = BookNotNullIdException.class)
	public void testShouldThrowBookNotNullIdException() {
		// given
		final BookTo bookToSave = new BookTo();
		bookToSave.setId(22L);
		// when
		bookService.saveBook(bookToSave);
		// then
		fail("test should throw BookNotNullIdException");
	}

	@Test
	@DirtiesContext
	public void testShouldSaveNewBooks() {
		// given
		BookEntity book = new BookEntity(null, "Wzorce w Javie", "first autohor second author");
		BookEntity book2 = new BookEntity(null, "Java Podstawy", "Murat Yener Alex Theedom ");
		// when
		BookTo result = bookService.saveBook(maper.mapToBookTo(book));
		BookTo result2 = bookService.saveBook(maper.mapToBookTo(book2));
		// then
		assertEquals(7L, result.getId().longValue());
		assertEquals(8L, result2.getId().longValue());
	}

	@Test
	public void testShouldFindTwoBooks() {
		// when
		List<BookTo> foundAllBooks = bookService.findBooksByTitle("An");
		List<Long> ids = createIdsList(foundAllBooks);
		// then
		assertEquals(2, foundAllBooks.size());
		assertEquals(4L, ids.get(0).longValue());
		assertEquals(5L, ids.get(1).longValue());
	}

	@Test
	public void testShouldFindFourBooks() {
		// when
		List<BookTo> foundAllBooks = bookService.findBooksByTitle("I");
		List<Long> ids = createIdsList(foundAllBooks);
		// then
		assertEquals(4, foundAllBooks.size());
		assertEquals(1L, ids.get(0).longValue());
		assertEquals(2L, ids.get(1).longValue());
		assertEquals(4L, ids.get(2).longValue());
		assertEquals(5L, ids.get(3).longValue());
	}
	
	@Test
	public void testShouldFindOneBookByAuthor() {
		// when
		List<BookTo> foundAllBooks = bookService.findBooksByAuthor("eDr aLeks");
		List<Long> ids = createIdsList(foundAllBooks);
		// then
		assertEquals(1, foundAllBooks.size());
		assertEquals(6L, ids.get(0).longValue());
	}
	
	@Test
	public void testShouldFindThreeBooksByAuthor() {
		// when
		List<BookTo> foundAllBooks = bookService.findBooksByAuthor("ki");
		List<Long> ids = createIdsList(foundAllBooks);
		// then
		assertEquals(3, foundAllBooks.size());
		assertEquals(3L, ids.get(0).longValue());
		assertEquals(4L, ids.get(1).longValue());
		assertEquals(5L, ids.get(2).longValue());
	}
	
	@Test
	@DirtiesContext
	public void testShouldFindOneBookByTwoAuthors() {
		// given
		BookEntity book = new BookEntity(null, "Java Podstawy","Murat Yener Alex Theedom");
		// when
		bookService.saveBook(maper.mapToBookTo(book));
		List<BookTo> foundAllBooks = bookService.findBooksByAuthor("eDom ura eX MUraT");
		List<Long> ids = createIdsList(foundAllBooks);
		// then
		assertEquals(1, foundAllBooks.size());
		assertEquals(7L, ids.get(0).longValue());
	}
	
	@Test
	@DirtiesContext
	public void testShouldFindOneBookBySecondAuthor() {
		// given
		BookEntity book = new BookEntity(null, "Java Podstawy","Murat Yener Alex Theedom");
		// when
		bookService.saveBook(maper.mapToBookTo(book));
		List<BookTo> foundAllBooks = bookService.findBooksByAuthor("Theedom");
		List<Long> ids = createIdsList(foundAllBooks);
		// then
		assertEquals(1, foundAllBooks.size());
		assertEquals(7L, ids.get(0).longValue());
	}
	
	@Test
	@DirtiesContext
	public void testShouldFindNoBookForEmptyString() {
		// given
		BookEntity book = new BookEntity(null, "Java Podstawy","Murat Yener Alex Theedom");
		// when
		bookService.saveBook(maper.mapToBookTo(book));
		List<BookTo> foundAllBooks = bookService.findBooksByAuthor("");
		// then
		assertEquals(0, foundAllBooks.size());
	}

	private List<Long> createIdsList(List<BookTo> books) {
		List<Long> ids = new ArrayList<>();
		for (BookTo book : books) {
			ids.add(book.getId().longValue());
		}
		Collections.sort(ids);
		return ids;
	}

}
