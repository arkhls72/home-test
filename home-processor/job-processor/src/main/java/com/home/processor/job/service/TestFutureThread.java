package com.home.processor.job.service;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class TestFutureThread {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		 ExecutorService es = Executors.newSingleThreadExecutor();
		 System.out.println(new Date());
		 CalculateService service = new CalculateService(2);
		 Future<Integer> result1 = es.submit(service);
		 
		 CalculateService service2 = new CalculateService(3);
		 Thread.sleep(1000);
		 Future<Integer> result2 = es.submit(service2);
		 System.out.println(new Date());
		 
		 Integer finalResult =result2.get() + result1.get();  				 		  
		 
		 System.out.println(finalResult);

	}
	
	
	public static int calucalte(int x) {
		 int result = x * 2;
		 return result;
	}
}
