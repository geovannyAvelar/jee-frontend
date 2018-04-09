package br.com.avelar.frontend.bussiness;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.avelar.backend.rest.model.Person;
import br.com.avelar.frontend.api.PersonApiClient;

@RequestScoped
public class PersonLogic {
	
	@Inject
	private PersonApiClient personApi;
	
	public void savePerson(Person person) {
		personApi.savePerson(person);
	}
	
	public List<Person> findAllPeople() {
		return personApi.findAll();
	}
	
	public void deletePerson(Person person) {
		personApi.deletePerson(person);
	}
	
}
