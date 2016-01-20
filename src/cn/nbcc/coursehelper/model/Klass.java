package cn.nbcc.coursehelper.model;

import java.util.ArrayList;

public class Klass {
	
	private String kid;
	String klassName;
	
	ArrayList<Student> students = new ArrayList<>();
	
	public Klass() {
		// TODO Auto-generated constructor stub
	}

	public Klass(String kid, String kname) {
		this.kid = kid;
		this.klassName = kname;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getKlassName() {
		return klassName;
	}

	public void setKlassName(String klassName) {
		this.klassName = klassName;
	}
	
	
}
