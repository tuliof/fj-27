package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentData {
	@JsonProperty("value")
	private BigDecimal value;

	public PaymentData(BigDecimal value) {
		this.value = value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return this.value;
	}
}
