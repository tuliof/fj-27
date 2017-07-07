package br.com.casadocodigo.loja.controllers;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.casadocodigo.loja.models.Product;

@Controller
@RequestMapping("/home")
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/teste")
	@ResponseBody
	public Product locale(String locale) {
		System.out.println("Locale: " + locale);
		Product p = new Product();
		p.setId(1);
		p.setTitle("Product title");
		p.setDescription("My test product");
		p.setReleaseDate(Calendar.getInstance());
		return p;
	}
}
