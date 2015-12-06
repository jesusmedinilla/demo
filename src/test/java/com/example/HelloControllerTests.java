package com.example;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.dao.EntityDAO;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class HelloControllerTests {

	private MockMvc mvc;
	
	@Mock
	private EntityDAO entityDao;
	
	@InjectMocks
	private HelloController helloController;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(helloController).build();
	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				//.andExpect(content().string(equalTo("Greetings from Spring Boot!")));
	}
	
//	@Test
//	public void craeteEntity() throws Exception {
//		
//		ResultActions ra = mvc.perform(MockMvcRequestBuilders.post("/", entity).accept(MediaType.APPLICATION_JSON))
//					.andExpect(status().isOk());
//		MvcResult result = ra.andReturn();
//		String entityJson = result.getResponse().getContentAsString();
//		Entity ob = new ObjectMapper().readValue(entityJson, Entity.class);
//		entityId = ob.getId();
//	}

}
