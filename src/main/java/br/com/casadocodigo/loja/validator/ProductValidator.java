package br.com.casadocodigo.loja.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.model.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		if (Product.class.isAssignableFrom(clazz)) {
			return true;
		}
		
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
			// Forma mais automática
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required",
					"Título do livro não pode estar vazio.");
			
			ValidationUtils.rejectIfEmpty(errors, "description", "field.required",
					"Descrição do livro não pode estar vazia.");

			// Forma manual
			Product p = (Product) target;
			if (p.getNumberOfPages() <= 0) {
				errors.reject("field.required", "Número de páginas tem que ser maior que zero.");
			}
			
			System.out.println("Numero de erros: " + errors.getAllErrors().size());
	}

}
