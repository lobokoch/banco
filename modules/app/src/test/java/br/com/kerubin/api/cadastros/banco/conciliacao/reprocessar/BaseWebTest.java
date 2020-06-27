package br.com.kerubin.api.cadastros.banco.conciliacao.reprocessar;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public abstract class BaseWebTest {
	
	@TestConfiguration
	@SpringBootConfiguration
    static class TestConfig {
        
    }
	
	@Inject
	protected MockMvc mockMvc;
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
     }
    
     protected <T> T mapFromJson(String json, Class<T> clazz) throws Exception {        
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
     }

}
