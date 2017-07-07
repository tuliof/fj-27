package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.ShoppingItem;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

	@Autowired
	private ProductDAO productDao;
	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private RestTemplate rest;

	@RequestMapping(method = RequestMethod.GET)
	public String itens() {
		return "shoppingCart/items";

	}

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

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public Callable<String> checkout(RedirectAttributes attributes) {

		// Além de Callable também existe DeferredResult
		// Dar uma olhada em DeferredResult

		// Equivalente a fazer new Callable<String>() { implementação de
		// call()...}
		// Interfaces que tem apenas um método são chamados de "Functional
		// interfaces"
		// Podem ser implementadas usando lambda. O tipo é inferido pelo retorno
		// do método nesse caso
		return () -> {

			BigDecimal valor = shoppingCart.getTotal();
			String uri = "http://book-payment.herokuapp.com/payment";

			String paymentMessage = "";
			try {
				paymentMessage = rest.postForObject(uri, new PaymentData(valor), String.class);
			} catch (HttpClientErrorException ex) {
				paymentMessage = "Erro no pagamento.";
				return "redirect:/shipping";
			}

			attributes.addFlashAttribute("paymentMessage", paymentMessage);

			return "redirect:/products";
		};
	}
}
