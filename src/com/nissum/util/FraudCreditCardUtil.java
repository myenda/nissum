/**
 * 
 */
package com.nissum.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nissum.model.CreditCard;

/**
 * @author myenda
 *
 */
public class FraudCreditCardUtil {
	
	public static List<CreditCard> getTestData() {
		return Stream
					.of(new CreditCard("123456789012",LocalDateTime.now(),300.00),
						new CreditCard("123456789013",LocalDateTime.now().plusDays(1l),300.00),	
						new CreditCard("123456789012",LocalDateTime.now().plusHours(12l),700.00),
						new CreditCard("123456789013",LocalDateTime.now().plusHours(12l),300.12),
						new CreditCard("123456789012",LocalDateTime.now().minusDays(1l),1300.12),
						new CreditCard("123456789013",LocalDateTime.now().plusHours(12l),1400.12),
						new CreditCard("123456789012",LocalDateTime.now().plusDays(1l),300.12),
						new CreditCard("123456789013",LocalDateTime.now().plusHours(12l),300.12),
						new CreditCard("123456789012",LocalDateTime.now().minusDays(2l),300.12),
						new CreditCard("123456789013",LocalDateTime.now().plusHours(12l),300.12),
						new CreditCard("123456789014",LocalDateTime.now().plusHours(12l),1300.12),
						new CreditCard("123456789017",LocalDateTime.now().plusHours(12l),300.12),
						new CreditCard("123456789016",LocalDateTime.now().plusHours(12l),300.12),
						new CreditCard("123456789015",LocalDateTime.now().plusDays(1l),300.12),
						new CreditCard("123456789014",LocalDateTime.now().plusDays(1l),700.12))
					.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public static Set<String> getFraudCreditcards(List<CreditCard> transactions) {
		Set<String> fraudCreditCards = null;
		/**
		 * Creating Map Grouping Transactions by CreditCardTokenNumber
		 */
		Map<String, List<CreditCard>> transactionsByCreditCardMap =
			transactions.stream().collect(Collectors.groupingBy(CreditCard::getCreditCardTokenNumber));
		fraudCreditCards = getFraudCardsFromMap(transactionsByCreditCardMap);
		return fraudCreditCards;
	}
	
	private static Set<String> getFraudCardsFromMap(Map<String, List<CreditCard>> transactionsByCreditCardMap) {
		Set<String> fraudCreditCards = new HashSet<String>();
		transactionsByCreditCardMap.forEach((k,v)-> {
			/**
			 * Sorting all transactions in Ascending order for each creditcard 
			 * Cosidering 24hrs as upperbound and 0 as lowerbound calculating differnce between transactions and adding sum
			 * if it is greater than or equal to 2000 then add card to set and go next credit card 
			 * else move lowerbound to 1 and start iteration from there sum it up till 24 hrs upperbound Repeat the process
			 */
			//TODO Still we can improvise the code and working on it
			Collections.sort(v, CreditCard.SortByTransactionDate);
			LocalDateTime firstTransactionDate = v.get(0).getTransactionDateTime();
			Double sum = v.get(0).getAmount();
			if(sum>=2000) {
				fraudCreditCards.add(k);
			} else {
				int lbIndex = 0;
				if(v.size()>1) {
					for(int i=1;i<v.size();i++) {
						LocalDateTime currDate = v.get(i).getTransactionDateTime();
						Duration mins = Duration.between(firstTransactionDate, currDate);
						if(mins.toMinutes()<=1440) {
							sum = sum + v.get(i).getAmount();
							if(sum>=2000) {
								fraudCreditCards.add(k);
								break;
							}
								
						} else {
							lbIndex++;
							sum = v.get(lbIndex).getAmount();
							firstTransactionDate = v.get(lbIndex).getTransactionDateTime();
							i = lbIndex+1;
						}
							
					}
				}
			
			}
		}
		
		);
		return fraudCreditCards;
	}

}
