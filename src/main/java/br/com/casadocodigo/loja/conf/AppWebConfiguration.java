package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.controllers.ProductController;
import br.com.casadocodigo.loja.dao.ProductDao;

@EnableWebMvc
@ComponentScan(basePackageClasses={ProductController.class, ProductDao.class})
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	@Bean
	/**
	 * Usado para configurar o base path das páginas e a extensão
	 * por meio de prefixo e sufixo.
	 * 
	 * prefixo + <String de retorno do método do controller> + sufixo
	 * @return
	 */
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = 
				new InternalResourceViewResolver();
		
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/");
	}
	
	/**
	 * @Bean(name="") é equivalente ao id do xml <bean id="" ...>
	 * @return
	 */
	@Bean(name="messageSource") 
	public MessageSource messageSource() {
		// Esse é o cara que carrega os message source
		// Message source são os arquivos de linguagem
		// Ex.: messages.properties
		// field.required=Esse campo é obrigatório
		ReloadableResourceBundleMessageSource messageSource = 
				new ReloadableResourceBundleMessageSource();
		
		// Aonde o(s) arquivo(s) se encontra(m)?
		// Haveram vários arquivos como pt-BR, en-US, es-ES...
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(30);
		
		return messageSource;
	}
}
