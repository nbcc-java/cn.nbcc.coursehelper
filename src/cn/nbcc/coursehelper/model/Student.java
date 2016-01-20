package cn.nbcc.coursehelper.model;

import java.util.HashMap;

public class Student {
	
	String id;
	String name;
	
	
	HashMap<String, Double> assResultMap=new HashMap<>();
	HashMap<String, Double> exResultMap=new HashMap<>();
	
	public Student(String id,String name) {
		this.id = id;
		this.name = name;
	}
	public Student() {
		id="unknown";
		name = "unknown";
	}
}
