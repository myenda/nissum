/**
 * 
 */
package com.nissum.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author myenda
 *
 */
public class DateSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		LocalDateTime lDate = LocalDateTime.now();
		System.out.println(lDate);
		lDate.plusDays(1l);
		System.out.println(lDate.plusDays(2l));
	}

}
