package br.com.avelar.frontend.rest;

import java.lang.reflect.ParameterizedType;

import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import br.com.avelar.backend.util.ProducerOnly;

@Stateless
public class ApiProxyProducer {
	
	// TODO Carregar via JNDI. Hardcoded apenas para simplificar.
	private String baseUrl = "http://localhost:8080/backend";
	
	@Produces
	@SuppressWarnings("unchecked")
	public <T> ApiProxy<T> produce(InjectionPoint ip, BeanManager bm) {
		Bean<? extends Object> bean = bm.resolve(bm.getBeans(ApiProxy.class, ApiProxy.class.getAnnotationsByType(ProducerOnly.class)));
		
		CreationalContext<? extends Object> ctx = bm.createCreationalContext(bean);
		ApiProxy<T> restProxy = (ApiProxy<T>) bm.getReference(bean, bean.getBeanClass(), ctx);
		
		Class<T> entityClass = (Class<T>) ((ParameterizedType) ip.getType()).getActualTypeArguments()[0];
		restProxy.set(makeProxy(entityClass));
		
		return restProxy;
	}
	
	private <T> T makeProxy(Class<T> cls) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JSR310Module());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		ResteasyJackson2Provider provider = new ResteasyJackson2Provider();
		provider.setMapper(mapper);
		
		WebTarget target = ClientBuilder.newClient()
										.register(provider)
										.target(UriBuilder.fromUri(baseUrl).path("api").build());
		
		return ((ResteasyWebTarget) target).proxy(cls);
	}
}
