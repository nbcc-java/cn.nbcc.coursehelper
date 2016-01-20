package cn.nbcc.coursehelper.model;

public class Teacher {
	
	String tid;
	String name;
	
	public Teacher(String tid,String name) {
		this.tid = tid;
		this.name = name;
	}

	public String getId() {
		return tid;
	}

	public String getName() {
		return name;
	}

}
