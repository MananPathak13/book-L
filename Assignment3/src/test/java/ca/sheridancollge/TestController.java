package ca.sheridancollge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import ca.sheridancollge.database.DatabaseAccess;



@RunWith(SpringRunner.class)
@SpringBootTest

@AutoConfigureMockMvc
public class TestController {
	@Autowired
	private DatabaseAccess da;
	@Autowired
	MockMvc mockMvc;
	@Test
	public void testRoot() throws Exception {
		
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}
	
	@Test
	public void testDatabaseAddReview() throws Exception {
		LinkedMultiValueMap<String,String> requestParams =
				new LinkedMultiValueMap<>();

		requestParams.add("text", "gog");
		requestParams.add("BookId", "26");
		
		
		int origSize = da.getReviews().size();
		mockMvc.perform(post("/addit")
				.params(requestParams))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		
		int newSize = da.getReviews().size();

	 assertThat(newSize).isEqualTo(origSize + 1);
	}
	
}
