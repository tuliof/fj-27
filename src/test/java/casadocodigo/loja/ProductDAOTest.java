package casadocodigo.loja;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.conf.JpaConfiguration;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import casadocodigo.ProductBuilder;
import casadocodigo.loja.conf.DataSourceConfigurationTest;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
// Aqui a ordem é importante. Caso você defina o mesmo Bean várias vezes, a instância válida no contexto
// será a última a ter sido instânciada. Nesse caso definimos um dataSource em JpaConfiguration e 
// DataSourceConfigurationTest. A instância válida será a do DataSource(...) pois foi a última a ser
// adicionada ao contexto.
@ContextConfiguration(classes = {ProductDAO.class, JpaConfiguration.class, DataSourceConfigurationTest.class })
@ActiveProfiles("test")
public class ProductDAOTest {

	@Autowired
	private ProductDAO productDao;
	
	//@Transactional
	//@Test
	public void shouldSumAllPricesOfEachBookPerType() {
		
		/*
		 * Forma manual de iniciar o context do spring e pegar a instância de um bean de dentro do contexto.
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProductDao.class,
				JpaConfiguration.class);
		
		ProductDao dao = context.getBean(ProductDao.class);
		*/
		
		List<Product> printedBooks = ProductBuilder
				.newProduct(BookType.PRINTED, BigDecimal.TEN)
				.more(4).buildAll();
		// Aqui salvamos os livros na base de dados
		printedBooks.stream().forEach(productDao::save);
		
		List<Product> ebooks = ProductBuilder
				.newProduct(BookType.EBOOK, BigDecimal.TEN)
				.more(4).buildAll();
		// Aqui salvamos os livros na base de dados
		printedBooks.stream().forEach(productDao::save);
		
		// Consultamos os livros que foram incluídos
		BigDecimal printedValue = productDao.sumPricesByType(BookType.PRINTED);
		BigDecimal ebookValue = productDao.sumPricesByType(BookType.EBOOK);
		
		Assert.assertEquals(new BigDecimal(50), printedValue);
		Assert.assertEquals(ebooks, ebookValue);
		
		// No final não é preciso deletar os livros criados
		// O Spring controla a transaction nos JUnits de forma diferente
		// Ele dá rollback ao final e não persiste nada no banco.
	}
}
