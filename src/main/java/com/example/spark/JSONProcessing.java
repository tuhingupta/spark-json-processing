package com.example.spark;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import com.example.model.Customer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

/**
 * @author Tuhin Gupta
 *
 */
public class JSONProcessing {
	
	
	public static class ParseJson implements FlatMapFunction<Iterator<String>, Customer> {
	    public Iterable<Customer> call(Iterator<String> lines) throws Exception {
	      ArrayList<Customer> customerList = new ArrayList<Customer>();
	      ObjectMapper mapper = new ObjectMapper();
	      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	      mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
	      while (lines.hasNext()) {
	        String line = lines.next();
	        
	        try {
	        	Customer oc = mapper.readValue(line, Customer.class);
	        	System.out.println(oc.toString()+"->>>"+line);
	        	customerList.add(oc);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	      
	      System.out.println("===>"+customerList.size());
	      return customerList;
	    }
	  }
	
	
	  public static class WriteJson implements FlatMapFunction<Iterator<Customer>, String> {
		    public Iterable<String> call(Iterator<Customer> outclearItr) throws Exception {
		      ArrayList<String> text = new ArrayList<String>();
		      ObjectMapper mapper = new ObjectMapper();
		      while (outclearItr.hasNext()) {
		    	  Customer jsonObj = outclearItr.next();
		        text.add(mapper.writeValueAsString(jsonObj));
		      }
		      return text;
		    }
		  }
	

	public static void main(String[] args) {
		
		//initialize spark
		SparkConf conf = new SparkConf().setAppName("spark-json-stream").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		
		//process json
		JavaRDD<String> input = sc.textFile("src/main/resources/input/customer.json");
	    JavaRDD<Customer> result = input.mapPartitions(new ParseJson());//.filter(new LikesPandas());
	    JavaRDD<String> formatted = result.mapPartitions(new WriteJson());
	    formatted.saveAsTextFile("src/main/resources/output");
		

	}

}
