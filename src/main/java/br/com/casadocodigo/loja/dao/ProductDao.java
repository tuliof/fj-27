package br.com.casadocodigo.loja.dao;

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
}
