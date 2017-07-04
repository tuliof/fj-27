package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.model.Product;

@Repository
public class ProductDao {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(Product product) {
		manager.persist(product);
	}
	
	public List<Product> getAll() {
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices", 
				Product.class)
		.getResultList();
	}
}
