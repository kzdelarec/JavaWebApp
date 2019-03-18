package hr.java.web.zdelarec.moneyapp;

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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
		
	@Test
	public void testGetMethod() throws Exception {
		this.mockMvc
			.perform(get("/home").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("expenses"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("searchName"))
			.andExpect(model().attributeExists("type"))
			.andExpect(view().name("index"));
	}
	
	@Test
	public void testFilterMethod() throws Exception{
		this.mockMvc
			.perform(post("/home").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("searchName", "pivo")
			.param("type", "")
			.param("searchDate", ""))
			.andExpect(status().isOk()).andExpect(model().attributeExists("expenses"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("searchName"))
			.andExpect(model().attributeExists("type"))
			.andExpect(view().name("index"));
		
		this.mockMvc
			.perform(post("/home").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("searchName", "")
			.param("type", "OTHER")
			.param("searchDate", ""))
			.andExpect(status().isOk()).andExpect(model().attributeExists("expenses"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("searchName"))
			.andExpect(model().attributeExists("type"))
			.andExpect(view().name("index"));
		
		this.mockMvc
			.perform(post("/home").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("searchName", "")
			.param("vrsta", "")
			.param("searchDate", "2019-05-07"))
			.andExpect(status().isOk()).andExpect(model().attributeExists("expenses"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("searchName"))
			.andExpect(model().attributeExists("type"))
			.andExpect(view().name("index"));
		
		this.mockMvc
			.perform(post("/home").with(user("admin").password("adminpass")
			.roles("USER", "ADMIN")).with(csrf())
			.param("searchName", "")
			.param("type", "")
			.param("searchDate", ""))
			.andExpect(status().isOk()).andExpect(model().attributeExists("expenses"))
			.andExpect(model().attributeExists("total"))
			.andExpect(model().attributeExists("searchName"))
			.andExpect(model().attributeExists("type"))
			.andExpect(view().name("index"));
	}
	
}
