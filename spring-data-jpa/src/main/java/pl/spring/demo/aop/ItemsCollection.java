package pl.spring.demo.aop;

import java.util.Collection;


import pl.spring.demo.to.IdAware;

public interface ItemsCollection <T extends IdAware> {
	public Collection<? extends IdAware> getAllItems();
}
