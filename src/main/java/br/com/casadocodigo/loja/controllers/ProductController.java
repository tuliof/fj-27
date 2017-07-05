package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDao;
import br.com.casadocodigo.loja.helper.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private FileSaver fileSaver;

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form(Product product, Model model) {
		model.addAttribute("types", BookType.values());
		System.out.println("Products form");
		return "product/product-form";
	}

	// A prop name de RequestMapping é usada na JSP
	// ${spring:mvcUrl("createProduct").build()}
	@RequestMapping(method = RequestMethod.POST, name = "createProduct")
	@Transactional
	public String save(@Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			Model model, MultipartFile summary) {

		if (bindingResult.hasErrors()) {
			System.out.println("Product form has errors.");
			return form(product, model);
		}
		System.out.println("Product form is OK.");

		// Handle file
		String webPath = fileSaver.storeFile("/Desktop/arquivos", summary);
		product.setSummaryPath(webPath);

		// Handle Product
		System.out.println("Creating a product: " + product.getTitle());
		productDao.save(product);
		redirectAttributes.addFlashAttribute("message", "Product added.");
		return "redirect:/products";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, name = "showProduct")
	public ModelAndView show(@PathVariable Integer id) {
		ModelAndView view = new ModelAndView("product/show");
		Product product = productDao.getById(id);
		view.addObject("product", product);
		return view;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Integer id) {

		Product p = productDao.getById(id);

		return "redirect:/products";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("products", productDao.getAll());
		return "product/list";
	}
	/*
	 * Não é mais necessário, agora estamos usando Hibernate Bean Validation
	 * 
	 * @InitBinder public void initinder(WebDataBinder binder) {
	 * binder.addValidators(new ProductValidator()); }
	 */
}
