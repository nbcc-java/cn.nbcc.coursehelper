package cn.nbcc.coursehelper.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Course {
	
	String cid;
	String cName;
	ArrayList<Teacher> teaList = new ArrayList<>();
	ArrayList<Klass> klassList = new ArrayList<>();
	ArrayList<Student> stuList = new ArrayList<>();
	
	
	public Course() {
	}
	
	public Course(String cid, String cname) {
		this.cid = cid;
		this.cName= cname;
	}
	public void setTeacher(Teacher teacher) {
		if(!teaList.contains(teacher))
			teaList.add(teacher);
	}
	public void setKlass(Klass klass) {
		if(!klassList.contains(klass))
			klassList.add(klass);
	}
	public void addStudents(List<Student> sList) {
		for (Student student : sList) {
			if (!this.stuList.contains(student)) {
				stuList.add(student);
			}
		}
	}
	public List<Student> getStudents() {
		return stuList;
	}
	public String getId() {
		return cid;
	}
	public String getName() {
		return cName;
	}
	
	public void setID(String cId) {
		this.cid = cId;
	}
	public void setName(String cName) {
		this.cName = cName;
	}
}
