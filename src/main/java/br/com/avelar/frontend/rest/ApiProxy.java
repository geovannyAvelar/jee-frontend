package br.com.avelar.frontend.rest;

import javax.enterprise.context.Dependent;

import br.com.avelar.backend.util.ProducerOnly;

@ProducerOnly
@Dependent
public class ApiProxy<T> {

	private T proxy;
	
	public T get() {
		return proxy;
	}
	
	public void set(T proxy) {
		this.proxy = proxy;
	}
	
}