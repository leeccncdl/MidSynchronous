package cn.zytec.lee;

import java.util.List;

/**
   * 中间件测试数据模型
   * @author: lee
   * @modify date: 2012-8-30 下午01:33:15
   */
public class TestDao {
	private int id;
	private int age;
	private String name;
	private String score;
	private List<TestDao2> list;
	
	public TestDao(int id,int age,String name,String score) {
		this.id = id;
		this.age = age;
		this.name = name;
		this.score = score;


	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<TestDao2> getList() {
		return list;
	}

	public void setList(List<TestDao2> list) {
		this.list = list;
	}
	
	public class TestDao2 {
		public String bookName;
		public String teacherName;
		
		public TestDao2(String bookName,String teacherName) {
			this.bookName = bookName;
			this.teacherName = teacherName;
		}
	}
	
}
