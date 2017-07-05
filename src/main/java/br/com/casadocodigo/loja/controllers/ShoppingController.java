package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProductDao;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.ShoppingItem;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShoppingCart shoppingCart;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(Integer productId, BookType bookType) {
		ShoppingItem item = createItem(productId, bookType);
		shoppingCart.add(item);
		return new ModelAndView("redirect:/products");
	}

	private ShoppingItem createItem(Integer productId, BookType bookType) {
		Product product = productDao.findById(productId);
		ShoppingItem item = new ShoppingItem(product, bookType);
		return item;
	}
}
