package ca.sheridancollge.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollge.beans.Book;
import ca.sheridancollge.beans.Review;

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	public List<String> getAuthorities(){
	
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT DISTINCT authority FROM authorities";
	
		List<String> authorities = jdbc.queryForList(query, namedParameters, String.class);
	
		return authorities;
	}
	

	public DatabaseAccess(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Book> getbooks() {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM books";

		// This will map a row coming in to an instance of Mission
		BeanPropertyRowMapper<Book> bookMapper = new BeanPropertyRowMapper<Book>(Book.class);

		List<Book> books = jdbc.query(query, namedParameters, bookMapper);
		return books;
	}
	
	public List<Review> viewReviews(Long id) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM reviews WHERE bookId = :bookId";

		namedParameters.addValue("bookId", id);
		BeanPropertyRowMapper<Review> bookMapper = new BeanPropertyRowMapper<Review>(Review.class);

		List<Review> reviews = jdbc.query(query, namedParameters, bookMapper);
		return reviews;
	}
	
	public int addReview(Review review) {

		// create an instance of MapSqlParameterSource for our use
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		System.out.println(review.toString());
		String query = "INSERT INTO reviews (bookId, text) VALUES (:bookId, :text)";

		// add the parameters to our map
		namedParameters.addValue("bookId",review.getBookId()).addValue("text", review.getText());

		int returnValue = jdbc.update(query, namedParameters);

		return returnValue;
	}
	
	public int addBook(Book book) {

		// create an instance of MapSqlParameterSource for our use
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO books (title, author) VALUES (:title, :author)";

		// add the parameters to our map
		namedParameters.addValue("title",book.getTitle()).addValue("author", book.getAuthor());

		int returnValue = jdbc.update(query, namedParameters);

		return returnValue;
	}
}