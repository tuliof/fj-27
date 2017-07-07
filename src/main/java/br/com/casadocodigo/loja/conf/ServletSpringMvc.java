package br.com.casadocodigo.loja.conf;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMvc extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		
		// Uma forma de fazer com que o contexto do Spring inicie com um profile
		// selecionado por padrão, é setar ele no onStartup.
		// servletContext.addListener(RequestContextListener.class);
		// servletContext.setInitParameter("spring.profiles.active", "dev");
		
		// Uma forma de não ficar fixo é pegar o active profile de uma variável de ambiente
		// servletContext.setInitParameter("spring.profiles.active", 
		// 		System.getenv("myapp.spring.profiles.active"));
		
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfiguration.class, AppWebConfiguration.class, JpaConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {  };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
