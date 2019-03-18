package hr.java.web.zdelarec.moneyapp;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import hr.java.web.zdelarec.moneyapp.entities.Expense;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

	@Autowired
	private MockMvc mockMvc;
		
	@Test
	public void testGetMethod() throws Exception {
		this.mockMvc
			.perform(get("/expense/new").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("expense"))
			.andExpect(model().attributeExists("type"))
			.andExpect(view().name("expense"));
	}
	
	@Test
	public void testPostMethod() throws Exception {
		
		//test for errors
		this.mockMvc
		.perform(post("/expense/new").with(user("admin").password("adminpass")
		.roles("USER", "ADMIN")).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("expense"))
		.andExpect(view().name("expense"));
		this.mockMvc
			.perform(post("/expense/new").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("name", "test")
			.param("xy", "FOOD")
			.param("value", "50"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("expense"))
			.andExpect(model().attributeExists("wallet"))
			.andExpect(view().name("expenseInfo"));
	}
	
	@Test
	public void testResetWallet() throws Exception {
		this.mockMvc
			.perform(get("/resetWallet").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/home"));
	}
	
	@Test
	public void testResetWalletByUsername() throws Exception {
		this.mockMvc
			.perform(get("/resetwalletbyusername").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN"))
			.param("id", "1"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/home"));
	}

}
