package hr.java.web.zdelarec.moneyapp;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetMethod() throws Exception {
		
		this.mockMvc
			.perform(get("/error").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/home?error"));
	}
}
