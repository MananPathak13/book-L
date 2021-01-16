package ca.sheridancollge.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/add-review/**").hasAnyRole("USER", "ADMIN").antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/add-book/**").hasRole("ADMIN").antMatchers("/h2-console/**").permitAll().and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/").and().logout().invalidateHttpSession(true)
				.clearAuthentication(true).and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().passwordEncoder(passwordEncoder)
				.withUser("bugs").password(passwordEncoder.encode("bunny")).roles("USER").and().withUser("daffy")
				.password(passwordEncoder.encode("duck")).roles("USER", "ADMIN");
	}

	/**
	 * Creates a Bean of type JdbcUserDetailsManager that will be used in
	 * HomeController
	 * 
	 * @return a instance of JdbcUserDetailsManager configured to use our datasource
	 * @throws Exception
	 */
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}

}
