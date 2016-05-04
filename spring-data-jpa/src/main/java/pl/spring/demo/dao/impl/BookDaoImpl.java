package pl.spring.demo.dao.impl;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.aop.BookDaoAdvisor;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BookDaoImpl implements BookDao {

	private final Set<BookEntity> ALL_BOOKS = new HashSet<>();

	@Autowired
	private Sequence sequence;

	public BookDaoImpl() {
		addTestBooks();
	}

	@Override
	public List<BookEntity> findAll() {
		return new ArrayList<>(ALL_BOOKS);
	}

	@Override
	public List<BookEntity> findBookByTitle(String title) {
		List<BookEntity> books = new ArrayList<>();
		for (BookEntity book : ALL_BOOKS) {
			if (StringUtils.containsIgnoreCase(book.getTitle(), title)) {
				books.add(book);
			}
		}
		return books;
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {
		List<BookEntity> books = new ArrayList<>();
		String[] authorsPrefixes = StringUtils.split(author, ' ');
		for (BookEntity book : ALL_BOOKS) {
			for (int i = 0; i < authorsPrefixes.length; i++) {
				if (i==authorsPrefixes.length-1 && StringUtils.containsIgnoreCase(book.getAuthors(), authorsPrefixes[i])) {
					books.add(book);
				}
				if (!StringUtils.containsIgnoreCase(book.getAuthors(), authorsPrefixes[i])) {
					i=authorsPrefixes.length;
				}
			}
		}
		return books;
	}

	@Override
	@NullableId
	public BookEntity save(BookEntity book) {
		ALL_BOOKS.add(book);
		return book;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	private void addTestBooks() {
		ALL_BOOKS.add(new BookEntity(1L, "Romeo i Julia", "Wiliam Szekspir"));
		ALL_BOOKS.add(new BookEntity(2L, "Opium w rosole", "Hanna Ożogowska"));
		ALL_BOOKS.add(new BookEntity(3L, "Przygody Odyseusza", "Jan Parandowski"));
		ALL_BOOKS.add(new BookEntity(4L, "Awantura w Niekłaju", "Edmund Niziurski"));
		ALL_BOOKS.add(new BookEntity(5L, "Pan Samochodzik i Fantomas", "Zbigniew Nienacki"));
		ALL_BOOKS.add(new BookEntity(6L, "Zemsta", "Aleksander Fredro"));
	}

}