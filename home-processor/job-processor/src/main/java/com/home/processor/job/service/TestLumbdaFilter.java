package com.home.processor.job.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class TestLumbdaFilter {
	public static void main(String[] args) {
		List<String> lines = Arrays.asList("spring", "node", "mkyong");

		List<String> result = lines.stream() 			//convert list to stream
			.filter(line -> !"node". equals (line))	//filters the line, equals to "mkyong"
			.collect(Collectors.toList());			//collect the output and convert streams to a List
		
	result.forEach(System.out::println);

	List<String>  result2 = lines.stream().filter(item-> item.equals("node")).collect(Collectors.toList());
  
			String results = lines.stream().filter(x -> "spring".equals(x))	// we want "michael" only
			.findAny()									// If 'findAny' then return found
			.orElse(null);	

			if (Optional.ofNullable(results).isPresent()) {

			}
	}
}
