package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Price {
	@Column(scale = 2)
	protected BigDecimal value;
	@Enumerated(EnumType.STRING)
	protected BookType bookType;
	
	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	/**
	 * @return the bookType
	 */
	public BookType getBookType() {
		return bookType;
	}
	/**
	 * @param bookType the bookType to set
	 */
	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
}
