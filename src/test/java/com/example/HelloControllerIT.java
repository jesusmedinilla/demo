package com.example;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.example.model.Entity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class) 
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class HelloControllerIT {

    @Value("${local.server.port}")
    private int port;
    
    @Mock
    private Entity newEntity;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
		
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void crudTest() throws Exception {
		
		int originalSize = getCurrentSize();
		
		//Create
		ResponseEntity<Entity> response2 = template.postForEntity(base.toString(), newEntity, Entity.class);
		newEntity = response2.getBody();
		
		assertThat(getCurrentSize(), equalTo( originalSize + 1 ) );
		
		//Update
		Entity e = new Entity();
		e.setId(newEntity.getId());
		e.setName("newName");
		HttpEntity<Entity> requestUpdate = new HttpEntity<>(e);;
		template.exchange(base.toString(), HttpMethod.PUT, requestUpdate, Void.class);//.put(base.toString(), e);
		
		
		//
		ResponseEntity<Entity> responseGet = template.getForEntity(base.toString() + "/" + newEntity.getId(), Entity.class);
		assertThat(responseGet.getBody().getName(), equalTo( "newName" ) );
		
		template.delete(base.toString() + "/" + responseGet.getBody().getId());
		
		assertThat(getCurrentSize(), equalTo( originalSize) );		
	}
	
	private int getCurrentSize() throws JsonParseException, JsonMappingException, IOException
	{
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		List<Entity> o = new ObjectMapper().readValue(response.getBody(), new TypeReference<List<Entity>>(){} );
		return o.size();
	}
}