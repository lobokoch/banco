/**********************************************************************************************
Code generated with MKL Plug-in version: 30.0.1
Code generated at time stamp: 2019-11-10T19:31:44.455
Copyright: Kerubin - logokoch@gmail.com

WARNING: DO NOT CHANGE THIS CODE BECAUSE THE CHANGES WILL BE LOST IN THE NEXT CODE GENERATION.
***********************************************************************************************/

package br.com.kerubin.api.cadastros.banco.conciliacao.reprocessar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.kerubin.api.cadastros.banco.conciliacao.model.ParametrosReprocessarDTO;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoService;
import br.com.kerubin.api.cadastros.banco.conciliacao.service.ConciliacaoServiceHelper;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaobancaria.ConciliacaoBancariaDTOConverter;
import br.com.kerubin.api.cadastros.banco.entity.conciliacaotransacao.ConciliacaoTransacaoDTOConverter;

public class ConciliacaoBancariaReprocessarArquivoWebTest extends BaseWebTest {
	
	@TestConfiguration
	@ComponentScan(basePackages = {
			"br.com.kerubin.api.cadastros.banco.conciliacao.controller"
	})
    static class TestConfig {
        
    }
	
    @MockBean
	private ConciliacaoService conciliacaoService;
    
    @MockBean
	private ConciliacaoServiceHelper conciliacaoServiceHelper;
	
    @MockBean
    private ConciliacaoTransacaoDTOConverter conciliacaoTransacaoDTOConverter;
	
    @MockBean
    private ConciliacaoBancariaDTOConverter conciliacaoBancariaDTOConverter;
    
    @Test
    public void testReprocessar() throws Exception {
    	 
    	UUID id1 = UUID.fromString("a058bfa2-64be-40a3-863c-01bea7ced89b");
    	List<UUID> list = Arrays.asList(id1);
    	
    	doNothing().when(conciliacaoService).reprocessar(list);
    	
	    String uri = "/cadastros/banco/entities/conciliacaoBancaria/reprocessar";
	    ParametrosReprocessarDTO parametros = new ParametrosReprocessarDTO();
	    parametros.setIds(Arrays.asList(id1));
		   
		String inputJson = mapToJson(parametros);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertThat(status).isEqualTo(HttpServletResponse.SC_NO_CONTENT);
		String content = mvcResult.getResponse().getContentAsString();
		assertThat(content).isEmpty();
    }
        
}
