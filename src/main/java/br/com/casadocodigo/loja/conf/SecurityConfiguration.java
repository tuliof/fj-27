package br.com.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.AntPathRequestMatcher;

import br.com.casadocodigo.loja.daos.UserDao;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDao users;
	
	@Override
	/**
	 * Esse cara faz a AUTORIZAÇÃO
	 */
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
		
		// suporta login por form e openId
		http.authorizeRequests()
		// Auth
		.antMatchers("/products/form").hasRole("ADMIN")
		.antMatchers("/shopping/**").permitAll()
		.antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
		.antMatchers("/products**/**").permitAll()
		.anyRequest().authenticated()
		
		// Login
		.and().formLogin()//.loginPage("/login").permitAll().defaultSuccessUrl("/products")
		
		// Logout
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.permitAll().logoutSuccessUrl("/login")
		
		// 403
		.and()
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp");
	}
	
	@Override
	/**
	 * Esse cara faz a AUTENTICAÇÃO
	 */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(users)
		// Mecanismo de cript é setado aqui, nele você define o que fazer com a senha antes de ser
		.passwordEncoder(new BCryptPasswordEncoder());
	}
}
