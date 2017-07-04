package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProductDao;
import br.com.casadocodigo.loja.model.BookType;
import br.com.casadocodigo.loja.model.Product;
import br.com.casadocodigo.loja.validator.ProductValidator;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form(Product product, Model model) {
		model.addAttribute("types", BookType.values());
		System.out.println("Products form");
		return "product/product-form";
	}
	
	@RequestMapping(method = RequestMethod.POST, name="createProduct")
	@Transactional
	public String create(@Valid Product product, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			System.out.println("Product form has errors.");
			return form(product, model);
		} else {
			System.out.println("Product form is OK.");
			System.out.println("Creating a product: " + product.getTitle());
			productDao.save(product);
			redirectAttributes.addFlashAttribute("message", "Product added.");
			return "redirect:/products";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("products", productDao.getAll());
		return "product/list";
	}
	/* Não é mais necessário, agora estamos usando Hibernate Bean Validation
	@InitBinder
	public void initinder(WebDataBinder binder) {
		binder.addValidators(new ProductValidator());
	}
	*/
}
