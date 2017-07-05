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
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices", Product.class)
				.getResultList();
	}

	public Product getById(Integer id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct(p) ");
		sb.append("from Product p join fetch p.prices ");
		sb.append("where id = :id");

		Product p = manager.createQuery(sb.toString(), Product.class).setParameter("id", id).getSingleResult();

		return p;
	}

	public void delete(Integer id) {
		Product p = getById(id);
		manager.remove(p);
	}
}
