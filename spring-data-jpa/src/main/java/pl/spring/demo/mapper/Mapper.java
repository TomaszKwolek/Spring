package pl.spring.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

@Service
public class Mapper {

	public BookTo mapToBookTo(BookEntity bookEntity) {
		if (bookEntity != null) {
			return new BookTo(bookEntity.getId(), bookEntity.getTitle(), mapAuthorsToTo(bookEntity));
		}
		return null;
	}

	public BookEntity mapToBookEntity(BookTo bookTo) {
		if (bookTo != null) {
			return new BookEntity(bookTo.getId(), bookTo.getTitle(), mapAuthorsToEntity(bookTo));
		}
		return null;
	}

	public List<BookTo> mapToTos(List<BookEntity> bookEntities) {
		List<BookTo> tos = new ArrayList<>();
		for (BookEntity book : bookEntities) {
			tos.add(mapToBookTo(book));
		}
		return tos;
	}

	public List<BookEntity> mapToEntities(List<BookTo> bookTos) {
		List<BookEntity> entities = new ArrayList<>();
		for (BookTo book : bookTos) {
			entities.add(mapToBookEntity(book));
		}
		return entities;
	}

	private String mapAuthorsToEntity(BookTo bookTo) {
		StringBuilder authorsEntity = new StringBuilder();

		for (AuthorTo authors : bookTo.getAuthors()) {
			authorsEntity.append(authors.getFirstName());
			authorsEntity.append(" ");
			authorsEntity.append(authors.getLastName());
			authorsEntity.append(" ");
		}
		return authorsEntity.toString();
	}

	private List<AuthorTo> mapAuthorsToTo(BookEntity bookEntity) {
		List<AuthorTo> authors = new ArrayList<>();
		String[] authorsArray = StringUtils.split(bookEntity.getAuthors(), ' ');
		Long id = 0L;
		for (int i = 0; i < authorsArray.length; i++) {
			if (i < authorsArray.length - 1) {
				authors.add(new AuthorTo(id++, authorsArray[i], authorsArray[i+1]));
				i=i+1;
			}
			if (i == authorsArray.length - 1) {
				authors.add(new AuthorTo(id++, authorsArray[i], ""));
			}
		}
		return authors;
	}
}
