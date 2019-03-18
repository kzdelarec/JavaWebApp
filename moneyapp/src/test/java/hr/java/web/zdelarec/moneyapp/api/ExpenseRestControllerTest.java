package hr.java.web.zdelarec.moneyapp.api;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.enumeration.Type;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetMethod() throws Exception {
		this.mockMvc
			.perform(get("/api/expense").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void post() throws Exception{
		Expense exp = new Expense();
		exp.setName("test");
		exp.setDate(new Date());
		exp.setValue(new BigDecimal(250));
		exp.setXy(Type.FOOD);
		exp.setWalletId((long)1);
		
		this.mockMvc.perform(get("/api/expense").with(user("admin").password("adminpass")
				.roles("USER", "ADMIN"))
				.content(new ObjectMapper().writeValueAsString(exp))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testGetMethodById() throws Exception {
		this.mockMvc
			.perform(get("/api/expense/").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN"))
			.param("id", "1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void testDelete() throws Exception {
		this.mockMvc
			.perform(delete("/api/expense/1").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isNoContent());
	}
	
	

}
