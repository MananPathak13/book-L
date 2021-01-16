package ca.sheridancollge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.sheridancollge.beans.Book;
import ca.sheridancollge.database.DatabaseAccess;



@RunWith (SpringRunner.class)
@SpringBootTest
public class TestDatabase {
	@Autowired
	public DatabaseAccess da;
	
	@Test
	public void testDatabaseAddBook() {
		Book book = new Book();
		book.setAuthor("Paulo Coelho");
		book.setTitle("The Alchemist");
		int orgDatabaseSize = da.getbooks().size();
		da.addBook(book);
		int newDatabaseSize = da.getbooks().size();
		assertThat(newDatabaseSize).isEqualTo(orgDatabaseSize + 1);	
	}

}
