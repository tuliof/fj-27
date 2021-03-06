package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.helper.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.User;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductDAO productDao;
	@Autowired
	private FileSaver fileSaver;
	@Autowired
	private CacheManager cacheManager;

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

		// Invalidate cache manually
		Cache cache = this.cacheManager.getCache("products");
		cache.clear();

		return "redirect:/products";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, name = "showProduct")
	public ModelAndView show(@PathVariable Integer id) {
		ModelAndView view = new ModelAndView("product/show");
		Product product = productDao.findById(id);
		view.addObject("product", product);
		return view;
	}

	@CacheEvict(value = { "lastProducts" })
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Integer id) {

		Product p = productDao.findById(id);

		return "redirect:/products";
	}

	@Cacheable("lastProducts")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(@AuthenticationPrincipal User user) {

		System.out.println("User: " + user.toString());
		System.out.println(this.cacheManager.getCacheNames());

		ModelAndView view = new ModelAndView("product/list");
		view.addObject("products", productDao.findAll());
		return view;
	}
	/*
	 * Não é mais necessário, agora estamos usando Hibernate Bean Validation
	 * 
	 * @InitBinder public void initinder(WebDataBinder binder) {
	 * binder.addValidators(new ProductValidator()); }
	 */
}
