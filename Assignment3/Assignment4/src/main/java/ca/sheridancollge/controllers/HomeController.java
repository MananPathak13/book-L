package ca.sheridancollge.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollge.beans.Book;
import ca.sheridancollge.beans.Review;
import ca.sheridancollge.database.DatabaseAccess;

@Controller
public class HomeController {

	@Autowired
	DatabaseAccess da;
	
	@Autowired
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	
	@PostMapping("/")
	public String goHome() {
		return "index";
	}
	@GetMapping("/")
	public String goHome2(Model model) {
		List<Book> books = da.getbooks();
		model.addAttribute("bookList", books);
		return "index";
	}
	@GetMapping("/user")
	public String goHome02() {
		return "/user/add-review";
	}
	@GetMapping("/add-review")
	public String addReview(Model model) {
    
		model.addAttribute("review", new Review());
		return "/user/add-review";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}
	@GetMapping("/add-book")
	public String addBook() {
		return "admin/add-book";
	}
	
	
	@PostMapping("/new-user")
	public String addUser(@RequestParam String userName, @RequestParam String password,
						 Model model) {
		String encodedPassword = passwordEncoder.encode(password);
		List<GrantedAuthority> authorityList = new ArrayList<>();
		
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		User user = new User(userName, encodedPassword, authorityList);
		jdbcUserDetailsManager.createUser(user);
		
		model.addAttribute("message", "User successfully added");
		return "/login";
		
	}
	@PostMapping("/addit")
	public String addit(@ModelAttribute Review review,@RequestParam Long bookId) {
		review.setBookId(bookId);
			int returnValue = da.addReview(review);
			System.out.println(returnValue);
			return "redirect:/";
	}
	
	@PostMapping("/add-book")
	public String addbook(@ModelAttribute Book book) {
			int returnValue = da.addBook(book);
			System.out.println(returnValue);
			return "redirect:/";
	}
	
	@GetMapping("/viewBook/{id}")
	public String viewbook(@ModelAttribute Book book, Model model) {
		
		List<Review> reviews = da.viewReviews(book.getId());

		model.addAttribute("reviewList", reviews);
	
		return "/view-book";
	}
	
}
