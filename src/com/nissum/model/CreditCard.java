/**
 * 
 */
package com.nissum.model;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;

/**
 * @author myenda
 *
 */
public class CreditCard {
	
	private String creditCardTokenNumber;
	private LocalDateTime transactionDateTime;
	private Double amount;
	/**
	 * @param creditCardTokenNumber
	 * @param transactionDateTime
	 * @param amount
	 */
	public CreditCard(String creditCardTokenNumber, LocalDateTime transactionDateTime, Double amount) {
		super();
		this.creditCardTokenNumber = creditCardTokenNumber;
		this.transactionDateTime = transactionDateTime;
		this.amount = amount;
	}
	/**
	 * @return the creditCardTokenNumber
	 */
	public String getCreditCardTokenNumber() {
		return creditCardTokenNumber;
	}
	/**
	 * @param creditCardTokenNumber the creditCardTokenNumber to set
	 */
	public void setCreditCardTokenNumber(String creditCardTokenNumber) {
		this.creditCardTokenNumber = creditCardTokenNumber;
	}
	/**
	 * @return the transactionDateTime
	 */
	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}
	/**
	 * @param transactionDateTime the transactionDateTime to set
	 */
	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public static Comparator<CreditCard> SortByTransactionDate = (c1, c2) -> {return c1.getTransactionDateTime().compareTo(c2.getTransactionDateTime());};
	
//	public static Comparator<CreditCard> SortByTransactionDate = new Comparator<CreditCard>() {
//		public int compare(CreditCard c1, CreditCard c2) {
//		   /*For ascending order*/
//		   return c1.getTransactionDateTime().compareTo(c2.getTransactionDateTime());
//		}
//	};
	
}
