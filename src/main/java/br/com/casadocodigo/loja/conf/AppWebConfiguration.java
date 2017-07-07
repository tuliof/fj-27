package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controllers.ProductController;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.helper.FileSaver;
import br.com.casadocodigo.loja.models.ShoppingCart;

@EnableWebMvc
@EnableCaching
@ComponentScan(basePackageClasses = { ProductController.class, ProductDAO.class, FileSaver.class, ShoppingCart.class })
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	/**
	 * Usado para configurar o base path das páginas e a extensão por meio de
	 * prefixo e sufixo.
	 * 
	 * prefixo + <String de retorno do método do controller> + sufixo
	 * 
	 * @return
	 */
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		// Expondo a classe ShoppingCart.class
		// Essa classe vai entrar no contexto do spring e vai poder ser acessada
		// nas JSPs
		resolver.setExposedContextBeanNames("shoppingCart");

		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	/**
	 * O nome desse bean precisa OBRIGATORIAMENTE ser "messageSource" O nome do
	 * método deve ser esse ou você deve setar a propriedade name na
	 * annotation @Bean
	 * 
	 * @Bean(name="") é equivalente ao id do xml <bean id="" ...>
	 * @return
	 */
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		// Esse é o cara que carrega os message source
		// Message source são os arquivos de linguagem
		// Ex.: messages.properties
		// field.required=Esse campo é obrigatório
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		// Aonde o(s) arquivo(s) se encontra(m)?
		// Haveram vários arquivos como pt-BR, en-US, es-ES...
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(30);

		return messageSource;
	}

	@Bean(name = "mvcConversionService")
	public FormattingConversionService configureTimeFormat() {
		// Passar true para que ele seja instanciado com os formatadores padrões
		// do contrário ele vem vazio.
		FormattingConversionService conversionService = new DefaultFormattingConversionService(true);

		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
		registrar.registerFormatters(conversionService);

		return conversionService;
	}

	/*
	 * @Bean public MultipartResolver commonsMultipartResolver() {
	 * 
	 * CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	 * resolver.setDefaultEncoding("UTF-8");
	 * resolver.setMaxUploadSize(50000000); return resolver; }
	 */

	@Bean(name = "multipartResolver")
	public MultipartResolver standardServletMultipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Também é preciso anotar a classe em que esse Bean é configurado
	 * como @EnableCaching
	 * 
	 * @return
	 */
	@Bean
	public CacheManager cacheManager() {

		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES)
				.maximumSize(100);

		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(cacheBuilder);

		return manager;
	}

	@Bean
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {

		List<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(internalResourceViewResolver());
		viewResolvers.add(new JsonViewResolver());
		viewResolvers.add(new XMLViewResolver());

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;
	}
	
	@Bean
	// O LocaleChangeInterceptor pega o header Accept do cliente e seta no LocaleResolver
	public LocaleResolver localeResolver() {
		LocaleResolver resolver = new SessionLocaleResolver();// ou CookieLocaleResolver
		return resolver;
	}
	
	@Override
	// Registra a implementação padrão do Spring para o LocaleChangeInterceptor
	// Esse interceptor vai pegar o locale do navegador do cliente e setar no LocaleResolver
	// que você configurar, pode ser o SessionLocaleResolver ou o CookieL...Resolver
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
}
