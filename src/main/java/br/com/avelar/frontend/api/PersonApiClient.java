package br.com.avelar.frontend.api;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.avelar.backend.rest.PersonApi;
import br.com.avelar.backend.rest.model.Person;
import br.com.avelar.frontend.rest.ApiProxy;

@RequestScoped
public class PersonApiClient implements PersonApi {
	
	private static final long serialVersionUID = 1042084185680630070L;
	
	@Inject
	private ApiProxy<PersonApi> personApi;
	
	@Override
	public void savePerson(Person person) {
		personApi.get().savePerson(person);
	}
	
	@Override
	public List<Person> findAll() {
		return personApi.get().findAll();
	}

	@Override
	public void deletePerson(Person person) {
		personApi.get().deletePerson(person);
	}
	
}
