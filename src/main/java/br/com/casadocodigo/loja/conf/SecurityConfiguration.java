package br.com.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.AntPathRequestMatcher;

import br.com.casadocodigo.loja.daos.UserDao;

@EnableWebMvcSecurity
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
		.antMatchers("/home/**").permitAll()
		.anyRequest().authenticated()
		
		// Login
		.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/products")
		
		// Logout
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.permitAll().logoutSuccessUrl("/login")
		
		// 403
		.and()
		.exceptionHandling()
		.accessDeniedPage("/WEB-INF/views/error/403.jsp");
	}
	
	@Override
	// Você precisa adicionar o path dos seus recursos(JS/CSS/imgs) para serem ignorados pelo filtro
	// do Spring Security.
	// 
	// Você será redirecionado a um css ou js aon invés da página esperada.
	// 
	// A tela de login te redireciona automaticamente para o ultimo recurso restrito pedido.
	// Por exemplo, se o seu login está configurado pra redirecionar p/ pagina produtos e essa página
	// faz um request pra um arquivo estilo.css, o ultimo recurso restrito requisitado é o estilo.css
	// Então ao fazer login, o Spring vai te passar o estilo.css ao invés da página produtos.
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring().antMatchers("/resources/**");
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
