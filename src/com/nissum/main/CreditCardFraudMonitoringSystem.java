/**
 * 
 */
package com.nissum.main;

import java.util.List;
import java.util.Set;

import com.nissum.model.CreditCard;
import com.nissum.util.FraudCreditCardUtil;

/**
 * @author myenda
 *
 */
public class CreditCardFraudMonitoringSystem {
	
	public static void main(String args[]) {
		List<CreditCard> transactions = FraudCreditCardUtil.getTestData();
		Set<String> fraudCreditcards = FraudCreditCardUtil.getFraudCreditcards(transactions);
		fraudCreditcards.stream().forEach(System.out::println);
	}

}
