/**
 * 
 */
package com.nissum.main;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
public class CreditCardFraudMonitoringSystem {
	
	private Set<String> getFraudCreditcards(List<CreditCard> transactions) {
		Set<String> fraudCreditCards = new HashSet<String>();
		Map<String, List<CreditCard>> transactionsByCreditCardMap =
			transactions.stream().collect(Collectors.groupingBy(CreditCard::getCreditCardTokenNumber));
		fraudCreditCards = getFraudCardsFromMap(transactionsByCreditCardMap,fraudCreditCards);
		return fraudCreditCards;
	}
	
	private Set<String> getFraudCardsFromMap(Map<String,List<CreditCard>> transactionsByCreditCardMap,Set<String> fraudCreditCards) {
		
		transactionsByCreditCardMap.forEach((k,v)-> {
			Collections.sort(v, CreditCard.SortByTransactionDate);
			Date firstTransactionDate = v.get(0).getTransactionDateTime();
			Double sum = v.get(0).getAmount();
			int lbIndex = 0;
			if(v.size()>1) {
				for(int i=1;i<v.size();i++) {
					Date currDate = v.get(i).getTransactionDateTime();
					Duration  mins = java.time.Duration.between(
					LocalDateTime.of(firstTransactionDate.getYear(), firstTransactionDate.getMonth(), firstTransactionDate.getDay(), firstTransactionDate.getHours(), firstTransactionDate.getMinutes()), 
					LocalDateTime.of(currDate.getYear(),currDate.getMonth(),currDate.getDay(),currDate.getHours(),currDate.getMinutes()));
					if(mins.toMinutes()<=1440) {
						sum = sum + v.get(i).getAmount();
						if(sum>=2000) {
							fraudCreditCards.add(k);
							break;
						}
							
					} else {
						firstTransactionDate = v.get(i).getTransactionDateTime();
						sum = sum-v.get(lbIndex).getAmount();
						lbIndex++;
						i--;
					}
						
				}
			}
			
			}
		
		);
		return fraudCreditCards;
	}
	
	public static void main(String args[]) {
		//SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  		
		List<CreditCard> transactions = null;
		
			transactions = Stream
					.of(new CreditCard("123456789012",new Date(),300.00),
						new CreditCard("123456789013",new Date(),300.00),	
						new CreditCard("123456789012",new Date(),700.00),
						new CreditCard("123456789013",new Date(),300.12),
						new CreditCard("123456789012",new Date(),1300.12),
						new CreditCard("123456789013",new Date(),1400.12),
						new CreditCard("123456789012",new Date(),300.12),
						new CreditCard("123456789013",new Date(),300.12),
						new CreditCard("123456789012",new Date(),300.12),
						new CreditCard("123456789013",new Date(),300.12),
						new CreditCard("123456789014",new Date(),1300.12),
						new CreditCard("123456789017",new Date(),300.12),
						new CreditCard("123456789016",new Date(),300.12),
						new CreditCard("123456789015",new Date(),300.12),
						new CreditCard("123456789014",new Date(),700.12))
					.collect(Collectors.toCollection(ArrayList::new));
		
		CreditCardFraudMonitoringSystem creditCardFraudMonitoringSystem = new CreditCardFraudMonitoringSystem();
		Set<String> fraudCreditcards = creditCardFraudMonitoringSystem.getFraudCreditcards(transactions);
		fraudCreditcards.stream().forEach(System.out::println);
	}

}
