package pl.spring.demo.dao;

import java.util.List;

import pl.spring.demo.to.IdAware;

public interface Dao<T extends IdAware> {

    List<T> findAll();

    T save(T book);
	
}
