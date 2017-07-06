package br.com.casadocodigo.loja.conf;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityFilterConfiguration extends AbstractSecurityWebApplicationInitializer {
	// Apesar da AbstractSecurityWebApplicationInitializer já ter uma
	// implemntação padrão, ela está marcada como abstract, então temos que
	// herdar, mesmo que fique vazio.

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		// TODO Auto-generated method stub
		super.beforeSpringSecurityFilterChain(servletContext);
	}

	@Override
	protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
		// TODO Auto-generated method stub
		super.afterSpringSecurityFilterChain(servletContext);
	}
}
