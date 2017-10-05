package dtu.is31380.busPass;

public class Person {
	private String name;
private int age;
private String location;

public Person(String name,int age, String loc) {
	this.setName(name);
	this.age = age;
	this.location = loc;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
