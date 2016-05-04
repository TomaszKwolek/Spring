package pl.spring.demo.aop;

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
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

@Aspect
@Component
public class BookDaoAdvisor {

    @Autowired
    private Sequence sequence;
    
    @Autowired
    private BookDao bookDao;
    
    @Before("@annotation(nullableId)")
	public void before(JoinPoint joinPoint, NullableId nullableId) {
		Object[] bookArgs = joinPoint.getArgs();
		BookTo book = (BookTo) bookArgs[0];
		checkNotNullId(bookArgs[0]);
		if (book.getId() == null) {
			book.setId(sequence.nextValue(bookDao.findAll()));
	        }

	}

	private void checkNotNullId(Object o) {
		if (o instanceof IdAware && ((IdAware) o).getId() != null) {
			throw new BookNotNullIdException();
		}
	}

}