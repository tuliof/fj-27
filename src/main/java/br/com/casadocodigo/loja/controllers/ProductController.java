package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProductDao;
import br.com.casadocodigo.loja.model.BookType;
import br.com.casadocodigo.loja.model.Product;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String ind(Model model) {
		model.addAttribute("types", BookType.values());
		System.out.println("Products form");
		return "product/product-form";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public String create(Product product, RedirectAttributes redirectAttributes) {
		System.out.println("Creating a product: " + product.getTitle());
		productDao.save(product);
		
		redirectAttributes.addFlashAttribute("x", "a");
		
		return "redirect:/products";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("products", productDao.getAll());
		return "product/list";
	}
}
