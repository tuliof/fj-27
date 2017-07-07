package br.com.casadocodigo.loja.conf;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SpringSecurityFilterConfiguration extends AbstractSecurityWebApplicationInitializer {
	// Apesar da AbstractSecurityWebApplicationInitializer já ter uma
	// implemntação padrão, ela está marcada como abstract, então temos que
	// herdar, mesmo que fique vazio.
}
