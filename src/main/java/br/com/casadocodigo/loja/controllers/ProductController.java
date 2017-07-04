package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.loja.dao.ProductDao;
import br.com.casadocodigo.loja.model.Product;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	@RequestMapping("/")
	public String ind() {
		System.out.println("index");
		return "product/product-form";
	}
	
	@RequestMapping("/create")
	@Transactional
	public String create(Product product) {
		System.out.println("Creating a product: " + product.getTitle());
		productDao.save(product);
		return "product/ok";
	}
}
