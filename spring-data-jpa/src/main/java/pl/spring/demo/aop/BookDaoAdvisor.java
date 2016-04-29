package pl.spring.demo.aop;

import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.dao.Dao;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

@Aspect
@Component
public class BookDaoAdvisor {

	@Autowired
	private Sequence sequence;
	
	private Dao<?> dao;

	@SuppressWarnings("unchecked")
	@Before("@annotation(nullableId)")
	public void before(JoinPoint joinPoint, NullableId nullableId) {
		IdAware item = (IdAware)joinPoint.getArgs()[0];
		checkNotNullId(item);
		dao = (Dao<? extends IdAware>) joinPoint.getThis();
		
		Collection<? extends IdAware> allItems = (Collection<? extends IdAware>) dao.findAll();
		setId(item, allItems);
	}

	 private void setId(IdAware item, Collection<? extends IdAware> allItems){
		 if (item.getId() == null) {
				Long id = sequence.nextValue(allItems);
				item.setId(id);
			}
	 }

	private void checkNotNullId(Object o) {
		if (o instanceof IdAware && ((IdAware) o).getId() != null) {
			throw new BookNotNullIdException();
		}
	}

}