package dtu.is31380.drools;

public class SetPoint {

	private String name;
	private double temp;

public SetPoint(String name,double temp) {
	this.setName(name);
	this.temp = temp;
}

public double getTemp() {
	return temp;
}

public void setTemp(double temp) {
	this.temp = temp;
}


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
